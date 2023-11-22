package learn.mastery.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ConsoleIO {
    private static final String INVALID_NUMBER = "[INVALID] Enter a valid number.";
    private static final String NUMBER_OUT_OF_RANGE = "[INVALID] Enter a number between %s and %s.";
    private static final String REQUIRED = "[INVALID] Value is required.";
    private static final String REQUIRED_EMAIL = "[INVALID] Email is required and must contain @.";
    private static final String INVALID_DATE = "[INVALID] Enter a date in MM/dd/yyyy format.";
    private static final String INVALID_Y_N = "[INVALID] Please enter 'y' or 'n'.";

    private final Scanner scanner = new Scanner(System.in);
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public void print(String message) {
        System.out.print(message);
    }

    public void println(String message) {
        System.out.println(message);
    }

    public void printf(String message, Object... values) {
        System.out.printf(message, values);
    }

    public String readString(String prompt) {
        print(prompt);
        return scanner.nextLine();
    }

    public String readRequiredString(String prompt) {
        while (true) {
            String result = readString(prompt);
            if (!result.isBlank()) {
                return result;
            }
            println(REQUIRED);
        }
    }

    public String readRequiredEmail(String prompt) {
        while (true) {
            String result = readString(prompt);
            if (!result.isBlank() && result.contains("@")) {
                return result;
            }
            println(REQUIRED_EMAIL);
        }
    }

    public int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readRequiredString(prompt));
            } catch (NumberFormatException ex) {
                println(INVALID_NUMBER);
            }
        }
    }

    public int readInt(String prompt, int min, int max) {
        while (true) {
            int result = readInt(prompt);
            if (result >= min && result <= max) {
                return result;
            }
            println(String.format(NUMBER_OUT_OF_RANGE, min, max));
        }
    }

    public boolean readBoolean(String prompt) {
        while (true) {
            String input = readRequiredString(prompt).toLowerCase();
            if (input.equals("y")) {
                return true;
            } else if (input.equals("n")) {
                return false;
            }
            println(INVALID_Y_N);
        }
    }

    public LocalDate readLocalDate(String prompt) {
        while (true) {
            String input = readRequiredString(prompt);
            try {
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException ex) {
                println(INVALID_DATE);
            }
        }
    }
}
