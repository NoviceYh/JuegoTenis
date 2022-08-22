package models;

public class Game {

    private Player player1;
    private Player player2;
    private Integer sets;
    private String nameTournament;
    private String[] points = {"0","15","30","40","Adv", "Game"};

    public Game() {
    }

    public String getPoint(Integer i) {
        return this.points[i];
    }


    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Integer getSets() {
        return sets;
    }

    public void setSets(Integer sets) {
        this.sets = sets;
    }

    public String getNameTournament() {
        return nameTournament;
    }

    public void setNameTournament(String nameTournament) {
        this.nameTournament = nameTournament;
    }
}
