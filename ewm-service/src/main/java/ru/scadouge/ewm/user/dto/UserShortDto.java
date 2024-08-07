package ru.scadouge.ewm.user.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class UserShortDto {
    private Long id;

    private String name;
}
