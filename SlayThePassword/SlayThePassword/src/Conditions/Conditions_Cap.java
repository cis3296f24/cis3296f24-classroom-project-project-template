package Conditions;

public class Conditions_Cap implements Conditions {
    String hint = "Password must contain 1 Uppercase letter ";

    @Override
    public void checkCondition(String input) {

        if (input == null || !input.matches(".*[A-Z].*")) {
            throw new AssertionError(hint);
        }
    }
}
