package services;

import models.Game;
import models.Player;

import java.util.Scanner;

public class GameService {

    private static final String WIN_SET = " ganó el set!";
    private static final String WIN_TIEBREAK = " ganó el tiebreak!";

    private Scanner sc = new Scanner(System.in).useDelimiter("\n");

    public void init(Game game) throws InterruptedException {

        enterData(game);
        showInfo(game);
        rematch(game);
    }

    private void rematch(Game game) throws InterruptedException {
        String ans;
        do {
            game.getPlayer1().initArray();
            game.getPlayer2().initArray();
            startGame(game);
            System.out.println("Quiere jugar la revancha? (s/n): ");
            ans = sc.next();
            game.getPlayer1().resetGames();
            game.getPlayer1().setSets(0);
            game.getPlayer2().resetGames();
            game.getPlayer2().setSets(0);
        } while (ans.equalsIgnoreCase("s") || ans.equalsIgnoreCase("SI"));

        System.out.println("\n----------- GRACIAS POR JUGAR -----------");

    }

    private void enterData(Game game) {
        //Ingreso del nombre del torneo
        String name;
        String first;
        String last;
        System.out.println("\n------ BIENVENIDO AL SIMULADOR DE TENIS ------");
        System.out.println("");
        System.out.print("Nombre del torneo: ");
        name = sc.next();
        first = name.substring(0, 1).toUpperCase();
        last = name.substring(1).toLowerCase();
        game.setNameTournament(first + last);
        //Ingreso los datos de los dos jugadores
        System.out.println("Datos del Jugador 1");
        game.setPlayer1(dataPlayer());
        System.out.println("Datos del Jugador 2");
        game.setPlayer2(dataPlayer());
        //Ingreso numero de sets
        System.out.println("Numero de sets");
        game.setSets(dataSets());
    }

    private void showInfo(Game game) throws InterruptedException {
        //Muestra toda la información del torneo
        Thread.sleep(1000);
        System.out.println("------------------------------------");
        System.out.println("Torneo: " + game.getNameTournament());
        Thread.sleep(1000);
        System.out.println("Se enfrentan");
        System.out.println("" +
                "Jugador 1: " + game.getPlayer1().toString() + "\n" +
                "Jugador 2: " + game.getPlayer2().toString());
        Thread.sleep(1000);
        System.out.println("\nSe jugará al mejor de " + game.getSets() + " sets.");
        System.out.println("------------------------------------");
        Thread.sleep(1000);
    }

    private Integer moduloPrueba(int a, int b) {
        int r = (a > b) ? (a - b) : (b - a);
        return r;
    }

    private void pruebaTiebreak(Game game) throws InterruptedException {
        int p1;
        int p2;
        int p1count = 0;
        int p2count = 0;
        boolean dif = true;
        System.out.println("--- TIEBREAK ---");
        do {

            do {
                p1 = Math.round((float) Math.random() * (game.getPlayer1().getWinrate() + 1) + 1);
                p2 = Math.round((float) Math.random() * (game.getPlayer2().getWinrate() + 1) + 1);
            } while (p1 == p2);

            if (p1 > p2) {
                p1count++;
            } else {
                p2count++;
            }
            System.out.println(game.getPlayer1().toString() + ": " + p1count +
                    "\n" + game.getPlayer2().toString() + ": " + p2count);
            Thread.sleep(1000);
            if ((p1count > 6 || p2count > 6) && moduloPrueba(p1count, p2count) > 1) {
                if (p1count > p2count) {
                    System.out.println(game.getPlayer1().toString() + WIN_TIEBREAK);
                    game.getPlayer1().winGame();
                    game.getPlayer1().winSet();
                } else {
                    System.out.println(game.getPlayer2().toString() + WIN_TIEBREAK);
                    game.getPlayer2().winGame();
                    game.getPlayer2().winSet();
                }
                break;
            }
        } while (dif);
    }

    private void pruebaGame(Game game) throws InterruptedException {
        int point = 0;
        serveFirst(game);

        do {
            //Tiebreak
            pruebaPoint(game);
            if (game.getPlayer1().getGames() == 6 && game.getPlayer2().getGames() == 6) {
                pruebaTiebreak(game);
                point = 7;
            } else if ((game.getPlayer1().getGames() == 6 && game.getPlayer2().getGames() < 5)) {
                point = winSetP1(game);
            } else if (game.getPlayer2().getGames() == 6 && game.getPlayer1().getGames() < 5) {
                point = winSetP2(game);
            } else if (game.getPlayer1().getGames() == 7) {
                point = winSetP1(game);
            } else if (game.getPlayer2().getGames() == 7) {
                point = winSetP2(game);
            }

            if (point != 7) {
                System.out.println("Resultado Parcial de Games:" +
                        "\n" + game.getPlayer1().getLastname() + ": " + game.getPlayer1().getGames() +
                        "\n" + game.getPlayer2().getLastname() + ": " + game.getPlayer2().getGames());
            }
            Thread.sleep(1000);

        } while (point != 7);
        //Se agregan los Sets al array
        game.getPlayer1().addTotal(game.getPlayer1().getGames());
        game.getPlayer2().addTotal(game.getPlayer2().getGames());
    }

    private Integer winSetP1(Game game) {
        System.out.println(game.getPlayer1().toString() + WIN_SET);
        game.getPlayer1().winSet();
        return 7;
    }

    private Integer winSetP2(Game game) {
        System.out.println(game.getPlayer2().toString() + WIN_SET);
        game.getPlayer2().winSet();
        return 7;
    }

