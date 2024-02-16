package HMWRK7;

import java.util.Collection;
import java.util.Scanner;
//подсмотрел, как можно было реализовать с помощью сканера. понравилось и решил тоже попробовать
// и в итоге не победил ошибку, которая указана на скрине. до этого все отрабатывало
public class TestClassWithCons {

    public static void main(String[] args) {
        System.out.println("Наберите help для справки по методам или exit для выхода");

        Scanner scanCons = new Scanner(System.in);

        PlayerService service = new PlayerServiceJSON();

        // бесконечный цикл на постоянную работу в консоли
        while (true){
            String doIt = scanCons.nextLine();

            //на получение списка игроков
            if (doIt.equalsIgnoreCase("getPlayers")){
                Collection<Player> players = service.getPlayers();
                if (players.isEmpty()){
                    System.err.println("Список игроков пуст");
                }
                for (Player player : players) {
                    System.out.println(player);
                }
            }
            // на создание игрока по никнейму
            if (doIt.toLowerCase().startsWith("createplayer")){
                String nick = doIt.substring(11);
                int newPlayerId = service.createPlayer(nick);
                System.out.println(newPlayerId);
            }

            // на удаление игрока по айдишнику
            if (doIt.toLowerCase().startsWith("deleteplayer")){
                String idString = doIt.substring(13);
                int id = Integer.parseInt(idString);
                Player player = service.deletePlayer(id);
                System.out.println(player);
            }

            // на добавление очков игроку - тут так и не получилось реализовать рабочий метод :(
            if (doIt.toLowerCase().startsWith("addpoints ")){
                String argStrings = doIt.substring(9);
                String[] arguments = argStrings.split(" ");
                int id = Integer.parseInt(arguments[0]);
                int points = Integer.parseInt(arguments[1]);
                int upDatePoint = service.addPoints(id,points);
                System.out.println(upDatePoint);
            }

            // на получение игрока по айдишнику
            if (doIt.toLowerCase().startsWith("getplayerbyid ")){
                String playerbyidget = doIt.substring(14);
                int id = Integer.parseInt(playerbyidget);
                Player player = service.getPlayerById(id);
                System.out.println(player);
            }

            // команда на завершение работы программы
            if (doIt.equalsIgnoreCase("exit")){
                System.exit(0);
            }

            // на вызов подсказки используемых команд
            if (doIt.equalsIgnoreCase("help")){
                System.out.println("Пользуйтесь следующими методами для проверок");
                System.out.println("getPlayerById - Получить игрока по id");
                System.out.println("getPlayers - Получить список игроков");
                System.out.println("createPlayer - Создать игрока (возвращает id нового игрока)");
                System.out.println("deletePlayer - Удалить игрока по id'шнику, вернет удаленного игрока");
                System.out.println("addPoints - Добавить очков игроку. Возвращает обновленный счет");
                System.out.println("Для выхода наберите exit");
            }




        }









    }


}
