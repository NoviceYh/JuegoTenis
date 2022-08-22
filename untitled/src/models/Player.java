package models;

import java.util.ArrayList;

public class Player {

    private String name;
    private String lastname;
    private Integer games;
    private Integer sets;
    private Integer winrate;
    private ArrayList<Integer> total;
    private Boolean serve = Boolean.FALSE;

    public Integer getWinrate() {
        return winrate;
    }

    public void initArray(){
        this.total = new ArrayList<>();
    }

    public void setWinrate(Integer winrate) {
        this.winrate = winrate;
    }

    public Boolean getServe() {
        return serve;
    }

    public void setServe(Boolean serve) {
        this.serve = serve;
    }



    public Player() {
        this.games = 0;
        this.sets = 0;
    }

    public void infoMatch(){
        int siz = this.lastname.length();
        int n = 15 - siz;
        System.out.print(this.name.charAt(0) + ". " + this.lastname);
        for (int i = 0; i < n; i++) {
            System.out.print(" ");
        }
        System.out.print("| ");
        getTotal();
    }
    public void getTotal() {
        for (Integer num : this.total) {
            System.out.print(num + " ");
        }
    }

    public void addTotal(Integer num) {
        this.total.add(num);
    }
/*
    public void winPoint(){
        this.points ++;
    }*/
    public void winSet(){
        this.sets ++;
    }
    public void winGame(){
        this.games ++;
    }/*
    public void resetPoints(){
        this.points = 0;
    }*/
    public void resetGames(){
        this.games = 0;
    }
    /*public void resetSets(){
        this.sets = 0;
    }*/
/*
    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }*/

    public Integer getGames() {
        return games;
    }
/*
    public void setGames(Integer games) {
        this.games = games;
    }*/

    public Integer getSets() {
        return sets;
    }

    public void setSets(Integer sets) {
        this.sets = sets;
    }


/*
    public String getName() {
        return name;
    }*/

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return name + " " + lastname;
    }
}
