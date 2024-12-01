
package Conditions;

public class Conditions_Have_Number implements Conditions {

    @Override

    public boolean checkCondition(String input) {

        if (input == null) {
            return false;
        }

        return !input.matches(".*\\d.*");

    }

    @Override
    public String toString() {
        return "Password must include a number ";
    }

}
