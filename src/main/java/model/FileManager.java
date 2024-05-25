package model;

import javafx.scene.paint.Color;

import java.io.*;
import java.util.ArrayList;

public class FileManager implements Serializable {

    /**
     * Serialization for Highscore
     * @param board is a 2dimensional array where the game is played
     * @param reset if true writes a 0 and resets the currently written Highscore
     */
    public void saveHighScore(Board board, boolean reset) {
        int highestNumber = 0;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("HighScore"))) {
            if (!reset) {
                highestNumber = board.getHighestNumber();
                oos.writeInt(highestNumber);
            } else {
                oos.writeInt(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int loadHighScore() {
        int highest = 0;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("HighScore"))) {
            highest = ois.readInt();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return highest;
    }

    /**
     *
     * @param list an ArrayList<String>, which has all the Items that should be written
     * @param reset if true writes null and resets the currently written Lastgames
     */
    public void saveLastGames(ArrayList<String> list, boolean reset) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("LastGames"))) {
            if(reset) {
                oos.writeObject(null);
            } else {
                oos.writeObject(list);
            }
            oos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> loadLastGames() {
        ArrayList<String> arrayList = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("LastGames"))) {
            arrayList = (ArrayList<String>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return arrayList;
        }

    /**
     *
     * @param str is the hexCode of Custom Theme
     */
    public void saveCustomTheme(String str) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Custom Theme"))) {
            oos.writeObject(str);
            oos.flush();
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public String loadCustomTheme() {
        String str = new String();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Custom Theme"))) {
            str = (String) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return str;
    }

    public void saveBoard(Board board) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Board"))) {
            oos.writeObject(board);
            oos.flush();
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Board loadBoard() {
        Board board = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Board"))) {
            board = (Board) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return board;
    }
}

