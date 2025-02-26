package Conditions;

public class Conditions_add_to38 implements Conditions {

    @Override
    public boolean checkCondition(String input) {

        if (input == null) {
            return false;
        }
        int sum = input.chars().filter(Character::isDigit).map(Character::getNumericValue).sum();
        return sum == 38;
    }

    @Override
    public String toString() {
        return "The sum of the digits should be 38";
    }
    // @Override
    // public int getDifficulty() {
    // return 2;
    // }

}
