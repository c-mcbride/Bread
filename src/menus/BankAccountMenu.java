package menus;
import java.util.Scanner;

import accounts.BankAccount;
import utils.MoneyUtils;

import java.math.BigDecimal;
public class BankAccountMenu {
    private BankAccount bankAccount;
    private Scanner scanner = new Scanner(System.in);

    public BankAccountMenu(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void showMenu() {
        int choice = -1;
        do {
            System.out.println("\nAccount Menu:");
            System.out.println("1. View Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
            }
            else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume invalid input
                continue;
            }

            try{
                switch (choice) {
                    case 1:
                        System.out.println("Balance: " + MoneyUtils.formatCurrency(bankAccount.getBalance()));
                        break;
                    case 2:
                        //Print confirmation here to decouple Print statements for core functions
                        System.out.print("Enter deposit amount: ");
                        if(scanner.hasNextBigDecimal()){
                            BigDecimal depositAmount = scanner.nextBigDecimal();
                            BigDecimal deposited = bankAccount.deposit(depositAmount);
                            System.out.println("Deposited: $" + MoneyUtils.formatCurrency(deposited)); //Nice formatted currency for display
                        }
                        else{
                            scanner.next();//Consume invalid input
                            throw new IllegalArgumentException("Invalid input. Please enter a numeric value.");
                        }

                        break;
                    case 3:
                        System.out.print("Enter withdrawal amount: ");
                        if(scanner.hasNextBigDecimal()){
                            BigDecimal withdrawalAmount = scanner.nextBigDecimal();
                            BigDecimal withdrawn = bankAccount.withdraw(withdrawalAmount);
                            System.out.println("Withdrew: $" + MoneyUtils.formatCurrency(withdrawn));
                        }
                        else{
                            scanner.next(); //Consume invalid input
                            throw new IllegalArgumentException("Invalid input. Please enter a numeric value. ");
                        }
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
