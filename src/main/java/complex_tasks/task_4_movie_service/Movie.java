package complex_tasks.task_4_movie_service;

public class Movie {
    private final String name;
    private int releaseYear;
    private String director;

    public Movie(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getDirector() {
        return director;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setDirector(String director) {
        this.director = director;
    }
}
