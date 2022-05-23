    package ru.yandex.practicum.filmorate.controller;

    import lombok.extern.slf4j.Slf4j;
    import org.apache.catalina.connector.Response;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.client.HttpServerErrorException;
    import ru.yandex.practicum.filmorate.exception.ValidationException;
    import ru.yandex.practicum.filmorate.model.Film;

    import javax.validation.Valid;
    import java.time.LocalDate;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;

    @RestController
    @RequestMapping("/films")
    @Slf4j
    public class FilmController extends AbstractController<Film> {
        private Long id = 0L;
        private String exceptionFilm = "";
        private final List<Film> films = new ArrayList<>();
        private HashMap<Long, Film> filmsBase = new HashMap<>();

        @GetMapping()
        @Override
        protected List<Film> findAll() {
            for (Film film: filmsBase.values()){
                films.add(film);
            }
            log.info("Текущее количество фильмов в списке: {}", filmsBase.size());
            return films;
        }

        @PostMapping()
        @Override
        protected Film create(@Valid @RequestBody Film film) throws ValidationException {
            if (validationFilm(film)) {
                log.info("Добавлен фильм: {}", film.getName());
                filmsBase.put(film.getId(), film);
            }
            return film;
        }

        @PutMapping()
        @Override
        protected Film put(@Valid @RequestBody Film film) throws ValidationException {
            if (validationFilm(film)) {
                log.info("Данные фильма: {} изменены или добавлены.", film.getName());
                filmsBase.put(film.getId(), film);
            }
            return film;
        }

        private void getIdFilm(Film film) {
            id = id + 1;
            film.setId(id);
        }
        private boolean validationFilm(Film film) throws ValidationException {
            if (film.getReleaseDate() == null || film.getReleaseDate().isBefore
                    (LocalDate.of(1895, 12,28)))
                throw new ValidationException("Дата релиза, не может быть раньше 28 декабря 1895 года! ");
            if (film.getDuration() == null || film.getDuration() <= 0)
                throw new ValidationException("Продолжительность фильма указана не верно!");
            if (film.getId() == null) {
                getIdFilm(film);
            }
            else if (film.getId() <= 0)  {
                throw new ValidationException ("id не может быть отрицательным!");
            }
            return true;
        }


        @ExceptionHandler(ValidationException.class)
        public ResponseEntity<Response> handleException(ValidationException e) {
            log.info("Пользователь не прошел валидацию, ValidationException! {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        @ExceptionHandler(NullPointerException.class)
        public ResponseEntity<Response> handleException2(NullPointerException e) {
            log.info("Пользователь не прошел валидацию, NullPointerException! {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @ExceptionHandler(RuntimeException.class)
        public ResponseEntity<Response> handleException3(RuntimeException e) {
            log.info("Пользователь не прошел валидацию, RuntimeException! {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
