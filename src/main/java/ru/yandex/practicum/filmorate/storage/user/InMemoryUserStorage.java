package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

    import java.util.*;

    @Slf4j
    @Component
    public class InMemoryUserStorage implements UserStorage {

        private Long id = 0L;
        private List<User> users = new ArrayList<>();
        private HashMap<Long, User> usersBase = new HashMap<>();

        public void getIdUserStorage(User user) {
            id = id + 1;
            user.setId(id);
        }

        public List<User> getAllUsersStorage(){
            for (User user : usersBase.values()) {
                users.add(user);
            }
            return users;
        }

        public User getUserStorage(Long id) {
            if (!usersBase.containsKey(id))
                throw new NoSuchElementException("Такого пользователя нет! getUserStorage()");
            return usersBase.get(id);
        }

        public List<User> getListFriendsStorage(Long id) {
            if (!usersBase.containsKey(id))
                throw new NoSuchElementException("Такого пользователя нет! getListFriendsStorage()");
            List<User> list= new ArrayList<>();
            for (Long idSet: usersBase.get(id).getFriends()) {
               list.add(usersBase.get(idSet));
            }
            return list;
        }

        public List<User> getListOurFriendsStorage(Long id, Long otherId) {
            if (!usersBase.containsKey(id) || !usersBase.containsKey(otherId))
                throw new NoSuchElementException("Такого пользователя нет! getListOurFriendsStorage()");
            List<User> list = new ArrayList<>();
            for (Long idUser: usersBase.get(id).getFriends()) {
                for (Long otherIdUser: usersBase.get(otherId).getFriends()) {
                    if (idUser == otherIdUser) list.add(usersBase.get(otherIdUser));
                }
            }
            return list;
        }
        public User deleteFriendsStorage(Long id, Long friendId) {
            if (!usersBase.get(id).getFriends().contains(friendId))
                throw new NoSuchElementException("Такого пользователя нет! deleteFriendsStorage()");
            User user = usersBase.get(friendId);
            usersBase.get(id).getFriends().remove(friendId);
            return  user;
        }

        public User createUserStorage(User user){
            usersBase.put(user.getId(), user);
            return usersBase.get(user.getId());
        }

        public User putUserStorage(User user) {
            usersBase.put(user.getId(), user);
            return usersBase.get(user.getId());
        }

        public User addFriendsStorage(Long id, Long friendId) {
            if (!usersBase.containsKey(id) || !usersBase.containsKey(friendId))
                throw new NoSuchElementException("Такого пользователя нет! AddFriendsStorage()");
            usersBase.get(id).getFriends().add(friendId);
            usersBase.get(friendId).getFriends().add(id);
            return usersBase.get(friendId);
        }

    }
