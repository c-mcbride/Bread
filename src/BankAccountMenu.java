import java.util.Scanner;

public class BankAccountMenu {
    private BankAccount bankAccount;
    private Scanner scanner = new Scanner(System.in);

    public BankAccountMenu(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void showMenu() {
        int choice;
        do {
            System.out.println("\nAccount Menu:");
            System.out.println("1. View Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            try{
                switch (choice) {
                    case 1:
                        System.out.println("Balance: " + bankAccount.getBalance());
                        break;
                    case 2:
                        //Print confirmation here to decouple Print statements for core functions
                        System.out.print("Enter deposit amount: ");
                        double depositAmount = scanner.nextDouble();
                        double deposited = bankAccount.deposit(depositAmount);
                        System.out.println("Deposited: $" + deposited);
                        break;
                    case 3:
                        System.out.print("Enter withdrawal amount: ");
                        double withdrawalAmount = scanner.nextDouble();
                        double withdrawn = bankAccount.withdraw(withdrawalAmount);
                        System.out.println("Withdrew: $" + withdrawn);
                        break;
                    case 4:
                        System.out.println("Exiting Account Menu...");
                        break;
                    default:
                        System.out.println("Invalid choice, please try again.");
                }
            }
            catch(IllegalArgumentException e){
                System.out.println("Error: " + e.getMessage());
            }
        } while (choice != 4);
    }
}
