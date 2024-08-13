package ru.scadouge.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.scadouge.ewm.event.args.NewCompilationArgs;
import ru.scadouge.ewm.event.args.UpdateCompilationArgs;
import ru.scadouge.ewm.event.dto.CompilationDto;
import ru.scadouge.ewm.event.dto.NewCompilationDto;
import ru.scadouge.ewm.event.dto.UpdateCompilationRequest;
import ru.scadouge.ewm.event.mapper.CompilationMapper;
import ru.scadouge.ewm.event.model.Compilation;
import ru.scadouge.ewm.event.service.CompilationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin/compilations")
@Slf4j
@RequiredArgsConstructor
public class CompilationAdminController {
    private final CompilationService compilationService;
    private final CompilationMapper compilationMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto createCompilation(@RequestBody @Valid NewCompilationDto newCompilationDto) {
        log.info("ADMIN: Создание новой подборки newCompilationDto={}", newCompilationDto);
        NewCompilationArgs args = compilationMapper.toNewCompilationArgs(newCompilationDto);
        Compilation compilation = compilationService.createCompilation(args);
        return compilationMapper.toCompilationDto(compilation);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable long compId) {
        log.info("ADMIN: Удаление подборки compId={}", compId);
        compilationService.deleteCompilation(compId);
    }

    @PatchMapping("/{compId}")
    public CompilationDto updateCompilation(@PathVariable long compId,
                                            @RequestBody @Valid UpdateCompilationRequest updateCompilationRequest) {
        log.info("ADMIN: Обновление подборки compId={}, updateCompilationRequest={}", compId, updateCompilationRequest);
        UpdateCompilationArgs args = compilationMapper.toUpdateCompilationArgs(updateCompilationRequest);
        Compilation compilation = compilationService.updateCompilation(compId, args);
        return compilationMapper.toCompilationDto(compilation);
    }
}
