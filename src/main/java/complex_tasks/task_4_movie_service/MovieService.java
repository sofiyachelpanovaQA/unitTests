package complex_tasks.task_4_movie_service;

import complex_tasks.task_2_user_validator.InvalidUserException;

import java.util.*;

public class MovieService<T extends Number> {
    private Map<Movie, List<Rating<T>>> movieListMap = new HashMap<>();

    public Map<Movie, List<Rating<T>>> getMovieListMap() {
        return Map.copyOf(movieListMap);
    }

    public synchronized void addRating(Movie movie, Rating<T> rating) {
        if (rating != null && rating.getRating() != null && rating.getRating().intValue() <= 10 && rating.getRating().intValue() >= 1) {
            movieListMap.computeIfAbsent(movie, k -> new ArrayList<>()).add(rating);
        } else throw new IllegalArgumentException("Рейтинг должен быть числом не меньше 1 и не больше 10");
    }

    public double getAverageRatingByMovie(Movie movie) {
        List<Rating<T>> ratings = movieListMap.get(movie);

        if (ratings == null || ratings.isEmpty()) {
            throw new IllegalArgumentException("Для данного фильма не найдено добавленных рейтингов");
        }

        return movieListMap.get(movie)
                .stream()
                .mapToDouble(rating -> rating.getRating().doubleValue())
                .average()
                .orElseThrow(
                        () -> new IllegalArgumentException("Для данного фильма не найдено добавленных рейтингов")
                );
    }

    public List<Movie> sortByRating() {
        return movieListMap.keySet()
                .stream()
                .sorted(Comparator.comparing(this::getAverageRatingByMovie).reversed())
                .toList();
    }
}
