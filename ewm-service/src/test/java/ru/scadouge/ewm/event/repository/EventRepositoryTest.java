package ru.scadouge.ewm.event.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.scadouge.ewm.category.model.Category;
import ru.scadouge.ewm.category.repository.CategoryRepository;
import ru.scadouge.ewm.event.args.AdminSearchEventsArgs;
import ru.scadouge.ewm.event.dto.enums.EventState;
import ru.scadouge.ewm.event.model.Event;
import ru.scadouge.ewm.event.service.EventService;
import ru.scadouge.ewm.location.Location;
import ru.scadouge.ewm.user.model.User;
import ru.scadouge.ewm.user.repository.UserRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class EventRepositoryTest {
    private final EventService eventService;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;

    @Test
    void findAll() {
        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);
        User user1 = userRepository.save(new User(null, "User 1", UUID.randomUUID() + "@mail.com"));
        User user2 = userRepository.save(new User(null, "User 2", UUID.randomUUID() + "@mail.com"));
        Category category = categoryRepository.save(new Category(null, "Category " + UUID.randomUUID()));
        Location location = locationRepository.save(new Location(null, "location 1", 20.0D, 10.0D));
        eventRepository.save(new Event(null, "title1", "desc", "anno",
                user2, category, timestamp, location, true, true, 4, 0L,
                EventState.PENDING, null, timestamp));
        eventRepository.save(new Event(null, "title2", "desc", "anno",
                user1, category, Timestamp.valueOf(now.plusYears(5)), location, true, true, 4, 0L,
                EventState.CANCELED, null, timestamp));
        eventRepository.save(new Event(null, "title3", "desc", "anno",
                user1, category, Timestamp.valueOf(now.plusYears(2)), location, true, true, 4, 0L,
                EventState.PENDING, null, timestamp));

        assertEquals(3, eventService.getAllEventsByAdmin(
                AdminSearchEventsArgs.builder().from(0).size(10).build()).size());
        assertEquals(1, eventService.getAllEventsByAdmin(
                AdminSearchEventsArgs.builder().from(0).size(1).build()).size());
        assertEquals(2, eventService.getAllEventsByAdmin(
                AdminSearchEventsArgs.builder().from(0).size(2).build()).size());
        assertEquals(1, eventService.getAllEventsByAdmin(
                AdminSearchEventsArgs.builder().from(2).size(2).build()).size());
        assertEquals(1, eventService.getAllEventsByAdmin(
                AdminSearchEventsArgs.builder().states(List.of(EventState.CANCELED.toString())).from(0).size(10).build()).size());
        assertEquals(3, eventService.getAllEventsByAdmin(
                AdminSearchEventsArgs.builder().categories(List.of(category.getId())).from(0).size(10).build()).size());
        assertEquals(1, eventService.getAllEventsByAdmin(
                AdminSearchEventsArgs.builder().users(List.of(user2.getId())).from(0).size(10).build()).size());
        assertEquals(0, eventService.getAllEventsByAdmin(
                AdminSearchEventsArgs.builder().categories(List.of(category.getId() + 1)).from(0).size(10).build()).size());
        assertEquals(1, eventService.getAllEventsByAdmin(
                AdminSearchEventsArgs.builder().rangeStart(Timestamp.valueOf(now.plusYears(3))).from(0).size(10).build()).size());
        assertEquals(2, eventService.getAllEventsByAdmin(
                AdminSearchEventsArgs.builder().rangeEnd(Timestamp.valueOf(now.plusYears(3))).from(0).size(10).build()).size());
    }
}