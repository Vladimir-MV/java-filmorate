    package ru.yandex.practicum.filmorate.controller;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;
    import ru.yandex.practicum.filmorate.exception.ValidationException;
    import ru.yandex.practicum.filmorate.model.User;
    import ru.yandex.practicum.filmorate.service.UserService;

    import javax.validation.Valid;
    import java.util.List;
    import java.util.Optional;

    @RestController
    @RequestMapping("/users")
    public class UserController extends AbstractController<User> {
        private UserService userService;

        @Autowired
        public UserController(UserService userService) {
            this.userService = userService;
        }

        @GetMapping()
        @Override
        protected List<User> findAll() {
            return userService.findAllUserService();
        }

        @GetMapping("/{id}")
        protected User findUserById(@PathVariable Optional<Long> id) {
            return userService.findUserByIdService(id);
        }

        @GetMapping("/{id}/friends")
        protected List<User> getUserFriendsList(@PathVariable Optional<Long> id) {
            return userService.getUserFriendsListService(id);
        }

        @GetMapping("/{id}/friends/common/{otherId}")
        protected List<User> getFriendsCommonList(@PathVariable Optional<Long> id,
                                                  @PathVariable Optional<Long> otherId) {
            return userService.getFriendsCommonListService(id, otherId);
        }

        @DeleteMapping("/{id}/friends/{friendId}")
        protected User deleteUserFriends(@PathVariable Optional<Long> id,
                                         @PathVariable Optional<Long> friendId) {
            return userService.deleteUserFriendsService(id, friendId);
        }

        @PostMapping()
        @Override
        protected User create(@Valid @RequestBody User user) throws ValidationException {
            return userService.createUserService(user);
        }

        @PutMapping()
        @Override
        protected User put(@Valid @RequestBody User user) throws ValidationException {
            return userService.putUserService(user);
        }

        @PutMapping("/{id}/friends/{friendId}")
        protected User addUserFriends(@PathVariable Optional<Long> id,
                                      @PathVariable Optional<Long> friendId) {
            return userService.addUserFriendsService(id, friendId);
        }
    }
