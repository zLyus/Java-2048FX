package model;

import java.io.*;



public class FileManager implements Serializable {


    public void save(Board board) {
        int highestNumber = 0;
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("HighScore"))) {
             highestNumber = board.getHighestNumber();
            System.out.println("aktuelle zahl" + highestNumber);
                oos.write(highestNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int load() {
        int highest = 0;
        try(BufferedReader reader = new BufferedReader(new FileReader("HighScore"))) {
            try {
                highest = Integer.parseInt(reader.readLine());
            } catch (NumberFormatException e) {

            }
            return highest;

        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
        return highest;
    }
}