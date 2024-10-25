import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        ArrayList<String> cars = new ArrayList<String>();
        cars.add("Volvo");
        cars.add("Ford");
        cars.add(1, "traistorm");
        System.out.println(cars);
    }
}
