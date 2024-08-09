package ru.scadouge.ewm.event.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.scadouge.ewm.category.model.Category;
import ru.scadouge.ewm.category.repository.CategoryRepository;
import ru.scadouge.ewm.error.ConflictException;
import ru.scadouge.ewm.event.dto.enums.EventState;
import ru.scadouge.ewm.event.dto.enums.RequestState;
import ru.scadouge.ewm.event.model.Event;
import ru.scadouge.ewm.event.model.Request;
import ru.scadouge.ewm.event.service.RequestService;
import ru.scadouge.ewm.event.model.Location;
import ru.scadouge.ewm.user.model.User;
import ru.scadouge.ewm.user.repository.UserRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class RequestRepositoryTest {
    private final RequestService requestService;
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    private final EventRepository eventRepository;

    @Test
    void findRequestsCountByEvent() {
        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);
        User user = userRepository.save(new User(null, "User 1", UUID.randomUUID() + "@mail.com"));
        Category category = categoryRepository.save(new Category(null, "Category " + UUID.randomUUID()));
        Location location = locationRepository.save(new Location(null, "location 1", null, false, 20.0D, 10.0D, 0.0D));
        Event event = eventRepository.save(new Event(null, "title", "desc", "anno",
                user, category, timestamp, location, true, true, 4, 0L,
                EventState.PENDING, null, timestamp));

        Request request1 = requestRepository.save(new Request(null, user, event, RequestState.PENDING, timestamp));
        Request request2 = requestRepository.save(new Request(null, user, event, RequestState.PENDING, timestamp));
        Request request3 = requestRepository.save(new Request(null, user, event, RequestState.PENDING, timestamp));
        Request request4 = requestRepository.save(new Request(null, user, event, RequestState.PENDING, timestamp));
        Request request5 = requestRepository.save(new Request(null, user, event, RequestState.PENDING, timestamp));
        Request request6 = requestRepository.save(new Request(null, user, event, RequestState.PENDING, timestamp));

        assertEquals(0, eventRepository.findById(event.getId()).orElseThrow().getConfirmedRequests());

        requestService.updateRequestsByEventOwner(user.getId(), event.getId(), List.of(request1.getId(), request2.getId()), RequestState.CONFIRMED);

        assertEquals(2, eventRepository.findById(event.getId()).orElseThrow().getConfirmedRequests());

        requestService.updateRequestsByEventOwner(user.getId(), event.getId(), List.of(request3.getId(), request4.getId(), request5.getId()), RequestState.CONFIRMED);

        assertEquals(4, eventRepository.findById(event.getId()).orElseThrow().getConfirmedRequests());

        List<Request> allRequests = requestRepository.findAllByEventId(event.getId());
        assertNotNull(allRequests.stream().filter(rq -> rq.getStatus() == RequestState.REJECTED).findFirst().orElseThrow());

        assertThrows(ConflictException.class,
                () -> requestService.updateRequestsByEventOwner(user.getId(), event.getId(), List.of(request6.getId()), RequestState.CONFIRMED));
    }
}