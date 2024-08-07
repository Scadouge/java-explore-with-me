package ru.scadouge.stats.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ApiError {
    private String message;

    private String reason;

    private String status;

    private LocalDateTime timestamp;
}
