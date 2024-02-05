package Task4;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Company {

    String tittle;
    int year;
    List<String> topMovies = new ArrayList<String>();

    public List<String> getTopMovies() {
        return topMovies;
    }

    public void setTopMovies(List<String> topMovies) {
        this.topMovies = topMovies;
    }

    public Company(String tittle, int year) {
        this.tittle = tittle;
        this.year = year;
    }

    public String getTittle() {
        return tittle;
    }

    public int getYear() {
        return year;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return year == company.year && Objects.equals(tittle, company.tittle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tittle, year);
    }

    @Override
    public String toString() {
        return "Название кинокомпании: " + tittle + '\'' +
                ", year=" + year +
                "  Список фильмов " + topMovies +
                '}';
    }

}
