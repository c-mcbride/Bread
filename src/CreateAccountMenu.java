import java.util.Scanner;
import java.math.BigDecimal;
//Creates account and sends the data to the BankAccount class.
public class CreateAccountMenu {
    private Scanner scanner = new Scanner(System.in);

    public BankAccount createAccount() throws IllegalArgumentException{
        String name = getValidName();
        BigDecimal initialDeposit = getValidInitialDeposit();
        return new BankAccount(name, initialDeposit);
    }


    //Creating yet another seperation of responsibilites by putting the input validation functions here. These can be overwritten later
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
