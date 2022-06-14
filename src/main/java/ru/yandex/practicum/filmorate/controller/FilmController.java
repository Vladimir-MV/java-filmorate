    package ru.yandex.practicum.filmorate.controller;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;
    import ru.yandex.practicum.filmorate.exception.ValidationException;
    import ru.yandex.practicum.filmorate.model.Film;
    import ru.yandex.practicum.filmorate.service.FilmService;
    import javax.validation.Valid;
    import java.util.List;
    import java.util.Optional;

    @RestController
    @Slf4j
    @RequestMapping("/films")
    public class FilmController extends AbstractController<Film> {

        private FilmService filmService;

        @Autowired
        public FilmController(FilmService filmService) {
            this.filmService = filmService;
        }

        @GetMapping()
        @Override
        protected List<Film> findAll() {
            return filmService.findAllFilmService();
        }

        @GetMapping("/{id}")
        protected Film findFilmId(@PathVariable Optional<Long> id) {
           return filmService.getFilmIdService(id);
        }

        @PutMapping("/{id}/like/{userId}")
        protected Long addLikeUserFilm(@PathVariable Optional<Long> id,
                                       @PathVariable Optional<Long> userId){
            return filmService.addLikeUserFilmService(id, userId);
        }

        @DeleteMapping("/{id}/like/{userId}")
        protected Long deleteLikeUser(@PathVariable Optional<Long> id,
                                         @PathVariable Optional<Long> userId) {
            return filmService.deleteLikeUserService(id, userId);
        }

        @GetMapping("/popular")
        protected List<Film> findFilmRate(@RequestParam(required = false)  Integer count) {
            return filmService.getPopularFilmService(count);
        }

        @PostMapping()
        @Override
        protected Film create(@Valid @RequestBody Film film) throws ValidationException {
            return filmService.createFilmService(film);
        }

        @PutMapping()
        @Override
        protected Film put(@Valid @RequestBody Film film) throws ValidationException {
            return filmService.putFilmService(film);
        }
    }
