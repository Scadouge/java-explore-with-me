package ru.scadouge.ewm.event.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import ru.scadouge.ewm.category.model.Category;
import ru.scadouge.ewm.event.dto.enums.EventState;
import ru.scadouge.ewm.location.Location;
import ru.scadouge.ewm.user.model.User;

import javax.persistence.*;
import java.sql.Timestamp;

import static ru.scadouge.ewm.utils.EwmSqlHelper.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = TABLE_EWM_EVENTS)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = EVENT_ID)
    private Long id;

    @Column(name = EVENT_TITLE, nullable = false)
    private String title;

    @Column(name = EVENT_DESCRIPTION, nullable = false)
    private String description;

    @Column(name = EVENT_ANNOTATION, nullable = false)
    private String annotation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = EVENT_INITIATOR_ID, nullable = false)
    private User initiator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = EVENT_CATEGORY_ID, nullable = false)
    private Category category;

    @Column(name = EVENT_DATE, nullable = false)
    private Timestamp eventDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = EVENT_LOCATION, nullable = false)
    private Location location;

    @Column(name = EVENT_REQUEST_MODERATION, nullable = false)
    private Boolean requestModeration;

    @Column(name = EVENT_PAID, nullable = false)
    private Boolean paid;

    @Column(name = EVENT_PARTICIPANT_LIMIT, nullable = false)
    private Integer participantLimit;

    @Column(name = EVENT_CONFIRMED_REQUESTS, nullable = false)
    private Long confirmedRequests;

    @Column(name = EVENT_STATE, nullable = false)
    @Enumerated(EnumType.STRING)
    private EventState state;

    @Column(name = EVENT_PUBLISHED_ON)
    private Timestamp publishedOn;

    @Column(name = EVENT_CREATED_ON, nullable = false)
    @CreationTimestamp
    private Timestamp createdOn;
}
