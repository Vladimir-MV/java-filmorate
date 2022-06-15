    package ru.yandex.practicum.filmorate.model;

    import lombok.*;
    import javax.validation.constraints.NotBlank;
    import javax.validation.constraints.Size;
    import java.time.LocalDate;
    import java.util.HashSet;
    import java.util.Set;

    @Getter
    @Setter
    @NoArgsConstructor
    public class Film {
        private Long id;
        private Set<Long> like = new HashSet<>();
        @NotBlank
        private String name;
        @NotBlank
        @Size(min = 1, max = 200)
        private String description;
        private LocalDate releaseDate;
        private Integer duration;
        private String genre;
        private String ratingMPA;
    }
