package Conditions;

public class Conditions_Cap implements Conditions {

    @Override
    public boolean checkCondition(String input) {
        if (input == null) {
            return false;
        }

        return input != null && input.matches(".*[A-Z].*");

    }

    @Override
    public String toString() {
        return "Password must contain 1 Uppercase letter";
    }
}
