package ru.scadouge.ewm.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ApiError {
    private List<String> errors;

    private String message;

    private String reason;

    private String status;

    private LocalDateTime timestamp;
}
