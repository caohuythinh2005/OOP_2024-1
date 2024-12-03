import java.io.*;
import java.util.ArrayList;

public class UserManager {
    private String filePath;

    public UserManager(String filePath) {
        this.setFilePath(filePath);
    }

    public ArrayList<User> readUsersFromFile() throws FileNotFoundException {
        ArrayList<User> userList = new ArrayList<User>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String username = parts[0].trim();
                    int followers = Integer.parseInt(parts[1].trim());
                    userList.add(new User(username, followers));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public void addUsersToFile(ArrayList<User> users) throws FileNotFoundException{
        ArrayList<User> oldUser = readUsersFromFile();
        ArrayList<User> newUser = new ArrayList<User>();
        for (User x : users) {
            if (!oldUser.contains(x)) {
                newUser.add(x);
            }
        }
        try {
            FileWriter myWriter = new FileWriter(this.filePath, true);
            for (User user : newUser) {
                myWriter.write(user.getUsername() + "," + user.getNumberOfFollowers());
                myWriter.write('\n');
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    public void setFilePath(String fp) {
        this.filePath = fp;
    }

    public String getFilePath(String fp) {
        return this.filePath;
    }
}
