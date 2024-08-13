package ru.scadouge.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.scadouge.ewm.event.args.SearchCompilationsArgs;
import ru.scadouge.ewm.event.dto.CompilationDto;
import ru.scadouge.ewm.event.mapper.CompilationMapper;
import ru.scadouge.ewm.event.model.Compilation;
import ru.scadouge.ewm.event.service.CompilationService;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping("/compilations")
@Slf4j
@Validated
@RequiredArgsConstructor
public class CompilationPublicController {
    private final CompilationService compilationService;
    private final CompilationMapper compilationMapper;

    @GetMapping
    public List<CompilationDto> getCompilations(@RequestParam(required = false) Boolean pinned,
                                                @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                @RequestParam(defaultValue = "10") @Positive Integer size) {
        SearchCompilationsArgs args = compilationMapper.toSearchCompilationsArgs(pinned, from, size);
        log.info("PUBLIC: Получение списка подборок args={}", args);
        List<Compilation> compilation = compilationService.getCompilations(args);
        return compilationMapper.toCompilationDto(compilation);
    }

    @GetMapping("/{compId}")
    public CompilationDto getCompilation(@PathVariable long compId) {
        log.info("PUBLIC: Получение подборки compId={}", compId);
        Compilation compilation = compilationService.getCompilation(compId);
        return compilationMapper.toCompilationDto(compilation);
    }
}
