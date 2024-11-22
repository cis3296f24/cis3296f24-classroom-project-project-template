package Conditions;

public class Conditions_add_to38 implements Conditions {
    String hint = "The sum of the digits should be 38";

    @Override
    public void checkCondition(String input) {
        int sum = input.chars().filter(Character::isDigit).map(Character::getNumericValue).sum();
        if (input == null || sum != 38) {
            throw new AssertionError(hint);
        }
    }

}
