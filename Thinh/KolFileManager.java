import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class KolFileManager {
    private String filePath;

    public KolFileManager(String filePath) {
        this.filePath = filePath;
    }

    public List<KOL> readKolsFromFile() throws FileNotFoundException {
        List<KOL> kolList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String username = parts[0].trim();
                    int followers = Integer.parseInt(parts[1].trim());
                    kolList.add(new KOL(username, followers));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return kolList;
    }

    public void addKolToFile(KOL kol) throws IOException {
//         try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
////             writer.write(kol.getDisplayName() + "," + kol.getUsername() + "," + kol.getNumberOfFollowers());
////             writer.newLine();
//             writer.write("1111");
//         } catch (IOException e) {
//             throw new RuntimeException(e);
//         }
        try {
            FileWriter myWriter = new FileWriter("src\\data.txt");
            myWriter.write(kol.getUsername() + "," + kol.getNumberOfFollowers());
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
//        if (fp.exists()) {
//            System.out.println("111");
//        }
    }

    public void addKolsToFile(ArrayList<KOL> kols) throws FileNotFoundException{
        try {
            FileWriter myWriter = new FileWriter("src\\data.txt");
            for (KOL kol : kols) {
                myWriter.write(kol.getUsername() + "," + kol.getNumberOfFollowers());
                myWriter.write('\n');
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    public void removeKolFromFile(String username) {

    }

    public void setFilePath(String fp) {
        this.filePath = fp;
    }

    public String getFilePath(String fp) {
        return this.filePath;
    }


}
