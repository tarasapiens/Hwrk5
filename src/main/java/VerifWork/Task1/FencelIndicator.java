package VerifWork.Task1;

import java.util.Scanner;

public class FencelIndicator {

        public static void main(String[] args) {

            int phraseLength = 15/3*62;
            int spaceLength = 3*12;
            int allLength = phraseLength + spaceLength;

            System.out.println("Введите длину забора в сантиметрах для проверки");
            Scanner scanCons = new Scanner(System.in);

            while (true) {
                int doIt = Integer.parseInt(scanCons.nextLine());

                if (doIt >= allLength){
                    System.out.println("На заборе достаточно места");
                } else {
                    System.err.println("На заборе не хватит места для надписи");
                }

            }

        }
    }

