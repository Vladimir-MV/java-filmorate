    package ru.yandex.practicum.filmorate.controller;

    import lombok.extern.slf4j.Slf4j;
    import org.springframework.web.bind.annotation.*;
    import ru.yandex.practicum.filmorate.exception.ValidationException;
    import ru.yandex.practicum.filmorate.model.User;

    import org.apache.catalina.connector.Response;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;

    import javax.validation.Valid;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;

    @RestController
    @RequestMapping("/users")
    @Slf4j
    public class UserController extends AbstractController<User> {
        private Long id = 0l;
        private String exceptionUser = "";
        private final List<User> users = new ArrayList<>();
        private HashMap<Long, User> usersBase = new HashMap<>();


        @GetMapping()
        @Override
        protected List<User> findAll() {
            for (User user : usersBase.values()) {
                users.add(user);
            }
            log.info("Текущее количество пользователей в списке: {}", usersBase.size());
            return users;
        }

        @PostMapping()
        @Override
        protected User create(@Valid @RequestBody User user) throws ValidationException {
            if (validationUser(user)) {
                log.info("Добавлен пользователь: {}", user.getName());
                usersBase.put(user.getId(), user);
            }
            return user;
        }

        @PutMapping()
        @Override
        protected User put(@Valid @RequestBody User user) throws ValidationException {
            if (validationUser(user)) {
                log.info("Данные пользователя: {} изменены или добавлены.", user.getName());
                usersBase.put(user.getId(), user);
            }
            return user;
        }

        private void getIdUser(User user) {
            id = id + 1;
            user.setId(id);
        }

        private boolean validationUser(User user) throws ValidationException {
            for (char ch: user.getLogin().toCharArray()) {
                if (ch == ' ') {
                    throw new ValidationException("Логин не может содержать символ пробела!");
                }
            }
            if (user.getName().isBlank()) {
                user.setName(user.getLogin());
                throw new ValidationException("Имя не указано!");
            }
            if (user.getId() == null) getIdUser(user);
            return true;
        }

        @ExceptionHandler(ValidationException.class)
        public ResponseEntity<Response> handleException(ValidationException e) {
            log.info("Пользователь не прошел валидацию! {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        @ExceptionHandler(NullPointerException.class)
        public ResponseEntity<Response> handleException2(NullPointerException e) {
            log.info("Пользователь не прошел валидацию! {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        @ExceptionHandler(RuntimeException.class)
        public ResponseEntity<Response> handleException3(RuntimeException e) {
            log.info("Пользователь не прошел валидацию! {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
