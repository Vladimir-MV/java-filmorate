    package ru.yandex.practicum.filmorate.service;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import ru.yandex.practicum.filmorate.exception.ValidationException;
    import ru.yandex.practicum.filmorate.model.User;
    import lombok.extern.slf4j.Slf4j;
    import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
    import ru.yandex.practicum.filmorate.storage.user.UserStorage;
    import java.util.*;

    @Slf4j
    @Service
    public class UserService {
        private UserStorage userStorage;

        @Autowired
        public UserService(InMemoryUserStorage userStorage) { // InMemoryFilmStorage filmStorage
            this.userStorage = userStorage;
        }

        private void getIdUserService(User user){
            userStorage.getIdUserStorage(user);
        }

        public List<User> findAllUserService() {
            List<User> list = userStorage.getAllUsersStorage();
            log.info("Текущее количество пользователей в списке: {}", list.size());
            return list;
        }

        public User findUserByIdService(Optional<Long> id) {
            if (id.isPresent()) {
                User user = userStorage.getUserStorage(id.get());
                log.info("Пользователь по id запросу: {}", user.getName());
                return user;
            }
            throw new NoSuchElementException("Не правильно задан id пользователя! findUserByIdService()");
        }

        public List<User> getUserFriendsListService(Optional < Long > id)  {
            if (id.isPresent()) {
                List<User> list = userStorage.getListFriendsStorage(id.get());
                log.info("Получен список друзей, в количестве {}.", list.size());
                return list;
            }
            throw new NoSuchElementException("Переменная пути указана не верно! getUserFriendsListService()");
        }

        public List<User> getFriendsCommonListService(Optional < Long > id, Optional < Long > otherId) {
            if (id.isPresent() && otherId.isPresent()){
                List<User> list = userStorage.getListOurFriendsStorage(id.get(), otherId.get());
                log.info("Получен список общих друзей, в количестве: {}.", list);
                return list;
            }
            throw new NoSuchElementException("Переменные пути указаны не верно! getFriendsCommonListService()");
        }

        public User deleteUserFriendsService(Optional<Long> id,
                                             Optional<Long> friendId){
            if (id.isPresent() && friendId.isPresent()) {
                User user = userStorage.deleteFriendsStorage(id.get(), friendId.get());
                log.info("Пользователь: {} удален из друзей.", user);
                return user;
            }
            throw new NoSuchElementException("Переменные пути указаны не верно! deleteUserFriendsService()");
        }

        public User createUserService(User user) throws ValidationException {
            if (validationUser(user)) {
                User userInStorage = userStorage.createUserStorage(user);
                log.info("Добавлен пользователь: {}", userInStorage.getName());
                return userInStorage;
            }
            throw new ValidationException("Валидация не пройдена! createUserService()");
        }

        public User putUserService(User user) throws ValidationException {
            if (validationUser(user)) {
                User userInStorage = userStorage.putUserStorage(user);
                log.info("Данные пользователя: {} изменены или добавлены.", userInStorage.getName());
                return userInStorage;
            }
            throw new ValidationException("Валидация не пройдена! putUserService()");
        }

        public User addUserFriendsService(Optional<Long> id,
                                          Optional<Long> friendId) {
            if (id.isPresent() && friendId.isPresent()) {
                User user = userStorage.addFriendsStorage(id.get(), friendId.get());
                log.info("Пользователь id: {} добавлен в друзья.", user);
                return user;
            }
            throw new NoSuchElementException("Переменная указана не верно! putUserService()");
        }

        public boolean validationUser(User user) throws ValidationException {
            for (char ch: user.getLogin().toCharArray()) {
                if (ch == ' ') {
                    throw new ValidationException("Логин не может содержать символ пробела!");
                }
            }
            if (user.getName().isBlank()) {
                user.setName(user.getLogin());
            }
            if (user.getId() == null) {
                getIdUserService(user);
            }
            else if (user.getId() <= 0)  {
                throw new NoSuchElementException ("id не может быть отрицательным!");
            }
            return true;
        }
    }
