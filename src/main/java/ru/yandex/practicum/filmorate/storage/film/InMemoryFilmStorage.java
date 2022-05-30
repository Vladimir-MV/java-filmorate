    package ru.yandex.practicum.filmorate.storage.film;

    import org.springframework.stereotype.Component;
    import ru.yandex.practicum.filmorate.model.Film;
    import java.util.*;
    import java.util.stream.Collectors;

    @Component
    public class InMemoryFilmStorage implements FilmStorage {

        private Long id = 0L;
        private final List<Film> films = new ArrayList<>();
        private HashMap<Long, Film> filmsBase = new HashMap<>();

        public void getIdFilmStorage(Film film) {
            id = id + 1;
            film.setId(id);
        }

        public List<Film> findAllFilmStorage() {
            for (Film film: filmsBase.values()){
                films.add(film);
            }
            return films;
        }
        public Film getFilmIdStorage(Long id){
            if (!filmsBase.containsKey(id))
                throw new NoSuchElementException("Такого фильма нет! getFilmIdStorage()");
            return filmsBase.get(id);
        }

        public Long addLikeUserFilmStorage(Long id, Long userId){
            if (!filmsBase.containsKey(id))
                throw new NoSuchElementException("Такого фильма нет! addLikeUserFilmStorage()");
            filmsBase.get(id).getLike().add(userId);
            return userId;
        }
        public Long deleteLikeUserStorage(Long id, Long userId){
            if (!filmsBase.get(id).getLike().contains(userId))
                throw new NoSuchElementException("Такого лайка нет! deleteLikeUserStorage()");
            filmsBase.get(id).getLike().remove(userId);
            return userId;
        }

        public Film createFilmStorage(Film film){
            filmsBase.put(film.getId(), film);
            return filmsBase.get(film.getId());
        }

        public Film putFilmStorage(Film film){
            filmsBase.put(film.getId(), film);
            return filmsBase.get(film.getId());
        }

        public List<Film> getPopularFilmStorage(Integer count) {
            if (count == null || count == 0) count = 10;
            List<Film> list = filmsBase.values().stream()
                    .sorted((o1, o2) -> o2.getLike().size() - o1.getLike().size())
                    .limit(count)
                    .collect(Collectors.toList());
            return list;
        }
    }
