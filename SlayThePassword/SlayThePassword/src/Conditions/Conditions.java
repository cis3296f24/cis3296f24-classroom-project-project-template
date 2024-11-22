
package Conditions;


public interface Conditions {
    /*
     * Each condition is a single AssertEquals that checks 
     * inputted String from the user to meets it given Criteria.
     * The condition has a Difficulty Variable signifying it's difficulty
     * in relation to other conditions.
    */

    void checkCondition(String input);
}
