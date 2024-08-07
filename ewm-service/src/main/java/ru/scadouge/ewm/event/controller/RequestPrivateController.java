package ru.scadouge.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.scadouge.ewm.event.args.NewRequestArgs;
import ru.scadouge.ewm.event.dto.ParticipationRequestDto;
import ru.scadouge.ewm.event.mapper.RequestMapper;
import ru.scadouge.ewm.event.model.Request;
import ru.scadouge.ewm.event.service.RequestService;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/requests")
@Slf4j
@RequiredArgsConstructor
public class RequestPrivateController {
    private final RequestService requestService;
    private final RequestMapper requestMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto createRequest(@PathVariable Long userId,
                                                 @RequestParam Long eventId) {
        log.info("PRIVATE: Создание нового запроса на участие userId={}, eventId={}", userId, eventId);
        Request request = requestService.createUserRequest(NewRequestArgs.builder().userId(userId).eventId(eventId).build());
        return requestMapper.toParticipationRequestDto(request);
    }

    @GetMapping
    public List<ParticipationRequestDto> getRequests(@PathVariable Long userId) {
        log.info("PRIVATE: Получение всех запросов на участие userId={}", userId);
        List<Request> requests = requestService.getUserRequests(userId);
        return requestMapper.toParticipationRequestDto(requests);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable Long userId,
                                                 @PathVariable Long requestId) {
        log.info("PRIVATE: Отмена запроса на участие userId={}, requestId={}", userId, requestId);
        Request request = requestService.cancelUserRequest(userId, requestId);
        return requestMapper.toParticipationRequestDto(request);
    }
}
