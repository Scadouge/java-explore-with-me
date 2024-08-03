package ru.scadouge.ewm.event.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class CompilationDto {
    private Long id;

    private String title;

    private Boolean pinned;

    private List<EventShortDto> events;
}
