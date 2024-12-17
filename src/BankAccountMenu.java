import java.util.Scanner;

public class BankAccountMenu {
    //For now this design will take a bank account object that is passed to it
    private BankAccount account;

    //Constructor for that accepts a BankAccountObject
    public BankAccountMenu(BankAccount account){
        this.account = account;
    }

    public void showMenu(){
        //Create scanner to read input
        Scanner scanner = new Scanner (System.in);
        int choice;

        //Menu loop
        do{
            System.out.println("Welcome to the Bank Account Menu");
            System.out.println("1. Display balance");
            System.out.println("2. Add Funds");
            System.out.println("3. Subtract Money");
            System.out.println("4. Exit");
            choice = scanner.nextInt();

            switch(choice){
                case 1:
                    account.displayBalance();
                    System.out.println("------------------------");
                    break;
                case 2:
                    System.out.println("Enter the amount to add: ");
                    int amountToAdd = scanner.nextInt();
                    account.addToBalance(amountToAdd);
                    System.out.println("------------------------");
                    break;
                case 3:
                    System.out.println("Enter the amount to subtract: ");
                    int amountToSubtract = scanner.nextInt();
                    account.subtractFromBalance(amountToSubtract);
                    System.out.println("------------------------");
                    break;
                case 4:
                    System.out.println("Exiting Account Modification Sub-routine");
                    System.out.println("------------------------");
                    break;
                default:
                    System.out.println("Invalid choice, please try again");
                    System.out.println("------------------------");
            }
        }
        while(choice != 4);

        scanner.close();
    }
}
