package model;


public class SaveGames {

    public Game[] saves;
    private int spots = 0;

    public SaveGames() {
        saves = new Game[10];
    }

    public void add(Game currentGame) {
        for(int i = 0; i < saves.length; i++) {
            if(saves[i] == null) {
                saves[i] = currentGame;
                currentGame.setSpot(i);
            } else {
                saves[spots] = currentGame;
                currentGame.setSpot(spots);
                checkSpots(spots++);
            }
        }
    }


    public void checkSpots(int num) {
        if(num >= 10) {
            spots = 0;
        } else {
            this.spots = spots;
        }
    }


    public String getScoreonIndex(int index) {
        if(index >= 0 &&index < 10) {
            return saves[index].toString();
        }
        return "";
    }
}
