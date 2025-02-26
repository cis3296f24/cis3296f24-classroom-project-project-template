
import java.util.Scanner;

public class PasswordDemo {

    public static void main(String[] args) {

        int total = 0;

        System.out.println("Submit a password that contains numbers adding to 30 in exactly 4 characters");

        Scanner input = new Scanner(System.in);
        String userInput = input.nextLine();
        int length = userInput.length();


        for(int i = 0; i < length; i++){
            if(Character.isDigit(userInput.charAt(i))){
                total += Character.getNumericValue(userInput.charAt(i));
            }
        }

        if(length == 4 && total == 30){
            System.out.println("You win!");
        }else{
            System.out.println("life system not implimented yet, you lose.");
        }

        input.close();    
    }
    
}

/*
 * public class PasswordDemo {

    public static void main(String[] args) {

        Password password = new Password();
        password.displayConditions();

        Scanner input = new Scanner(System.in);
        String userInput = input.nextLine();

        password.validate(userInput);

        input.close();    
    }
    
}
 */
