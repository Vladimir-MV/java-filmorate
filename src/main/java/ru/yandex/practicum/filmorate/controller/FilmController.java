    package ru.yandex.practicum.filmorate.controller;

    import lombok.extern.slf4j.Slf4j;
    import org.springframework.web.bind.annotation.*;
    import ru.yandex.practicum.filmorate.exception.ValidationException;
    import ru.yandex.practicum.filmorate.model.Film;

    import java.time.LocalDate;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;

    @RestController
    @RequestMapping("/films")
    @Slf4j
    public class FilmController {
        private Long id = 0l;
        private final List<Film> films = new ArrayList<>();
        private HashMap<Long, Film> filmsBase = new HashMap<>();

        @GetMapping()
        private List<Film> findAll() {
            for (Film film: filmsBase.values()){
                films.add(film);
            }
            log.info("Текущее количество фильмов в списке: {}", filmsBase.size());
            return films;
        }

        @PostMapping()
        private Film create(@RequestBody Film film) {
            if (validationFilm(film)) {
                log.info("Добавлен фильм: {}", film.getName());
                filmsBase.put(film.getId(), film);
                System.out.println(filmsBase);
            }
            return film;
        }
        @PutMapping()
        private Film putFilm(@RequestBody Film film) {
            if (validationFilm(film)) {
                log.info("Данные фильма: {} изменены или добавлены.", film.getName());
                filmsBase.put(film.getId(), film);
                System.out.println(filmsBase);
            }
            return film;
        }
        private void getIdFilm(Film film) {
            id = id + 1;
            film.setId(id);
        }
        private boolean validationFilm (Film film) {
            String exceptionFilm = "";
            try {
                if (film.getName() == null || film.getName().isBlank()) exceptionFilm = "Название фильма не указано! ";

                if (film.getDescription() == null || film.getDescription().isBlank() ||
                        (film.getDescription().length() > 200))
                    exceptionFilm = exceptionFilm + "Описания нет или длина больше 200 символов! ";

                if (film.getReleaseDate() == null || film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28)))
                    exceptionFilm = exceptionFilm + "Дата релиза, не может быть раньше 28 декабря 1895 года! ";

                if (film.getDuration() == null || film.getDuration().isNegative() || film.getDuration().isZero())
                    exceptionFilm = exceptionFilm + "Продолжительность фильма должна быть положительной!";

                if (exceptionFilm.isBlank()) {
                    if (film.getId() == null) getIdFilm(film);
                    return true;
                } else {
                    throw new ValidationException(exceptionFilm);
                }
            } catch (ValidationException e) {
                log.info("Фильм не прошел валидацию! {}", e.getMessage());
                throw new RuntimeException(e.getMessage());
            }
        }

    }
