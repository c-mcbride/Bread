import java.util.Scanner;

/*
 * Runs the console menu for the application. Will not be used in javafx application but used here for programming
 * purposes.
 */
public class BreadApp {
    private UserAccount userAccount;
    private Scanner scanner = new Scanner(System.in);

    public void run(){
        System.out.println("Welcome to bread!");
        int choice;

        do{
            displayMenu();
            choice = scanner.nextInt();

            switch(choice){
                case 1:
                    createUserAccount();
                    break;
                case 2:
                    modifyUserAccount();
                    break;
                case 3:
                    System.out.println("Exiting Bread....Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice, please try again. ");
            }
        }while (choice != 3);

        scanner.close();
    }

    //Helper function to create the menu display
    private void displayMenu(){
        System.out.println("1. Create User Account");
        System.out.println("2. Access/Modify Budget");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
    }

    //Uses the createAccout menu to make a new account
    private void createUserAccount(){
        CreateUserAccountMenu userAccountMenu = new CreateUserAccountMenu();
        userAccount = userAccountMenu.createUserAccount();
    }

    //Modify an account, if it exists
    private void modifyUserAccount(){
        if (userAccount== null){
            System.out.println("No account exists. Please create an account first");
        }
        else{
            UserAccountMenu userAccountMenu = new UserAccountMenu(userAccount);
            userAccountMenu.showUserMenu();
        }
    }
}
