package Conditions;

public class Conditions_Special_char implements Conditions {

    @Override
    public boolean checkCondition(String input) {

        if (input == null) {
            return false;
        }

        return input.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");

    }

    @Override
    public String toString() {
        return "Password must contain a special Character";
    }

}
