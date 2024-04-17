package model;

import java.io.*;



public class FileManager implements Serializable {


    public void save(Board board, boolean reset) {
        int highestNumber = 0;
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("HighScore"))) {
            if(!reset) {
                highestNumber = board.getHighestNumber();
                oos.writeInt(highestNumber);
            } else {
                oos.writeInt(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int load() {
        int highest = 0;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("HighScore"))) {
            highest = ois.readInt();  // Read integer data using readInt
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return highest;
    }
}