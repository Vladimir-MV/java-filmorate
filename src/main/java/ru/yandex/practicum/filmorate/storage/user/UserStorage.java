    package ru.yandex.practicum.filmorate.storage.user;

    import ru.yandex.practicum.filmorate.model.User;

    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;

    public interface UserStorage {
        final Long id = 0l;
        final List<User> users = new ArrayList<>();
        HashMap<Long, User> usersBase = new HashMap<>();

        void getIdUserStorage(User user);

        List<User> getAllUsersStorage();

        User getUserStorage(Long id);

        List<User> getListFriendsStorage(Long id);

        List<User> getListOurFriendsStorage(Long id, Long otherId);

        User addFriendsStorage(Long id, Long friendId);

        User deleteFriendsStorage(Long id, Long friendId);

        User createUserStorage(User user);

        User putUserStorage(User user);

    }

