import org.checkerframework.checker.units.qual.K;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Preprocessing {
    private ArrayList<User> allUsers;
    private ArrayList<String> kolList;
    private int followerThreshold;
    private String filePath;

    public Preprocessing(String filePath, int threshold) {
        this.allUsers = new ArrayList<>();
        this.kolList = new ArrayList<>();
        this.followerThreshold = threshold;
        this.filePath = filePath;
    }

    public void loadUsers(ArrayList<User> allUsers) {
        this.allUsers = allUsers;
    }

    public void filterKOLs() {
        for (User x : allUsers) {
            if (x.getNumberOfFollowers() >= followerThreshold) {
                kolList.add(x.getUsername());
            }
        }
    }

    public void saveKols() {
        try {
            FileWriter myWriter = new FileWriter(this.filePath);
            for (String user : kolList) {
                myWriter.write(user);
                myWriter.write('\n');
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    public void setFollowerThreshold(int threshold) {
        this.followerThreshold = threshold;
    }

    public int getFollowerThreshold() {
        return this.followerThreshold;
    }

    public void setFilePath(String fp) {
        this.filePath = fp;
    }

    public String getFilePath(String fp) {
        return this.filePath;
    }
}
