package ru.scadouge.ewm.event.service;

import ru.scadouge.ewm.event.args.NewCompilationArgs;
import ru.scadouge.ewm.event.args.SearchCompilationsArgs;
import ru.scadouge.ewm.event.args.UpdateCompilationArgs;
import ru.scadouge.ewm.event.model.Compilation;

import java.util.List;

public interface CompilationService {
    Compilation createCompilation(NewCompilationArgs newCompilationArgs);

    void deleteCompilation(long compId);

    Compilation updateCompilation(long compId, UpdateCompilationArgs updateCompilationArgs);

    List<Compilation> getCompilations(SearchCompilationsArgs searchCompilationsArgs);

    Compilation getCompilation(long compId);
}
