package complex_tasks.task_4;

import complex_tasks.task_2_user_validator.InvalidUserException;
import complex_tasks.task_4_movie_service.Movie;
import complex_tasks.task_4_movie_service.MovieService;
import complex_tasks.task_4_movie_service.Rating;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

public class MovieServiceTest {

    /*
     * Проверки метода добавления рейтинга:
     * 1. Добавление рейтинга если ключа по фильму еще нет -> длина списка рейтинга = 1, рейтинг есть в списке фильма, добавленный ключ-фильм есть в movieListMap
     * 2. Добавление рейтинга если ключи по фильму есть -> длина списка рейтинга = n+1, рейтинг есть в списке фильма, размер movieListMap = 1 (нет дубля ключа)
     * 3. Corner: рейтинг со значением 1.
     * 4. Corner: рейтинг со значением 10.
     * 5. Negative + corner: 0 —> выбрасывается IllegalArgumentException с текстом, movieListMap не изменился
     * 6. Negative + corner: 11 —> выбрасывается IllegalArgumentException с текстом, movieListMap не изменился
     * 7. Передача null вместо объекта Rating. —> выбрасывается IllegalArgumentException с текстом, movieListMap не изменился
     * 8. Передача Rating с null в поле rating. —> выбрасывается IllegalArgumentException с текстом, movieListMap не изменился
     */

    @Test
    @DisplayName("Добавление рейтинга если ключа по фильму еще нет")
    public void addFirstRating() {
        Movie gladiator = new Movie("Gladiator");
        MovieService<Integer> movieService = new MovieService<>();
        Rating<Integer> rating = new Rating<>(5);

        assertFalse(movieService.getMovieListMap().containsKey(gladiator)); // убедились что фильма еще нет

        movieService.addRating(gladiator, rating); //добавили рейтинг новому фильму
        assertTrue(movieService.getMovieListMap().containsKey(gladiator)); // проверили что фильм появился в movieListMap
        assertEquals(rating, movieService.getMovieListMap().get(gladiator).getFirst()); // проверили что рейтинг появился в списке
        assertEquals(1, movieService.getMovieListMap().get(gladiator).size()); // проверили что размер списка рейтинга = 1
    }

    @Test
    @DisplayName("Добавление рейтинга если ключ по фильму есть ")
    public void addNotFirstRating() {
        Movie gladiator = new Movie("Gladiator");
        MovieService<Integer> movieService = new MovieService<>();
        Rating<Integer> rating5 = new Rating<>(5);
        Rating<Integer> rating6= new Rating<>(6);


        movieService.addRating(gladiator, rating5); //добавили рейтинг новому фильму
        int sizeBefore = movieService.getMovieListMap().get(gladiator).size(); // запомнили размер списка рейтингов
        movieService.addRating(gladiator, rating6); //добавили второй рейтинг
        assertEquals(rating6, movieService.getMovieListMap().get(gladiator).getLast()); // проверили что рейтинг появился в списке
        assertEquals(sizeBefore + 1, movieService.getMovieListMap().get(gladiator).size()); // проверили что размер списка рейтинга увеличился на 1
        assertEquals(1, movieService.getMovieListMap().size()); // проверили что размер movieListMap = 1 (нет дубля ключа)

    }

    @ParameterizedTest
    @DisplayName("Граничные позитивные значения рейтинга")
    @ValueSource(ints = {1, 10})
    public void addRatingPositiveCornerCase(int newRating) {
        Movie gladiator = new Movie("Gladiator");
        MovieService<Integer> movieService = new MovieService<>();
        Rating<Integer> rating = new Rating<>(newRating);

        movieService.addRating(gladiator, rating); //добавили рейтинг новому фильму
        assertEquals(rating, movieService.getMovieListMap().get(gladiator).getLast()); // проверили что рейтинг появился в списке
    }

