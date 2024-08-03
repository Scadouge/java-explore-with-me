package ru.scadouge.stats.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static ru.scadouge.stats.repository.StatsSqlHelper.TABLE_STAT_HITS;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = TABLE_STAT_HITS)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class StatHit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String app;

    @Column(nullable = false)
    private String uri;

    @Column(nullable = false)
    private String ip;

    @Column(nullable = false)
    private LocalDateTime timestamp;
}
