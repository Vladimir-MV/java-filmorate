    package ru.yandex.practicum.filmorate.model;

    import lombok.*;
    import javax.validation.constraints.Email;
    import javax.validation.constraints.NotBlank;
    import javax.validation.constraints.Past;
    import java.time.LocalDate;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public class User {
        private Long id;
        @NotBlank
        @Email
        private String email;
        @NotBlank
        private String login;
        private String name;
        @Past
        private LocalDate birthday;
    }