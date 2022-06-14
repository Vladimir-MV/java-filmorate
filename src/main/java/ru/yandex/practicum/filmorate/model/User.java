    package ru.yandex.practicum.filmorate.model;

    import lombok.*;
    import javax.validation.constraints.*;
    import java.time.LocalDate;
    import java.util.HashSet;
    import java.util.Set;

    @Getter
    @Setter
    @NoArgsConstructor
    public class User {
        private Long id;
        private Set<Long> friends = new HashSet<>();
        @NotBlank
        @Email
        private String email;
        @NotBlank
        private String login;
        private String name;
        @Past
        private LocalDate birthday;
    }
