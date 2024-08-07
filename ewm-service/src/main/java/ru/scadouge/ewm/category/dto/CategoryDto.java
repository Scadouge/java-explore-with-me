package ru.scadouge.ewm.category.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class CategoryDto {
    @Null
    private Long id;

    @NotBlank
    @Size(min = 1, max = 50)
    private String name;
}
