package ru.scadouge.ewm.user.model;

import lombok.*;

import jakarta.persistence.*;

import static ru.scadouge.ewm.utils.EwmSqlHelper.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = TABLE_EWM_USERS)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = USER_ID)
    private Long id;

    @Column(name = USER_NAME, nullable = false)
    private String name;

    @Column(name = USER_EMAIL, unique = true, nullable = false)
    private String email;
}
