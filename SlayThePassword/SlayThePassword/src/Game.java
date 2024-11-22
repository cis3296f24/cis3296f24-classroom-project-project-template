import java.util.Scanner;

/**
 *   Class containing the main game loop. The user
 *   is thrown into a loop and given the objective
 *   of submitting the correct password based on a
 *   hint.
 *
 *   If the user submits the correct password, they
 *   gain health; if the password is incorrect, they
 *   lose health. The game ends when the user's HP
 *   is zero.
 */
public class Game
{
    public static void main(String[] args) throws InterruptedException
    {
        User user = new UserImpl();
        Password password;
        int lvl = 1;
        Scanner scanner = new Scanner(System.in);
        String input;

        // Opening formalities
        System.out.println("@---------------@\nSLAY THE PASSWORD\n@----------------@");
        Thread.sleep(2000);
        System.out.println("Submit the correct passwords and win!");
        Thread.sleep(2000);

        // Game loop, which ends when the player's HP is 0
        while (user.getHealth() != 0)
        {
            password = new Password();

            System.out.println("Lvl." + lvl + ", HP: " + user.getHealth());
            password.displayConditions(); // Display password hint

            // Take password input from the user
            input = scanner.nextLine();

            // Validate that the password is correct
            try {
                password.validate(input);

            } catch (AssertionError e) { // Wrong password submitted
                // Hint will be displayed again when AssertionError is thrown
                user.losehp(1);
                continue;
            }

            // Move to next level and restore some health
            lvl++;
            user.restorehp(1);
        }
        System.out.println("You lost! Game Over.");
    }
}
