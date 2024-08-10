package ru.scadouge.ewm.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.scadouge.ewm.category.model.Category;
import ru.scadouge.ewm.category.model.Category_;
import ru.scadouge.ewm.category.repository.CategoryRepository;
import ru.scadouge.ewm.error.ConflictException;
import ru.scadouge.ewm.error.NotFoundException;
import ru.scadouge.ewm.event.args.*;
import ru.scadouge.ewm.event.dto.enums.EventState;
import ru.scadouge.ewm.event.mapper.EventMapper;
import ru.scadouge.ewm.event.mapper.LocationMapper;
import ru.scadouge.ewm.event.model.Event;
import ru.scadouge.ewm.event.model.Event_;
import ru.scadouge.ewm.event.model.Location;
import ru.scadouge.ewm.event.model.Location_;
import ru.scadouge.ewm.event.repository.EventRepository;
import ru.scadouge.ewm.event.repository.LocationRepository;
import ru.scadouge.ewm.user.model.User;
import ru.scadouge.ewm.user.model.User_;
import ru.scadouge.ewm.user.repository.UserRepository;
import ru.scadouge.ewm.utils.Pagination;
import ru.scadouge.stats.client.StatsClient;
import ru.scadouge.stats.dto.ViewStats;

import jakarta.persistence.criteria.JoinType;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final StatsClient statsClient;
    private final EventMapper eventMapper;
    private final LocationMapper locationMapper;

    @Override
    @Transactional
    public Event createEventByUser(Long userId, NewEventArgs args) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(User.class, userId));
        Category category = categoryRepository.findById(args.getCategory())
                .orElseThrow(() -> new NotFoundException(Category.class, args.getCategory()));
        Location location = createEventLocation(args.getLocation(), args.getTitle(), args.getEventDate());

        Event event = eventMapper.toModel(args, user, category, location);
        return eventRepository.save(event);
    }

    @Override
    public List<EventWithViewsArgs> getEventsByUser(long userId, int from, int size) {
        Pageable page = Pagination.getPage(from, size);
        List<Event> events = eventRepository.findAllByInitiatorId(userId, page);
        return getEventsWithViews(events);
    }

    @Override
    public EventWithViewsArgs getEventByUser(long userId, long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(Event.class, eventId));
        if (event.getInitiator().getId() != userId) {
            throw new NotFoundException(Event.class, eventId);
        }
        return getEventsWithViews(List.of(event)).stream().findFirst().orElseThrow();
    }

    @Override
    @Transactional
    public EventWithViewsArgs updateEventByUser(long userId, long eventId, EventUserUpdateArgs eventUserUpdateArgs) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(Event.class, eventId));
        if (event.getInitiator().getId() != userId) {
            throw new NotFoundException(Event.class, eventId);
        }
        if (event.getState() == EventState.PUBLISHED) {
            throw new ConflictException(String.format("Cобытие можно редактировать, только если оно еще не опубликовано " +
                    "state=%s", event.getState()));
        }
        if (eventUserUpdateArgs.getCategory() != null) {
            Category category = categoryRepository.findById(eventUserUpdateArgs.getCategory())
                    .orElseThrow(() -> new NotFoundException(Category.class, eventUserUpdateArgs.getCategory()));
            event.setCategory(category);
        }
        if (eventUserUpdateArgs.getStateAction() != null) {
            switch (eventUserUpdateArgs.getStateAction()) {
                case SEND_TO_REVIEW:
                    event.setState(EventState.PENDING);
                    break;
                case CANCEL_REVIEW:
                    event.setState(EventState.CANCELED);
                    break;
            }
        }
        eventMapper.updateEvent(event, eventUserUpdateArgs);
        if (eventUserUpdateArgs.getLocation() != null) {
            Location location = updateEventLocation(event, eventUserUpdateArgs.getLocation());
            event.setLocation(location);
        }
        return getEventsWithViews(List.of(eventRepository.save(event))).stream().findFirst().orElseThrow();
    }

    private Location updateEventLocation(Event event, NewLocationArgs newLocationArgs) {
        if (event.getLocation() != null) {
            Location oldLocation = event.getLocation();
            if (!oldLocation.getPermanent()) {
                locationRepository.deleteById(event.getLocation().getId());
            }
        }
        Location location = createEventLocation(newLocationArgs, event.getTitle(), event.getEventDate());
        event.setLocation(location);
        return location;
    }

    private Location createEventLocation(NewLocationArgs newLocationArgs, String name, Timestamp expirationDate) {
        if (newLocationArgs.getId() != null) {
            Optional<Location> permanentLocationOpt = locationRepository.findById(newLocationArgs.getId());
            if (permanentLocationOpt.isPresent() && permanentLocationOpt.get().getPermanent()) {
                return permanentLocationOpt.get();
            }
        }
        Location location = locationMapper.toLocation(newLocationArgs);
        location.setExpirationDate(expirationDate);
        location.setName(name);
        return locationRepository.save(location);
    }

    @Override
    public List<EventWithViewsArgs> getAllEventsByAdmin(AdminSearchEventsArgs args) {
        Specification<Event> spec = Specification.where((root, query, criteriaBuilder) -> null);

        if (args.getUsers() != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    root.get(Event_.initiator).get(User_.id).in(args.getUsers()));
        }
        if (args.getStates() != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    root.get(Event_.state).as(String.class).in(args.getStates()));
        }
        if (args.getCategories() != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    root.get(Event_.category).get(Category_.id).in(args.getCategories()));
        }
        if (args.getRangeStart() != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThan(root.get(Event_.eventDate), args.getRangeStart()));
        }
        if (args.getRangeEnd() != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThan(root.get(Event_.eventDate), args.getRangeEnd()));
        }
        if (args.getLat() != null && args.getLon() != null && args.getRadius() != null) {
            spec = spec.and((root, query, criteriaBuilder) -> {
                        root.fetch(Event_.location, JoinType.LEFT);
                        return criteriaBuilder.lessThanOrEqualTo(
                                criteriaBuilder.function("distance", Double.class,
                                        criteriaBuilder.literal(args.getLat()),
                                        criteriaBuilder.literal(args.getLon()),
                                        root.get(Event_.location).get(Location_.LAT),
                                        root.get(Event_.location).get(Location_.LON)),
                                args.getRadius()
                        );
                    }
            );
        }

        Pageable page = Pagination.getPage(args.getFrom(), args.getSize());
        List<Event> events = eventRepository.findAll(spec, page);
        return getEventsWithViews(events);
    }

    @Override
    @Transactional
    public EventWithViewsArgs updateEventByAdmin(long eventId, EventAdminUpdateArgs eventAdminUpdateArgs) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(Event.class, eventId));
        if (event.getState() == EventState.PUBLISHED) {
            throw new ConflictException(String.format("Cобытие можно редактировать, только если оно еще не опубликовано " +
                    "state=%s", event.getState()));
        }
        if (eventAdminUpdateArgs.getStateAction() != null) {
            switch (eventAdminUpdateArgs.getStateAction()) {
                case PUBLISH_EVENT:
                    if (event.getState() != EventState.PENDING) {
                        throw new ConflictException("Cобытие можно публиковать, только если оно в состоянии ожидания публикации");
                    }
                    event.setState(EventState.PUBLISHED);
                    break;
                case REJECT_EVENT:
                    event.setState(EventState.CANCELED);
                    break;
            }
        }
        if (eventAdminUpdateArgs.getCategory() != null) {
            Category category = categoryRepository.findById(eventAdminUpdateArgs.getCategory())
                    .orElseThrow(() -> new NotFoundException(Category.class, eventAdminUpdateArgs.getCategory()));
            event.setCategory(category);
        }
        eventMapper.updateEvent(event, eventAdminUpdateArgs);
        if (eventAdminUpdateArgs.getLocation() != null) {
            Location location = updateEventLocation(event, eventAdminUpdateArgs.getLocation());
            event.setLocation(location);
        }

        return getEventsWithViews(List.of(eventRepository.save(event))).stream().findFirst().orElseThrow();
    }

    @Override
    public List<EventWithViewsArgs> getAllPublishedEventsByPublic(SearchEventsArgs args) {
        Specification<Event> spec = Specification.where((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Event_.state), EventState.PUBLISHED));

        if (args.getText() != null) {
            String searchText = String.format("%%%s%%", args.getText());
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.or(
                    criteriaBuilder.like(root.get(Event_.annotation), searchText),
                    criteriaBuilder.like(root.get(Event_.description), searchText)
            ));
        }
        if (args.getCategories() != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    root.get(Event_.category).get(Category_.id).in(args.getCategories()));
        }
        if (args.getPaid() != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get(Event_.paid), args.getPaid()));
        }
        if (args.getRangeStart() != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThan(root.get(Event_.eventDate), args.getRangeStart()));
        }
        if (args.getRangeEnd() != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThan(root.get(Event_.eventDate), args.getRangeEnd()));
        }
        if (args.getOnlyAvailable() != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.or(
                            criteriaBuilder.equal(root.get(Event_.participantLimit), 0),
                            criteriaBuilder.lessThanOrEqualTo(
                                    root.get(Event_.confirmedRequests),
                                    root.get(Event_.participantLimit).as(Long.class)
                            )
                    )
            );
        }
        Pageable page = Pagination.getPage(args.getFrom(), args.getSize());
        List<Event> events = eventRepository.findAll(spec, page);
        List<EventWithViewsArgs> eventsWithViews = getEventsWithViews(events);

        if (args.getSort() != null) {
            switch (args.getSort()) {
                case EVENT_DATE:
                    eventsWithViews.sort(Comparator.comparing(ea -> ea.getEvent().getEventDate()));
                    break;
                case VIEWS:
                    eventsWithViews.sort(Comparator.comparing(EventWithViewsArgs::getViews));
                    break;
            }
        }
        return eventsWithViews;
    }

    @Override
    public EventWithViewsArgs getEventByPublic(long eventId) {
        Event event = eventRepository.findEventByIdAndState(eventId, EventState.PUBLISHED).orElseThrow(() ->
                new NotFoundException(Event.class, eventId));
        return getEventsWithViews(List.of(event)).stream().findFirst().orElseThrow();
    }

    private List<EventWithViewsArgs> getEventsWithViews(List<Event> events) {
        if (events.size() == 0) {
            return List.of();
        }
        Map<Long, Event> eventMap = events.stream().collect(Collectors.toMap(Event::getId, e -> e));
        Map<String, Long> eventIdUris = eventMap.keySet().stream()
                .collect(Collectors.toMap(id -> String.format("/events/%s", id), id -> id));

        List<ViewStats> stats = statsClient.getViewStats(eventIdUris.keySet(), true);

        Map<Long, Integer> eventViews = stats.stream()
                .collect(Collectors.toMap(vs -> eventIdUris.get(vs.getUri()), ViewStats::getHits));
        return eventMapper.toEventWithViewsArgs(eventMap, eventViews);
    }
}
