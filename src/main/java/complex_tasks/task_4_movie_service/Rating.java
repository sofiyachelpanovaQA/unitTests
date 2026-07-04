package complex_tasks.task_4_movie_service;

import java.util.Objects;

public class Rating<T extends Number> {
    private T rating;

    public Rating(T rating) {
        this.rating = rating;
    }

    public T getRating() {
        return rating;
    }
}
