package ru.practicum.adminpart.compilations.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.adminpart.compilations.interfaces.AdminCompilationService;
import ru.practicum.adminpart.events.interfaces.AdminEventService;
import ru.practicum.exceptions.BadRequestException;
import ru.practicum.exceptions.CrudException;
import ru.practicum.exceptions.WrongObjectException;
import ru.practicum.mappers.CompilationMapper;
import ru.practicum.models.Compilation;
import ru.practicum.models.CompilationDto;
import ru.practicum.models.Event;
import ru.practicum.models.NewCompilationDto;
import ru.practicum.privatepart.events.interfaces.PrivateEventService;
import ru.practicum.repositories.CompilationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AdminCompilationServiceImpl implements AdminCompilationService {

    private final AdminEventService adminEventService;

    private final CompilationRepository compilationRepository;

    @Override
    public Compilation findById(Long compId) {
        log.info("Поиск подборки id = {}", compId);
        return compilationRepository.findById(compId).orElseThrow(() -> new WrongObjectException("Отсутствует подборка с id = " + compId)
        );
    }

    @Override
    @Transactional
    public CompilationDto createCompilation(NewCompilationDto compilationDto) {
        List<Event> events = adminEventService
                .findEventsByIds(compilationDto.getEvents().stream().collect(Collectors.toList()));
        Compilation compilation = compilationRepository
                .save(CompilationMapper.toCompilation(compilationDto, events));
        log.debug("Создание подборки id = " + compilation.getId());
        return CompilationMapper.toDto(compilation);
    }

    @Override
    @Transactional
    public void deleteCompilation(Long compId) {
        Compilation compilation = this.findById(compId);
        log.debug("Удаление подборки id = {}", compId);
        compilationRepository.deleteById(compId);

    }

    @Override
    @Transactional
    public void deleteEventFromCompilation(Long compId, Long eventId) {
        Compilation compilation = this.findById(compId);
        Event event = adminEventService.findEventById(eventId);
        List<Event> events = new ArrayList<>(compilation.getEvents());
        if (!events.contains(event)) {
            throw new BadRequestException("Событие отсутствует в подборке событий");
        }
        events.remove(event);
        compilation.setEvents(events);
        compilationRepository.save(compilation);
        log.debug("Событие id = {} удалено", eventId);
    }

    @Override
    @Transactional
    public void addEventToCompilation(Long compId, Long eventId) {
        Compilation compilation = this.findById(compId);
        Event event = adminEventService.findEventById(eventId);
        List<Event> events = compilation.getEvents().stream().collect(Collectors.toList());
        if (events.contains(event)) {
            throw new BadRequestException("Событие уже в подборке событий");
        }
        events.add(event);
        compilation.setEvents(events);
        compilationRepository.save(compilation);
        log.debug("Событие id={} добавлено в подборку id{}", compId, eventId);
    }

    @Transactional
    @Override
    public void deletePinned(Long compId) {
        Compilation compilation = this.findById(compId);
        if (!compilation.getPinned()) {
            throw new BadRequestException("Подборка не в закрепе");
        }
        compilation.setPinned(false);
        compilationRepository.save(compilation);
        log.debug("Подборка припинена в закреп id = " + compId);
    }

    @Override
    @Transactional
    public void addPinned(Long compId) {
        Compilation compilation = this.findById(compId);
        if (compilation.getPinned()) {
            throw new CrudException("Подборка уже в закрепе");
        }
        compilation.setPinned(true);
        compilationRepository.save(compilation);
        log.debug("Подборка событий припинена в закреп id = " + compId);
    }
}
