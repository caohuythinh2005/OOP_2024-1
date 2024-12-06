package pagerank;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class DataLoader {

    public static List<KOL> loadKOLsFromJsonFile(String filePath) {
        Gson gson = new Gson();
        List<KOL> kols = null;

        try (FileReader reader = new FileReader(filePath)) {
            Type listType = new TypeToken<List<KOL>>() {}.getType();
            kols = gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return kols;
    }

//    public static void main(String[] args) {
//        String filePath = "kol_data.json";
//
//        // Đọc dữ liệu từ file
//        List<KOL> kols = loadKOLsFromJsonFile(filePath);
//
//        if (kols != null) {
//            for (KOL kol : kols) {
//                System.out.println(kol);
//            }
//        } else {
//            System.out.println("Failed to load data.");
//        }
//    }
}
