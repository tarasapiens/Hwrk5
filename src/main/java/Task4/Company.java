package Task4;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Company {

    private String tittle;
    private int year;
    private List<Movie> topMovies;

    public Company(String tittle, int year, List<Movie> topMovies) {
        this.tittle = tittle;
        this.year = year;
        this.topMovies = topMovies;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Movie> getTopMovies() {
        return topMovies;
    }

    public void setTopMovies(List<Movie> topMovies) {
        this.topMovies = topMovies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return year == company.year && Objects.equals(tittle, company.tittle) && Objects.equals(topMovies, company.topMovies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tittle, year, topMovies);
    }

    @Override
    public String toString() {
        return "Название компании: " + tittle +
                "  Топ фильмов: " + topMovies;
    }
}