    @ParameterizedTest
    @DisplayName("Граничные негативные значения рейтинга")
    @ValueSource(ints = {0, 11})
    public void addRatingNegativeCornerCase(int newRating) {
        Movie gladiator = new Movie("Gladiator");
        MovieService<Integer> movieService = new MovieService<>();
        Rating<Integer> rating = new Rating<>(newRating);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> movieService.addRating(gladiator, rating)); //проверили что выбрасывается исключение
        assertEquals("Рейтинг должен быть числом не меньше 1 и не больше 10", exception.getMessage()); //проверили текст ошибки
        assertTrue(movieService.getMovieListMap().isEmpty()); // проверили что movieListMap пустой

    }

    @Test
    @DisplayName("Передача Rating с null в поле rating")
    public void addNullRatingField() {
        Movie gladiator = new Movie("Gladiator");
        MovieService<Integer> movieService = new MovieService<>();
        Rating<Integer> rating = new Rating<>(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> movieService.addRating(gladiator, rating)); //проверили что выбрасывается исключение
        assertEquals("Рейтинг должен быть числом не меньше 1 и не больше 10", exception.getMessage()); //проверили текст ошибки
        assertTrue(movieService.getMovieListMap().isEmpty()); // проверили что movieListMap пустой

    }

    @Test
    @DisplayName("Передача null вместо объекта Rating")
    public void addNullRatingObject() {
        Movie gladiator = new Movie("Gladiator");
        MovieService<Integer> movieService = new MovieService<>();
        Rating<Integer> rating = null;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> movieService.addRating(gladiator, rating)); //проверили что выбрасывается исключение
        assertEquals("Рейтинг должен быть числом не меньше 1 и не больше 10", exception.getMessage()); //проверили текст ошибки
        assertTrue(movieService.getMovieListMap().isEmpty()); // проверили что movieListMap пустой
    }

    /*
     * Проверки метода вычисления среднего рейтинга фильма:
     * 1. Вычисление среднего по нескольким рейтингам одного фильма.
     * 2. Вычисление среднего по одному рейтингу.
     * 3. Корректное вычисление среднего для дробных рейтингов.
     * 4. Для нескольких фильмов учитываются рейтинги только выбранного фильма.
     * 5. Передача фильма, отсутствующего в movieListMap.
     */

    @Test
    @DisplayName("Среднее вычисляется по нескольким рейтингам")
    void getAverageRatingSeveralRatings() {
        MovieService<Integer> movieService = new MovieService<>();
        Movie avatar = new Movie("Avatar");

        movieService.addRating(avatar, new Rating<>(8));
        movieService.addRating(avatar, new Rating<>(6));
        movieService.addRating(avatar, new Rating<>(10));

        assertEquals(8.0, movieService.getAverageRatingByMovie(avatar));
    }

    @Test
    @DisplayName("Среднее вычисляется по одному рейтингу")
    void getAverageRatingOneRating() {
        MovieService<Integer> movieService = new MovieService<>();
        Movie avatar = new Movie("Avatar");

        movieService.addRating(avatar, new Rating<>(7));

        assertEquals(7.0, movieService.getAverageRatingByMovie(avatar));
    }

    @Test
    @DisplayName("Вычисление среднего дробных рейтингов")
    void getAverageRatingDoubleRatings() {
        MovieService<Double> movieService = new MovieService<>();
        Movie avatar = new Movie("Avatar");

        movieService.addRating(avatar, new Rating<>(8.5));
        movieService.addRating(avatar, new Rating<>(9.5));

        assertEquals(9.0, movieService.getAverageRatingByMovie(avatar), 0.0001);
    }

    @Test
    @DisplayName("Учитываются рейтинги только выбранного фильма")
    void getAverageRatingOnlySelectedMovie() {
        MovieService<Integer> movieService = new MovieService<>();

        Movie avatar = new Movie("Avatar");
        Movie matrix = new Movie("Matrix");

        movieService.addRating(avatar, new Rating<>(8));
        movieService.addRating(avatar, new Rating<>(10));

        movieService.addRating(matrix, new Rating<>(1));
        movieService.addRating(matrix, new Rating<>(2));

        assertEquals(9.0, movieService.getAverageRatingByMovie(avatar));
        assertEquals(1.5, movieService.getAverageRatingByMovie(matrix));
    }

    @Test
    @DisplayName("Фильм отсутствует в movieListMap")
    void movieNotFound() {
        MovieService<Integer> movieService = new MovieService<>();
        Movie avatar = new Movie("Avatar");

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> movieService.getAverageRatingByMovie(avatar));
        assertEquals("Для данного фильма не найдено добавленных рейтингов", exception.getMessage());
    }

    /*
    * Проверки метода сортировки по рейтингу
    * 1. несколько фильмов с разными средними рейтингами
    * 2. Пустая коллекция — метод возвращает пустой список
    * 3. Один фильм — возвращается список из одного элемента
    * 4. Одинаковые средние рейтинги — все фильмы присутствуют в результате
    * 5. Дробные рейтинги
     */

    @Test
    @DisplayName("Несколько фильмов с разными средними рейтингами")
    void severalFilmsWithDifferentAverageRatings() {
        MovieService<Integer> movieService = new MovieService<>();
        Movie avatar = new Movie("Avatar"); // average = 8.0
        Movie gladiator = new Movie("Gladiator"); // average = 7.33
        Movie matrix = new Movie("Matrix"); // average = 9.33

        movieService.addRating(avatar, new Rating<>(8));
        movieService.addRating(avatar, new Rating<>(6));
        movieService.addRating(avatar, new Rating<>(10));
        movieService.addRating(gladiator, new Rating<>(5));
        movieService.addRating(gladiator, new Rating<>(7));
        movieService.addRating(gladiator, new Rating<>(10));
        movieService.addRating(matrix, new Rating<>(9));
        movieService.addRating(matrix, new Rating<>(9));
        movieService.addRating(matrix, new Rating<>(10));

        assertEquals(List.of(matrix, avatar, gladiator), movieService.sortByRating());
    }

    @Test
    @DisplayName("Пустая коллекция")
    public void emptyListMap() {
        MovieService<Integer> movieService = new MovieService<>();

        assertEquals(List.of(), movieService.sortByRating());
    }

    @Test
    @DisplayName("Один фильм — возвращается список из одного элемента")
    public void sortOneFilm() {
        MovieService<Integer> movieService = new MovieService<>();
        Movie avatar = new Movie("Avatar");
        movieService.addRating(avatar, new Rating<>(8));

        assertEquals(List.of(avatar), movieService.sortByRating());
    }

    @Test
    @DisplayName("Одинаковые средние рейтинги")
    void identicalRating() {
        MovieService<Integer> movieService = new MovieService<>();
        Movie avatar = new Movie("Avatar");
        Movie gladiator = new Movie("Gladiator");
        Movie matrix = new Movie("Matrix");
        Movie zombie = new Movie("Zombie");

        movieService.addRating(avatar, new Rating<>(8));
        movieService.addRating(avatar, new Rating<>(6));
        movieService.addRating(avatar, new Rating<>(10));
        movieService.addRating(gladiator, new Rating<>(8));
        movieService.addRating(gladiator, new Rating<>(6));
        movieService.addRating(gladiator, new Rating<>(10));
        movieService.addRating(matrix, new Rating<>(8));
        movieService.addRating(matrix, new Rating<>(6));
        movieService.addRating(matrix, new Rating<>(10));

        List<Movie> sortedMovies = movieService.sortByRating();
        assertTrue(sortedMovies.containsAll(List.of(matrix, avatar, gladiator)));
        assertEquals(3, sortedMovies.size());
    }

    @Test
    @DisplayName("Дробные рейтинги")
    void doubleRatings() {
        MovieService<Double> movieService = new MovieService<>();
        Movie avatar = new Movie("Avatar"); // average = 8.25
        Movie gladiator = new Movie("Gladiator"); // average = 7.56
        Movie matrix = new Movie("Matrix"); // average = 9.2

        movieService.addRating(avatar, new Rating<>(8.25));
        movieService.addRating(avatar, new Rating<>(6.6));
        movieService.addRating(avatar, new Rating<>(9.9));
        movieService.addRating(gladiator, new Rating<>(5.1));
        movieService.addRating(gladiator, new Rating<>(7.6));
        movieService.addRating(gladiator, new Rating<>(10.0));
        movieService.addRating(matrix, new Rating<>(9.3));
        movieService.addRating(matrix, new Rating<>(9.2));
        movieService.addRating(matrix, new Rating<>(9.1));

        assertEquals(List.of(matrix, avatar, gladiator), movieService.sortByRating());
    }

}
