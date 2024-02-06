package Task4;

import java.util.Objects;

public class Movie {

    private String name;

    private int rating;

    private String country;

    public Movie(String name, int rating, String country) {
        this.name = name;
        this.rating = rating;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public int getRating() {
        return rating;
    }

    public String getCountry() {
        return country;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return rating == movie.rating && Objects.equals(name, movie.name) && Objects.equals(country, movie.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, rating, country);
    }

    @Override
    public String toString() {
        return  name;
    }
}
