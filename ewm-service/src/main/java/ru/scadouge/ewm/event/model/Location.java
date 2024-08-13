package ru.scadouge.ewm.event.model;

import lombok.*;

import jakarta.persistence.*;
import java.sql.Timestamp;

import static ru.scadouge.ewm.utils.EwmSqlHelper.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = TABLE_EWM_LOCATIONS)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = LOCATION_ID)
    private Long id;

    @Column(name = LOCATION_NAME)
    private String name;

    @Column(name = LOCATION_EXPIRATION_DATE)
    private Timestamp expirationDate;

    @Column(name = LOCATION_PERMANENT)
    private Boolean permanent;

    @Column(name = LOCATION_LAT)
    private Double lat;

    @Column(name = LOCATION_LON)
    private Double lon;

    @Column(name = LOCATION_RADIUS)
    private Double radius;
}
