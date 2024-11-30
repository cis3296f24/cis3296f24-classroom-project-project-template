package Conditions;

public class Conditions_L1_Length implements Conditions {

    @Override
    public boolean checkCondition(String input) {
        if (input == null) {
            return false;
        }

        int expectedLength = 10;
        return input.length() == expectedLength;

    }

    @Override
    public String toString() {
        return "Password must be 10 characters long";
    }
}
