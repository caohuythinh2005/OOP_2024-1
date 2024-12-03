import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataSaver {

    public static void saveKOLsToJsonFile(List<KOL> kols, String filePath) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(kols, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
        String username = "caohuythinh@gmail.com";
        String user = "ThnhCaoHuy";
        String password = "mancity1st";
        String filePathScanner = "src\\dataKol.txt";
        String filePathSaved= "src\\dataKolCollected.txt";
        String filePath = "src\\kol_data.json";
        int scrolls = 5;

        TwitterKOLScanner obj = new TwitterKOLScanner(filePath, filePathScanner, filePathSaved, username, user, password, scrolls);
        obj.readUsersFromFile();
        List<KOL> kols = obj.collectData();

//        saveKOLsToJsonFile(kolsCollected, filePath);
//        System.out.println("Data saved to " + filePath);
    }
}
