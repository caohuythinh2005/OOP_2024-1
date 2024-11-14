import com.beust.ah.A;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// "src\\hashtags.txt"

public class HashtagManager {
    private final ArrayList<String> hashtags = new ArrayList<>();
    private String filePath;
    // Constructor
    public HashtagManager() {

    }

    public ArrayList<String> getHashtags() {
        return new ArrayList<>(hashtags);
    }

    public void addHashtag(String hashtag) {
        if (!hashtags.contains(hashtag)) {
            hashtags.add(hashtag);
        }
    }

    public void removeHashtag(String hashtag) {
        hashtags.remove(hashtag);
    }

    public boolean containsHashtag(String hashtag) {
        return hashtag.contains(hashtag);
    }


    public void readHashtagsFromFile(String filePath) throws IOException {
        if (filePath != null) {
            FileReader fr = new FileReader(filePath);
            BufferedReader reader = new BufferedReader(fr);
            String line;
            while ((line = reader.readLine()) != null) {
                this.addHashtag(line);
            }
        }
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String fileNPath) {
        this.filePath = fileNPath;
    }

}
