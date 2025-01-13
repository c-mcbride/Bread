package main;
import java.util.Scanner;

import accounts.UserAccount;
import menus.UserAccountMenu;
import service.AccountManagerService;

/*
 * Runs the console menu for the application. Will not be used in javafx application but used here for programming
 * purposes.
 */
public class BreadApp {
    private UserAccount userAccount;
    private AccountManagerService accountManagerService;
    private Scanner scanner = new Scanner(System.in);

    public BreadApp(){
        this.accountManagerService = new AccountManagerService();
    }

    public void run(){
        System.out.println("Welcome to bread!");
        int choice;

        do{
            displayMenu();
            choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice){
                case 1:
                    createUserAccount();
                    break;
                case 2:
                    loginToAccount();
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
        System.out.println("2. Login to Account");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
    }

    //The addAccount method of the UserAccountService creates the account with hashed pin...really we just need strings
    private void createUserAccount(){
        String userName = getValidUserName();
        String pin = getValidPin();
        accountManagerService.addAccount(userName, pin);
    }

    //Modify an account, if it exists
    private void loginToAccount(){
        if(!accountManagerService.hasAccounts()){
            System.out.println("No accounts exist. Please create an account first. ");
            return;
        }

        System.out.println("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.println("Enter PIN: ");
        String pin = scanner.nextLine().trim();

        try{
            UserAccount userAccount = accountManagerService.login(username, pin);
            System.out.println("Login successful! Welcome, " + username);
            UserAccountMenu userAccountMenu = new UserAccountMenu(userAccount);
            userAccountMenu.showUserMenu();
        }
        catch(IllegalArgumentException e){
            System.out.println("Login failed: " + e.getMessage());
        }

    }

    public String getValidUserName(){
        while(true){
            System.out.println("Enter account name: ");
            String userName = scanner.nextLine().trim();
            if(!userName.isEmpty()){
                return userName;
            }

            System.out.println("Error: Name cannot be empty. Pleast try again. ");
        }
    }

    public String getValidPin(){
        while(true){
            System.out.println("Enter PIN number: ");
            String pin = scanner.nextLine().trim();
            if(!pin.isEmpty()){
                return pin;
            }

            System.out.println("Error: PIN cannot be empty. Please Try Again");
        }
    }
}
