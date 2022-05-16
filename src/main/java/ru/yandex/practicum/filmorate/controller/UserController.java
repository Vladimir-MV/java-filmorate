    package ru.yandex.practicum.filmorate.controller;

    import lombok.extern.slf4j.Slf4j;
    import org.springframework.web.bind.annotation.*;
    import ru.yandex.practicum.filmorate.exception.ValidationException;
    import ru.yandex.practicum.filmorate.model.User;

    import java.time.LocalDate;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;

    @RestController
    @RequestMapping("/users")
    @Slf4j
    public class UserController {
        private Long id = 0l;
        private final List<User> users = new ArrayList<>();
        private HashMap<Long, User> usersBase = new HashMap<>();

        @GetMapping()
        private List<User> findAll() {
            for (User user: usersBase.values()){
                users.add(user);
            }
            log.info("Текущее количество пользователей в списке: {}", usersBase.size());
            return users;
        }

        @PostMapping()
        private User create(@RequestBody User user) {
            if (validationUser(user)) {
                log.info("Добавлен пользователь: {}", user.getName());
                usersBase.put(user.getId(), user);
                System.out.println(usersBase);
            }
            return user;
        }

        @PutMapping()
        private User putUser(@RequestBody User user) {
            if (validationUser(user)) {
                log.info("Данные пользователя: {} изменены или добавлены.", user.getName());
                usersBase.put(user.getId(), user);
                System.out.println(usersBase);
            }
            return user;
        }

        private void getIdUser(User user) {
            id = id + 1;
            user.setId(id);
        }

        private boolean validationUser (User user) {
            String exceptionUser = "";
            try {
                if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@"))
                    exceptionUser = "Электронная почта указана не верно! ";

                if (user.getLogin() == null || user.getLogin().isBlank() || user.getLogin().contains(" "))
                    exceptionUser = exceptionUser + "Логин не может быть пустым и содержать символ пробела! ";

                if (user.getBirthday() == null || user.getBirthday().isAfter(LocalDate.now()))
                    exceptionUser = exceptionUser + "Дата рождения не может быть в будущем! ";

                if (user.getName() == null || user.getName().isBlank()) {
                    if (!user.getLogin().isBlank() && !user.getLogin().contains(" ")) {
                        exceptionUser = exceptionUser + "Имя не указано!";
                        user.setName(user.getLogin());
                        if (user.getId() == null) getIdUser(user);
                        usersBase.put(user.getId(), user);
                        System.out.println(usersBase);
                    }
                }
                if (exceptionUser.isEmpty()) {
                    if (user.getId() == null) getIdUser(user);
                    return true;
                } else {
                    throw new ValidationException(exceptionUser);
                }
            } catch (ValidationException e) {
                log.info("Пользователь не прошел валидацию! {}", e.getMessage());
                throw new RuntimeException(e.getMessage());
            }

        }

    }
