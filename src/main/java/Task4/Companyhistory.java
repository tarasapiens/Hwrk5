package Task4;

import java.util.ArrayList;
import java.util.List;

public class Companyhistory {
    public static void main(String[] args) {

        List<Company> companies = new ArrayList<>();

        List<Movie> movBaz = new ArrayList<>();
        movBaz.add(new Movie("Ночной дозор", 7, "Russia"));
        movBaz.add(new Movie("Дневной дозор", 6, "Russia"));
        movBaz.add(new Movie("Особо опасен", 4, "Russia"));

        List<Movie> moAm = new ArrayList<>();
        moAm.add(new Movie("Закрытая школа", 5, "Russia"));
        moAm.add(new Movie("Екатерина", 3, "Russia"));
        moAm.add(new Movie("Луна", 6, "Russia"));

        List<Movie> moWB = new ArrayList<>();
        moWB.add(new Movie("Зеленая миля", 9, "USA"));
        moWB.add(new Movie("Идеальный шторм", 8, "USA"));
        moWB.add(new Movie("Готика", 5, "USA"));

        Company companiesBaz = new Company("Bazelevs", 1994, movBaz);
        Company companyAm = new Company("Amedia", 2002, moAm);
        Company companyWB = new Company("Warner Bros.", 1923, moWB);

        companies.add(companyAm);
        companies.add(companiesBaz);
        companies.add(companyWB);

        for (int i = 0; i < companies.size(); i++) {

            System.out.println(companies.get(i));

        }





    }
}

