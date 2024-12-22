package main;

public class Main {
    public static void main(String[] args) {
        // Parse args
        String number = parseArgs(args);

        // Create main.Converter class
        Converter conv = new Converter(number);

        // Sanitize
        conv.sanitiseNum();

        // Convert
        String finalNumber = conv.convertNum();

        // Return
        System.out.println(finalNumber);
    }

    /**
     * Parses the commandline arguments, checks for errors and invalid input.
     * Calls System.exit() if error is found.
     * Status 1: Too many arguments
     * Status 2: Argument is not a number or number is too large
     *
     * @param args the commandline arguments
     * @return the first argument of the commandline ie the number to be converted
     */
    private static String parseArgs(String[] args) {
        // If the user inputs more than 1 argument
        if (args.length != 1) {
            System.out.println("Invalid arguments");
            System.exit(1);
        }
        float number;
        // If the user inputs the correct number of arguments
        // but does not input a number or the number is too large
        try {
            number = Float.parseFloat(args[0]);
            if (number > (Math.pow(10, 10) - 1)) {
                throw new NumberFormatException("Number too large");
            }
        } catch (NumberFormatException e) {

            System.out.println("Invalid input");
            System.exit(2);
        }
        return args[0];
    }
}
