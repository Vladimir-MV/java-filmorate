    package ru.yandex.practicum.filmorate.exception;

    import org.apache.catalina.connector.Response;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.ControllerAdvice;
    import org.springframework.web.bind.annotation.ExceptionHandler;
    import lombok.extern.slf4j.Slf4j;

    import java.util.NoSuchElementException;

    @Slf4j
    @ControllerAdvice
    public class ErrorHandler {

        @ExceptionHandler(ValidationException.class)
        public ResponseEntity<Response> handleException (ValidationException e){
            log.info("Пользователь не прошел валидацию! {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        @ExceptionHandler(NullPointerException.class)
        public ResponseEntity<Response> handleException (NullPointerException e){
            log.info("NullPointerException! {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        @ExceptionHandler(RuntimeException.class)
        public ResponseEntity<Response> handleException (RuntimeException e){
            log.info("RuntimeException! {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @ExceptionHandler(NoSuchElementException.class)
        public ResponseEntity<Response> handleException (NoSuchElementException e){
            log.info("NoSuchElementException! {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
