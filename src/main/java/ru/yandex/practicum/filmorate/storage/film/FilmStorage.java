    package ru.yandex.practicum.filmorate.storage.film;

    import ru.yandex.practicum.filmorate.model.Film;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;

    public interface FilmStorage {

        Long id = 0L;
        List<Film> films = new ArrayList<>();
        HashMap<Long, Film> filmsBase = new HashMap<>();

        List<Film> findAllFilmStorage();

        Film getFilmIdStorage(Long id);

        Long addLikeUserFilmStorage(Long id, Long userId);

        Long deleteLikeUserStorage(Long id, Long userId);

        Film createFilmStorage(Film film);

        Film putFilmStorage(Film film);

        List<Film> getPopularFilmStorage(Integer count);

        void getIdFilmStorage(Film film);

    }
