package ru.scadouge.ewm.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.scadouge.ewm.error.ConflictException;
import ru.scadouge.ewm.error.NotFoundException;
import ru.scadouge.ewm.event.args.NewRequestArgs;
import ru.scadouge.ewm.event.dto.enums.EventState;
import ru.scadouge.ewm.event.dto.enums.RequestState;
import ru.scadouge.ewm.event.mapper.RequestMapper;
import ru.scadouge.ewm.event.model.Event;
import ru.scadouge.ewm.event.model.Request;
import ru.scadouge.ewm.event.repository.EventRepository;
import ru.scadouge.ewm.event.repository.RequestRepository;
import ru.scadouge.ewm.user.model.User;
import ru.scadouge.ewm.user.repository.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RequestMapper requestMapper;

    @Override
    @Transactional
    public Request createUserRequest(NewRequestArgs args) {
        User user = userRepository.findById(args.getUserId())
                .orElseThrow(() -> new NotFoundException(User.class, args.getUserId()));
        Event event = eventRepository.findById(args.getEventId())
                .orElseThrow(() -> new NotFoundException(Event.class, args.getEventId()));
        if (event.getParticipantLimit() != 0 && event.getParticipantLimit() - event.getConfirmedRequests() <= 0) {
            throw new ConflictException(String.format("Достигнут лимит участников для события eventId=%s",
                    event.getId()));
        }
        if (event.getState() != EventState.PUBLISHED) {
            throw new ConflictException(String.format("Нельзя создать запрос в неопубликованном событии eventId=%s",
                    event.getId()));
        }
        if (Objects.equals(event.getInitiator().getId(), user.getId())) {
            throw new ConflictException("Нельзя создать запрос на участие в своем событии");
        }
        if (requestRepository.findByRequesterIdAndEventId(user.getId(), event.getId()).isPresent()) {
            throw new ConflictException(String.format("Запрос от пользователя id=%s на событие i=%s уже существует",
                    user.getId(), event.getId()));
        }

        Request request = requestMapper.toModel(user, event);
        if (event.getParticipantLimit() == 0 || !event.getRequestModeration()) {
            request.setStatus(RequestState.CONFIRMED);
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            eventRepository.save(event);
        }
        return requestRepository.save(request);
    }

    @Override
    public List<Request> getUserRequests(long userId) {
        return requestRepository.findAllByRequesterId(userId);
    }

    @Override
    public Request cancelUserRequest(long userId, long requestId) {
        Request request = requestRepository.findById(requestId).orElseThrow(() -> new NotFoundException(Request.class, requestId));
        if (request.getRequester().getId() != userId) {
            throw new NotFoundException(Request.class, request.getRequester().getId());
        }
        request.setStatus(RequestState.CANCELED);
        return requestRepository.save(request);
    }

    @Override
    public List<Request> getRequestsByEventOwner(long userId, long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(Event.class, eventId));
        if (event.getInitiator().getId() != userId) {
            throw new NotFoundException(Event.class, eventId);
        }
        return requestRepository.findAllByEventId(eventId);
    }

    @Override
    @Transactional
    public List<Request> updateRequestsByEventOwner(long userId, long eventId, List<Long> requestIds, RequestState status) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(Event.class, eventId));
        if (event.getInitiator().getId() != userId) {
            throw new NotFoundException(Event.class, eventId);
        }
        if (event.getParticipantLimit() == 0 || !event.getRequestModeration()) {
            return List.of();
        }
        final int freeRequests = (int) (event.getParticipantLimit() - event.getConfirmedRequests());
        if (freeRequests <= 0) {
            throw new ConflictException("Достигнут лимит заявок");
        }
        List<Request> requests = requestRepository.findAllByIdInAndStatus(requestIds, RequestState.PENDING);

        AtomicInteger freeRequestsLeft = new AtomicInteger(freeRequests);
        AtomicLong confirmedRequests = new AtomicLong(event.getConfirmedRequests());
        requests.forEach(rq -> {
            if (freeRequestsLeft.getAndDecrement() > 0) {
                rq.setStatus(status);
                confirmedRequests.incrementAndGet();
            } else {
                rq.setStatus(RequestState.REJECTED);
            }
        });
        event.setConfirmedRequests(confirmedRequests.get());

        return requests;
    }
}
