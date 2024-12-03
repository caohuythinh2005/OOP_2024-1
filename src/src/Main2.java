import java.io.IOException;
import java.util.ArrayList;

public class Main2 {
    public static void main(String[] args) throws IOException {
        UserManager manager = new UserManager("src\\data.txt");
        ArrayList<User> users = manager.readUsersFromFile();
        for (User x : users) {
            System.out.println(x);
        }
        Preprocessing prep = new Preprocessing("src\\dataKol.txt", 5000);
        prep.loadUsers(users);
        prep.filterKOLs();
        prep.saveKols();
    }
}
