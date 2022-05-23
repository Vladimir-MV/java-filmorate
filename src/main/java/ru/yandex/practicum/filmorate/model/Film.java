    package ru.yandex.practicum.filmorate.model;

    import lombok.*;
    import javax.validation.constraints.NotBlank;
    import javax.validation.constraints.Size;
    import java.time.Duration;
    import java.time.LocalDate;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public class Film {
        private Long id;
        @NotBlank
        private String name;
        @NotBlank
        @Size(min = 1, max = 200)
        private String description;
        private LocalDate releaseDate;
        private Duration duration;

    }
