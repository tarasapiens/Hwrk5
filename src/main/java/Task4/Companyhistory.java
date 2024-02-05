package Task4;

import java.util.ArrayList;
import java.util.List;

public class Companyhistory {
    public static void main(String[] args) {

        // с вложенным списком проблемы. много гуглил, но не нашел решения
        // как вносить значения в список, который внутри другого списка

        List<Company> companies = new ArrayList<>();

        companies.add(new Company("Bazelevs", 1994)); // Ночной дозор Дневной дозор Омобо опасен
        companies.add(new Company("Amedia", 2002)); // Закрытая школа Екатерина Луна
        companies.add(new Company("Warner Bros.", 1923)); //Зеленая миля Идеальный шторм Готика
        System.out.println(companies.get(0));
        System.out.println(companies.get(1));
        System.out.println(companies.get(2));






    }
}

