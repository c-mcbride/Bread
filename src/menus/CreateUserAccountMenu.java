package menus;
import java.util.Scanner;

import accounts.UserAccount;

public class CreateUserAccountMenu {
    private Scanner scanner = new Scanner(System.in);


    public UserAccount createUserAccount() throws IllegalArgumentException{
        String name = getValidUsername();
        return new UserAccount(name);
    }

    /**
     * Validates username input to ensure that it is not empty
     * @return validated username that is not empty for sure
     */
    private String getValidUsername(){
        while(true){
            System.out.println("Enter account holder name: ");
            String userName = scanner.nextLine().trim();
            if(!userName.isEmpty()){
                return userName;
            }

            System.out.println("Error: Name cannot be empty. Please try again");
        }
    }
}
