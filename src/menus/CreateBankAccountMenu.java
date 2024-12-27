package menus;
import java.util.Scanner;

import accounts.BankAccount;

import java.math.BigDecimal;
/*
 * Holds the menu for the creating an account for the first time.
 * Gathers the account holder name, and inital balance
 */
public class CreateBankAccountMenu {
    private Scanner scanner = new Scanner(System.in);

    /*
     * Constructs bank account by calling getValidName() and initial deposit
     * @returns BankAccount Object with input validation
     * @throws illegal argument exception if name or balance are not the correct format
     */
    public BankAccount createAccount() throws IllegalArgumentException{
        String name = getValidName();
        BigDecimal initialDeposit = getValidInitialDeposit();
        return new BankAccount(name, initialDeposit);
    }


    /*
     * Makes sure that a name is entered and is not empty
     * @returns input validated account nmae
     * @throws illegal argument exception if the amount is less than 1 or if the balance is too low
     */
    private String getValidName(){
        while (true){
            System.out.print("Enter account holder name: ");
            String name = scanner.nextLine().trim();
            if(!name.isEmpty()){
                return name;
            }
            System.out.println("Error: Name cannot be empty. Please try again.");
        }
    }

    /*
     * Validates initial deposit by making sure that it is greater than 0
     * @returns validated deposit info
     * @throws illegal argument exception if the amount is less than 1 or if the balance is too low
     */
    private BigDecimal getValidInitialDeposit(){
        while(true){
            System.out.println("Enter initial deposit amount: ");
            if(scanner.hasNextBigDecimal()){
                BigDecimal amount = scanner.nextBigDecimal();
                scanner.nextLine(); //Consume newline

                if(amount.compareTo(BigDecimal.ZERO) >= 0){
                    return amount;
                }
                System.out.println("Error: Deposit amount cannot be negative.");
            }
            else{
                scanner.nextLine(); //Consume invalid input
                System.out.println("Error: Invalid input. Please enter a numeric value. ");
            }
        }
    }
}
