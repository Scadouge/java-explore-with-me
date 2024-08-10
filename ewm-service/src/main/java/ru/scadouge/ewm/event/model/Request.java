package ru.scadouge.ewm.event.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import ru.scadouge.ewm.event.dto.enums.RequestState;
import ru.scadouge.ewm.user.model.User;

import jakarta.persistence.*;
import java.sql.Timestamp;

import static ru.scadouge.ewm.utils.EwmSqlHelper.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = TABLE_EWM_REQUESTS)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = REQUEST_ID)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = REQUEST_USER_ID, nullable = false)
    private User requester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = REQUEST_EVENT_ID, nullable = false)
    private Event event;

    @Enumerated(EnumType.STRING)
    @Column(name = REQUEST_STATUS, nullable = false)
    private RequestState status = RequestState.PENDING;

    @CreationTimestamp
    @Column(name = REQUEST_CREATED, nullable = false)
    private Timestamp created;
}
