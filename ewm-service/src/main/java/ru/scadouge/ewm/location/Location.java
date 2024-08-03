package ru.scadouge.ewm.location;

import lombok.*;

import javax.persistence.*;

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

    @Column(name = LOCATION_LAT)
    private Double lat;

    @Column(name = LOCATION_LON)
    private Double lon;
}
