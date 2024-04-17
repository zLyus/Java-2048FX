package model;

public class Game {

    private int achievedScore;
    private int spot;

    public Game(int achievedScore) {
        setAchievedScore(achievedScore);
    }

    public void setAchievedScore(int achievedScore) {
        this.achievedScore = achievedScore;
    }

    public int getAchievedScore() {
        return achievedScore;
    }


    public void setSpot(int spot) {
        this.spot = spot;
    }

    public int getSpot() {
        return spot;
    }
}
