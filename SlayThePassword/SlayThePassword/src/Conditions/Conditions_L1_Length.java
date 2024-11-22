package Conditions;

public class Conditions_L1_Length implements Conditions {
    String hint = "yadayadayada";

    @Override
    public void checkCondition(String input) {
        int expectedLength = 10;
        if (input == null || input.length() != expectedLength) {
            throw new AssertionError(hint);
        }
    }
}
