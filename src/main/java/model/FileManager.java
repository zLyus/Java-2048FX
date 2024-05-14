package model;

import javafx.scene.control.ListView;

import java.io.*;
import java.util.ArrayList;

public class FileManager implements Serializable {


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
    }
