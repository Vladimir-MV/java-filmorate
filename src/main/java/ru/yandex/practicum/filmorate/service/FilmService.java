    package ru.yandex.practicum.filmorate.service;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import ru.yandex.practicum.filmorate.exception.ValidationException;
    import ru.yandex.practicum.filmorate.model.Film;
    import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
    import lombok.extern.slf4j.Slf4j;
    import java.time.LocalDate;
    import java.util.*;

    @Slf4j
    @Service
    public class FilmService {
        private FilmStorage filmStorage;

        @Autowired
        public FilmService(FilmStorage filmStorage) { // InMemoryFilmStorage filmStorage
            this.filmStorage = filmStorage;
        }

        private void getIdFilmService(Film film) {
            filmStorage.getIdFilmStorage(film);
        }

        public List<Film> findAllFilmService() {
            List<Film> list = filmStorage.findAllFilmStorage();
            log.info("Текущее количество фильмов в списке: {}", list.size());
            return list;
        }

        public Film getFilmIdService(Optional<Long> id) {
            if (id.isPresent()) {
                Film film = filmStorage.getFilmIdStorage(id.get());
                log.info("Фильм по id запросу: {}", film.getName());
                return film;
            }
            throw new NoSuchElementException("Переменная пути указана не верно! getFilmIdService()");
        }

        public Long addLikeUserFilmService(Optional<Long> id, Optional<Long> userId){
            if (id.isPresent() && userId.isPresent()){
            filmStorage.addLikeUserFilmStorage(id.get(), userId.get());
            log.info("Лайк добавлен, пользователя с id: {}", userId.get());
            return userId.get();
            }
            throw new NoSuchElementException("Переменные пути указаны не верно! addLikeUserFilmService()");
        }

        public Long deleteLikeUserService(Optional<Long> id, Optional<Long> userId) {
            if (id.isPresent() && userId.isPresent()) {
                filmStorage.deleteLikeUserStorage(id.get(), userId.get());
                log.info("Лайк удален по запросу пользователя с id: {}", userId.get());
                return userId.get();
            }
            throw new NoSuchElementException("Переменные пути указаны не верно! deleteLikeUserService()");
        }

        public Film createFilmService(Film film) throws ValidationException {
            if (validationFilm(film)) {
                Film filmService = filmStorage.createFilmStorage(film);
                log.info("Добавлен фильм: {}", filmService.getName());
                return filmService;
            }
            throw new ValidationException("Валидация не пройдена! createFilmService()");
        }

        public Film putFilmService(Film film) throws ValidationException {
            if (validationFilm(film)) {
                Film filmService = filmStorage.putFilmStorage(film);
                log.info("Данные фильма: {} изменены или добавлены.", filmService.getName());
                return filmService;
            }
            throw new ValidationException("Валидация не пройдена! putFilmService()");
        }

        public List<Film> getPopularFilmService(Integer count){
            List<Film> list = filmStorage.getPopularFilmStorage(count);
            log.info("Самые популярные фильмы, всего {}", list.size());
            return list;
        }

        public boolean validationFilm(Film film) throws ValidationException {
            if (film.getReleaseDate() == null || film.getReleaseDate().isBefore
                    (LocalDate.of(1895, 12,28)))
                throw new ValidationException("Дата релиза, не может быть раньше 28 декабря 1895 года! ");
            if (film.getDuration() == null || film.getDuration() <= 0)
                throw new ValidationException("Продолжительность фильма указана не верно!");
            if (film.getId() == null) {
                getIdFilmService(film);
            }
            else if (film.getId() <= 0)  {
                throw new NoSuchElementException ("id не может быть отрицательным!");
            }
            return true;
        }
    }
