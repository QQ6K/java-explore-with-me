package ru.practicum.enums;

import ru.practicum.exceptions.BadRequestException;

public enum SortEvent {
    EVENT_DATE,
    VIEWS;

    public static SortEvent from(String sort) {
        for (SortEvent sortEvent : SortEvent.values()) {
            if (sortEvent.name().equalsIgnoreCase(sort)) {
                return sortEvent;
            }
        }
        throw new BadRequestException("Неверное значение сортировки");
    }
}
