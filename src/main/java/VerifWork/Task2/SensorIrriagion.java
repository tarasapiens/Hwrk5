package VerifWork.Task2;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class SensorIrriagion {
    public static void main(String[] args) {
        Random randome = new Random();
        int humidity = randome.nextInt(101);

        String irrigatorday = "30.07.2024";
        LocalDate irri = LocalDate.parse(irrigatorday, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        int monthIrr = irri.getMonthValue();
        int dayIrr = irri.getDayOfMonth();
        System.out.println("Дата последнего полива твоего кактуса: " + "Месяц - " + monthIrr + " " + "Число - " + " " + dayIrr);

     //   january = 1;
     //   february = 2;
     //   march = 3;
     //   april = 4;
     //   may = 5;
     //   june = 6;
     //   july = 7;
     //   august = 8;
     //   september = 9;
     //   october = 10;
     //   november = 11;
     //   december = 12;

        switch (monthIrr){

            //зима
            case 2 :
                System.out.println("Рекомендуемая дата полива кактуса" + " " + irri.plusDays(30));
                break;

            case 1 :
                System.out.println("Рекомендуемая дата полива кактуса" + " " + irri.plusDays(30));
                break;

            case 12 :
                System.out.println("Рекомендуемая дата полива кактуса" + " " + irri.plusDays(30));
                break;

            // осень
            case 9 :
                System.out.println("Рекомендуемая дата полива кактуса" + " " + irri.plusDays(7));
                break;

            case 10 :
                System.out.println("Рекомендуемая дата полива кактуса" + " " + irri.plusDays(7));
                break;

            case 11 :
                System.out.println("Рекомендуемая дата полива кактуса" + " " + irri.plusDays(7));
                break;

            // весна
            case 3:
                System.out.println("Рекомендуемая дата полива кактуса" + " " + irri.plusDays(7));
                break;

            case 4 :
                System.out.println("Рекомендуемая дата полива кактуса" + " " + irri.plusDays(7));
                break;

            case 5 :
                System.out.println("Рекомендуемая дата полива кактуса" + " " + irri.plusDays(7));

            // лето
            case 6 :
                System.out.println("Рекомендуемая дата полива кактуса" + " " + irri.plusDays(2));
                break;

            case 7 :
                System.out.println("Рекомендуемая дата полива кактуса" + " " + irri.plusDays(2));
                break;

            case 8 :
                System.out.println("Рекомендуемая дата полива кактуса" + " " + irri.plusDays(2));
                break;

        }

        System.out.println("Текущая влажность воздуха составляет" + " " + humidity + "%");

        if (humidity <= 29){
            System.err.println("ВНИМАНИЕ! Влажность воздуха менее 30%, поэтому кактус нужно полить сейчас!");
        }

    }

}
