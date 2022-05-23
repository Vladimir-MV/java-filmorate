package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.util.List;


public abstract class AbstractController<T> {

    abstract List<T> findAll();
    abstract T create(T variable) throws ValidationException;
    abstract T put(T variable) throws ValidationException;
}
