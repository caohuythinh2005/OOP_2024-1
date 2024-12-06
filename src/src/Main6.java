import java.io.FileNotFoundException;
import java.util.List;

public class Main6 {
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
        obj.collectData();
    }
}
