package ru.scadouge.ewm.event.model;

import lombok.*;

import jakarta.persistence.*;
import java.util.List;

import static ru.scadouge.ewm.utils.EwmSqlHelper.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = TABLE_EWM_COMPILATIONS)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = COMPILATION_ID)
    private Long id;

    @Column(name = COMPILATION_TITLE, nullable = false)
    private String title;

    @Column(name = COMPILATION_PINNED, nullable = false)
    private Boolean pinned;

    @ManyToMany
    @JoinTable(
            name = TABLE_EWM_COMPILATION_EVENT,
            joinColumns = @JoinColumn(name = COMPILATION_COMPILATION_ID),
            inverseJoinColumns = @JoinColumn(name = COMPILATION_EVENT_ID))
    private List<Event> events;
}