    private void serveFirst(Game game) {
        int p1;
        int p2;
        do {
            p1 = Math.round((float) Math.random() * 101 + 1);
            p2 = Math.round((float) Math.random() * 101 + 1);
        } while (p1 == p2);
        if (p1 > p2) {
            game.getPlayer1().setServe(Boolean.TRUE);
        } else {
            game.getPlayer2().setServe(Boolean.TRUE);
        }
    }

    private void pruebaPoint(Game game) throws InterruptedException {
        int point = 0;
        int p1count = 0;
        int p2count = 0;
        int p1;
        int p2;
        serveAndChange(game);
        System.out.println("--- Comienzo del Game ---");

        do {
            //Si p1 es igual a p2 se repite el bucle
            do {
                p1 = Math.round((float) Math.random() * (game.getPlayer1().getWinrate() + 1) + 1);
                p2 = Math.round((float) Math.random() * (game.getPlayer2().getWinrate() + 1) + 1);
            } while (p1 == p2);

            if (p1 > p2) {
                //Por si hay Deuce
                if (p1count == 3 && p2count == 4) {
                    p2count = 3;
                } else if (p1count == 3 && (p2count != 3)) { //Por si gana el Adv
                    p1count = 5;
                } else {
                    p1count++;
                }
            } else {
                //Por si hay Deuce
                if (p2count == 3 && p1count == 4) {
                    p1count = 3;
                } else if (p2count == 3 && (p1count != 3)) { //Por si gana el Adv
                    p2count = 5;
                } else {
                    p2count++;
                }
            }
            //Pregunta si alguno ganó
            if (p1count == 5 || p2count == 5) {
                if (p1count > p2count) {
                    System.out.println(game.getPlayer1().toString() + " ganó el game!");
                    game.getPlayer1().winGame();
                } else {
                    System.out.println(game.getPlayer2().toString() + " ganó el game!");
                    game.getPlayer2().winGame();
                }
                point = 5;
            } else {
                //Sino ganó nadie, informa los puntos
                System.out.println("Puntos:" +
                        "\n" + game.getPlayer1().getLastname() + ": " + game.getPoint(p1count) +
                        "\n" + game.getPlayer2().getLastname() + ": " + game.getPoint(p2count));
                System.out.println("");
                Thread.sleep(1000);
            }
        } while (point != 5);
        System.out.println("--- Termino el game ---");
    }

    private void startGame(Game game) throws InterruptedException {
        int wins;

        if (game.getSets() == 5) {
            wins = 3;
        } else {
            wins = 2;
        }

        for (int i = 0; i < game.getSets(); i++) {
            System.out.println("\n--- COMIENZO DEL SET N° " + (i + 1) + " ---");
            pruebaGame(game);
            game.getPlayer1().resetGames();
            game.getPlayer2().resetGames();
            System.out.println("--- TERMINO EL SET N° " + (i + 1) + " ---");
            if (game.getPlayer1().getSets() == wins || game.getPlayer2().getSets() == wins) {
                break;
            } else {
                System.out.println("---- Resultado Parcial ----");
                infoMatch(game);
            }
        }

        System.out.println("---- Resultado Final del partido ----");
        infoMatch(game);
        System.out.println("----------------------------------------------------");

        String ganador = (game.getPlayer1().getSets() == wins)
                ? game.getPlayer1().toString()
                : game.getPlayer2().toString();
        System.out.println("GANADOR DEL TORNEO " + game.getNameTournament() + ": " + ganador);
        System.out.println("----------------------------------------------------");
    }

    private void infoMatch(Game game) throws InterruptedException {
        System.out.println("----------------------------------------");
        Thread.sleep(1000);
        game.getPlayer1().infoMatch();
        System.out.println("");
        game.getPlayer2().infoMatch();
        System.out.println("\n----------------------------------------");
    }

    private Integer dataSets() {
        int sets;
        System.out.print("Ingrese la cantidad de sets a jugar, 3 o 5: ");
        sets = sc.nextInt();
        while (sets != 3 && sets != 5) {
            System.out.print("El numero de sets ingresado es invalido!. Ingrese nuevamente: ");
            sets = sc.nextInt();
        }
        return sets;
    }

    private Player dataPlayer() {
        int prob;
        String name;
        String lastname;
        String first;
        String last;

        Player player = new Player();
        System.out.print("Nombre: ");
        name = sc.next();
        first = name.substring(0, 1).toUpperCase();
        last = name.substring(1).toLowerCase();
        player.setName(first + last);
        System.out.print("Apellido: ");
        lastname = sc.next();
        first = lastname.substring(0, 1).toUpperCase();
        last = lastname.substring(1).toLowerCase();
        player.setLastname(first + last);
        //Ingreso de probabilidad de ganar
        System.out.print("Probabilidad de ganar del jugador(1 al 100): ");
        prob = sc.nextInt();
        while (prob < 1 || prob > 100) {
            System.out.print("Error. Ingrese la probabilidad nuevamente(1 al 100): ");
            prob = sc.nextInt();
        }
        player.setWinrate(prob);
        return player;
    }

    private void serveAndChange(Game game) {
        if (game.getPlayer1().getServe()) {
            System.out.println("*Saque: " + game.getPlayer1().toString());
            game.getPlayer1().setServe(Boolean.FALSE);
            game.getPlayer2().setServe(Boolean.TRUE);
        } else {
            System.out.println("*Saque: " + game.getPlayer2().toString());
            game.getPlayer1().setServe(Boolean.TRUE);
            game.getPlayer2().setServe(Boolean.FALSE);
        }

    }

}
