package Task3;

public class Movie {

    String tittle;
    int rating;
    String genre;
    String country;
    boolean oscar;

    public Movie(String tittle, int rating, String genre, String country, boolean oscar) {
        this.tittle = tittle;
        this.rating = rating;
        this.genre = genre;
        this.country = country;
        this.oscar = oscar;
    }

    public String getTittle() {
        return tittle;
    }

    public int getRating() {
        return rating;
    }

    public String getGenre() {
        return genre;
    }

    public String getCountry() {
        return country;
    }

    public boolean isOscar() {
        return oscar;
    }

    @Override
    public String toString() {
        return "Movie: " +
                "tittle ='" + tittle + '\'' +
                ", rating = " + rating +
                ", genre ='" + genre + '\'' +
                ", country ='" + country + '\'' +
                ", oscar =" + oscar +
                ' ';
    }

}

