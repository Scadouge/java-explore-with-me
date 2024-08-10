package ru.scadouge.ewm.category.model;

import lombok.*;

import jakarta.persistence.*;

import static ru.scadouge.ewm.utils.EwmSqlHelper.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = TABLE_EWM_CATEGORIES)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = CATEGORY_ID)
    private Long id;

    @Column(name = CATEGORY_NAME, unique = true, nullable = false)
    private String name;
}
