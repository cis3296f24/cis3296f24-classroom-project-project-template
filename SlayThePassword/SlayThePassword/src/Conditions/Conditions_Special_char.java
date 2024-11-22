package Conditions;

public class Conditions_Special_char implements Conditions {
    String hint = "Password must contain a special Character";

    @Override
    public void checkCondition(String input) {

        if (input == null || !input.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            throw new AssertionError(hint);

        }

    }

}
