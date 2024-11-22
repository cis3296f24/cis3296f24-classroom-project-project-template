
package Conditions;

public class Conditions_Have_Number implements Conditions {
    String hint = "Password must include a number ";

    @Override
    public void checkCondition(String input) {

        if (input == null || !input.matches(".*\\d.*")) {

            throw new AssertionError(hint);
        }
    }
}
