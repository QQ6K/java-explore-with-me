package ru.practicum.models;

import java.util.List;

public class Followers {
    Long id;
    Long user_id;
    List<Long> followers;
    Boolean lock;
    List<Long> blacklist;
}
