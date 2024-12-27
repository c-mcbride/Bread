package menus;

import java.util.Scanner;
import java.math.BigDecimal;

import service.BankAccountService;
import utils.MoneyUtils;

public class BankAccountMenu {
    private BankAccountService bankAccountService;
    private Scanner scanner = new Scanner(System.in);

    //Interacts with service layer only
    public BankAccountMenu(BankAccountService bankAccountService) {
        this.bankAccountService= bankAccountService;
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
                        System.out.println("Balance: " + bankAccountService.getFormattedBalance());
                        break;
                    case 2:
                        //Print confirmation here to decouple Print statements for core functions
                        System.out.print("Enter deposit amount: ");
                        if(scanner.hasNextBigDecimal()){
                            BigDecimal depositAmount = scanner.nextBigDecimal();
                            BigDecimal deposited = bankAccountService.deposit(depositAmount);
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
                            BigDecimal withdrawn = bankAccountService.withdraw(withdrawalAmount);
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
