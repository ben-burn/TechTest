package main;

import java.util.HashMap;

public class Converter {
    // Constants
    private static final int DOLLARS = 0;
    private static final int CENTS = 1;

    private static final HashMap<Integer, String> NumNames = new HashMap<>();

    static {
        // Numbers 0-19
        NumNames.put(0, "ZERO");
        NumNames.put(1, "ONE");
        NumNames.put(2, "TWO");
        NumNames.put(3, "THREE");
        NumNames.put(4, "FOUR");
        NumNames.put(5, "FIVE");
        NumNames.put(6, "SIX");
        NumNames.put(7, "SEVEN");
        NumNames.put(8, "EIGHT");
        NumNames.put(9, "NINE");
        NumNames.put(10, "TEN");
        NumNames.put(11, "ELEVEN");
        NumNames.put(12, "TWELVE");
        NumNames.put(13, "THIRTEEN");
        NumNames.put(14, "FOURTEEN");
        NumNames.put(15, "FIFTEEN");
        NumNames.put(16, "SIXTEEN");
        NumNames.put(17, "SEVENTEEN");
        NumNames.put(18, "EIGHTEEN");
        NumNames.put(19, "NINETEEN");

        // Tens
        NumNames.put(20, "TWENTY");
        NumNames.put(30, "THIRTY");
        NumNames.put(40, "FORTY");
        NumNames.put(50, "FIFTY");
        NumNames.put(60, "SIXTY");
        NumNames.put(70, "SEVENTY");
        NumNames.put(80, "EIGHTY");
        NumNames.put(90, "NINETY");

        // Hundreds and larger
        NumNames.put(100, "HUNDRED");
        NumNames.put(1000, "THOUSAND");
        NumNames.put(1000000, "MILLION");
    }

    private String protoNumber;
    private Boolean negative = false;

    /**
     * Constructor for the main.Converter class
     *
     * @param number A string representing the numeric value to be converted.
     */
    public Converter(String number) {
        this.protoNumber = number;
    }

    /**
     * Truncates or extends the decimal places of the number for consistency.
     * Adds 0 if no dollars input
     */
    public void sanitiseNum() {
        // If number has no decimal places
        if (!this.protoNumber.contains(".")) {
            this.protoNumber += ".00";
        } else {
            int decimalIndex = this.protoNumber.indexOf('.');
            int decimalPlaces = this.protoNumber.length() - decimalIndex - 1;

            if (decimalPlaces == 0) {
                // If there are no digits after the decimal, add "00"
                this.protoNumber += "00";
            } else if (decimalPlaces == 1) {
                // If there is only 1 decimal place, add "0" to make it 2 decimal places
                this.protoNumber += "0";
            } else {
                // Remove all values after the second decimal place
                this.protoNumber = this.protoNumber.substring(0, decimalIndex + 3);
            }
            // If no dollars input, add a 0 eg .45 - > 0.45
            if (decimalIndex == 0) {
                this.protoNumber = "0" + this.protoNumber;
            }
        }
        // Check if number is negative, remove the '-' from the start, set this.negative to true
        if (this.protoNumber.charAt(0) == '-') {
            this.negative = true;
            this.protoNumber = this.protoNumber.substring(1);
        }
    }

    /**
     * Converts a numeric string representation of a monetary amount into its worded equivalent.
     *
     * @return A string representation of the monetary amount in words, including the words "DOLLARS AND CENTS".
     *         For example, "123.45" would return "ONE HUNDRED TWENTY-THREE DOLLARS AND FORTY-FIVE CENTS".
     */
    public String convertNum() {
        StringBuilder converted = new StringBuilder();

        // Split at decimal
        String[] splitNum = this.protoNumber.split("\\.");

        // Convert to integers for arithmetic
        int dollars = Integer.parseInt(splitNum[DOLLARS]);
        int cents = Integer.parseInt(splitNum[CENTS]);

        // Append minus if number is negative
        if (this.negative) {
            converted.append("MINUS ");
        }

        // Build string including additional words
        converted.append(convertDollars(dollars)).append((" DOLLARS AND "));
        converted.append(convertCents(cents)).append(" CENTS");

        // Return built string
        return converted.toString();
    }

    /**
     * Converts an integer representing the of a monetary amount in dollars into its worded equivalent.
     *
     * @return A string representation of the monetary amount in words.
     *         For example, "123" would return "ONE HUNDRED AND TWENTY-THREE DOLLARS".
     */
    private String convertDollars(int dollars) {
        // Check 0 dollars
        if (dollars == 0) {
            return NumNames.get(0);
        }

        StringBuilder ret = new StringBuilder();

        // Check Million
        if (dollars >= 1000000) {
            int milNum = dollars / 1000000;
            // Append the hundreds section then 'MILLION'
            ret.append(hundredsCheck(milNum)).append(" ").append(NumNames.get(1000000));
            dollars %= 1000000;
            // If remainder add space
            if (dollars > 0) {
                ret.append(" ");
            }
        }

        // Check Thousand
        if (dollars >= 1000) {
            int thouNum = dollars / 1000;
            // Append the hundreds section then "THOUSAND'
            ret.append(hundredsCheck(thouNum)).append(" ").append(NumNames.get(1000));
            dollars %= 1000;
            // If remainder add space
            if (dollars > 0) {
                ret.append(" ");
            }
        }
        // If remainder then check number < 1000
        if (dollars > 0) {
            ret.append(hundredsCheck(dollars));
        }
        return ret.toString();
    }

    /**
     * Converts an integer representing the of a monetary amount in cents into its worded equivalent.
     *
     * @return A string representation of the monetary amount in words.
     *         For example, "45" would return "FORTY-FIVE CENTS".
     */
    private String convertCents(int cents) {
        // Check 0 cents
        if (cents == 0) {
            return NumNames.get(0);
        }
        return tensCheck(cents);
    }

    /**
     * Helper function to convert numbers < 1000 into words.
     * Calls another helper function tensCheck.
     *
     * @param number is an integer that is to be converted
     * @return the word representation of the input parameter
     */
    private String hundredsCheck(int number) {
        StringBuilder ret = new StringBuilder();
        if (number >= 100) {
            ret.append(NumNames.get(number / 100)).append(" HUNDRED ");
            number %= 100;
            if (number > 0) {
                // Add the "AND" for hundreds since english language...
                ret.append("AND ");
            }
        }
        // Append the number < 100
        return ret.append(tensCheck(number)).toString();
    }

    /**
     * Helper function to convert numbers < 100 into words.
     *
     * @param number is an integer that is to be converted
     * @return the word representation of the input parameter
     */
    private String tensCheck(int number) {
        StringBuilder ret = new StringBuilder();
        // Check Tens
        if (number >= 20) {
            ret.append(NumNames.get((number / 10) * 10));
            number %= 10;
            // Append the integer division * 10 eg. 45 -> 40 -> FORTY-
            if (number > 0) {
                ret.append('-');
            }
        }
        if (number > 0) {
            // Append the ones
            ret.append(NumNames.get(number));

        }
        return ret.toString();
    }

    /**
     * Returns the number for testing purposes.
     * To be called after sanitising.
     *
     * @return the protoNumber as a string
     */
    public String getProtoNumber() {
        return this.protoNumber;
    }

    /**
     * Returns if the number is negative for testing purposes.
     * To be called after sanitising.
     *
     * @return the protoNumber as a string
     */
    public boolean getNegative() {
        return this.negative;
    }
}