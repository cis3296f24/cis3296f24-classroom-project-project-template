import Conditions.*;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.Random;
import Conditions.Conditions;

public class Password {

    private ArrayList<Conditions> conditionsList;
    private Conditions currentCondition;

    public Password() {
        // create a list to store the conditions
        conditionsList = new ArrayList<>();
        conditionsList.add(new Conditions_add_to38());
        conditionsList.add(new Conditions_Cap());
        conditionsList.add(new Conditions_Have_Number());
        conditionsList.add(new Conditions_L1_Length());
        conditionsList.add(new Conditions_Special_char());

        // generate a random index for randome condition
        Random rand = new Random();
        int index = rand.nextInt(conditionsList.size());
        currentCondition = conditionsList.get(index);
    }

    // method to validate password
    public void validate(String input) {
        if (!currentCondition.checkCondition(input)) {
            throw new AssertionError("Invalid password! Try again.");
        }

    }

    // show the hint
    public void displayConditions() {
        System.out.println("Hint: " + currentCondition.toString());

    }

}