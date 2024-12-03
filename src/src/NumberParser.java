public class NumberParser {
    public static int parseNumber(String input) {
        input = input.replace(",", "");
        double multiplier = 1.0;
        if (input.endsWith("K")) {
            multiplier = 1_000;
            input = input.substring(0, input.length() - 1);
        } else if (input.endsWith("M")) {
            multiplier = 1_000_000;
            input = input.substring(0, input.length() - 1);
        } else if (input.endsWith("B")) {
            multiplier = 1_000_000_000;
            input = input.substring(0, input.length() - 1);
        }

        try {
            return (int)(Double.parseDouble(input) * multiplier);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input format: " + input);
        }
    }

//    public static void main(String[] args) {
//        String input = "1,494M";
//        int result = parseNumber(input);
//        System.out.println("Parsed number: " + result);
//    }
}
