package ru.scadouge.ewm.event.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class NewCompilationDto {
    @NotBlank
    @Size(min = 1, max = 50)
    private String title;

    private List<Long> events;

    private Boolean pinned;
}
