package ru.yandex.practicum.filmorate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class FilmorateApplicationTests {

	@Autowired
	TestRestTemplate restTemplate;
	@Autowired
	ObjectMapper mapper;

	@Autowired
	MockMvc mockMvc;

	String url;

	@BeforeEach
	void setUp() {
		url = "http://localhost:8080";
	}

	@Test
	void filmCreateTest() throws Exception {
		Film film = new Film();
		film.setName("nisi eiusmod");
		film.setDescription("adipisicing");
		film.setReleaseDate(LocalDate.of(1967,03, 25));
		film.setDuration(Duration.ofMinutes(100));
		var response = this.restTemplate.postForEntity(url + "/films",
				film, Film.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	void filmFailNameTest() throws Exception {
		Film film = new Film();
		film.setName("");
		film.setDescription("Description");
		film.setReleaseDate(LocalDate.of(1900,03, 25));
		film.setDuration(Duration.ofMinutes(200));
		var response = this.restTemplate.postForEntity(url + "/films", film, Film.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}

	@Test
	void filmFailDescriptionTest() throws Exception {
		Film film = new Film();
		film.setName("Cinema");
		film.setDescription("");
		film.setReleaseDate(LocalDate.of(1905,03, 25));
		film.setDuration(Duration.ofMinutes(150));
		var response = this.restTemplate.postForEntity(url + "/films", film, Film.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}

	@Test
	void filmFailReleaseDateTest() throws Exception {
		Film film = new Film();
		film.setName("Name");
		film.setDescription("Description");
		film.setReleaseDate(LocalDate.of(1890,03, 25));
		film.setDuration(Duration.ofMinutes(200));
		var response = this.restTemplate.postForEntity(url + "/films", film, Film.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}

	@Test
	void filmDurationTest() throws Exception {
		Film film = new Film();
		film.setName("Name");
		film.setDescription("Description");
		film.setReleaseDate(LocalDate.of(1980,03, 25));
		film.setDuration(Duration.ofMinutes(-200));
		var response = this.restTemplate.postForEntity(url + "/films", film, Film.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}

	@Test
	void filmPutTest() throws Exception {
		Film film = new Film();
		film.setId(1L);
		film.setName("nisi eiusmod");
		film.setDescription("adipisicing");
		film.setReleaseDate(LocalDate.of(1967,03, 25));
		film.setDuration(Duration.ofMinutes(100));
		String body = mapper.writeValueAsString(film);
		this.mockMvc.perform(put("/films").content(body).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void filmGetTest() throws Exception {
		var response = this.restTemplate.getForEntity(url + "/films", List.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	void userCreateTest() throws Exception {
		User user = new User();
		user.setLogin("dolore");
		user.setName("Nick Name");
		user.setEmail("mail@mail.ru");
		user.setBirthday(LocalDate.parse("1946-08-20"));
		var response = this.restTemplate.postForEntity(url + "/users", user, User.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	@Test
	void userFailLoginTest() throws Exception {
		User user = new User();
		user.setLogin("dolore ullamco");
		user.setEmail("mail@mail.ru");
		user.setBirthday(LocalDate.parse("1980-08-20"));
		var response = this.restTemplate.postForEntity(url + "/users", user, User.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	@Test
	void userFailNameTest() throws Exception {
		User user = new User();
		user.setLogin("dolore");
		user.setName("");
		user.setEmail("mail@mail.ru");
		user.setBirthday(LocalDate.parse("1946-08-20"));
		var response = this.restTemplate.postForEntity(url + "/users", user, User.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	@Test
	void userFailEmailTest() throws Exception {
		User user = new User();
		user.setLogin("dolore");
		user.setName("Nick Name");
		user.setEmail("mail.ru");
		user.setBirthday(LocalDate.parse("1946-08-20"));
		var response = this.restTemplate.postForEntity(url + "/users", user, User.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	@Test
	void userFailBirthdayTest() throws Exception {
		User user = new User();
		user.setLogin("dolore");
		user.setName("Nick Name");
		user.setEmail("mail@mail.ru");
		user.setBirthday(LocalDate.parse("2440-08-20"));
		var response = this.restTemplate.postForEntity(url + "/users", user, User.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
	}

	@Test
	void userPutTest() throws Exception {
		User user = new User();
		user.setId(2l);
		user.setLogin("dolore");
		user.setName("est adipisicing");
		user.setEmail("mail@yandex.ru");
		user.setBirthday(LocalDate.parse("1946-08-20"));
		String body = mapper.writeValueAsString(user);
		this.mockMvc.perform(put("/users").content(body).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void userGetTest() throws Exception {
		var response = this.restTemplate.getForEntity(url + "/users", List.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

}




