import java.util.Scanner;

//Creates account and sends the data to the BankAccount class.
public class CreateAccountMenu {
    private Scanner scanner = new Scanner(System.in);

    public BankAccount createAccount() throws IllegalArgumentException{
        String name = "";
        double initialDeposit = -1;

        //Get the account holders name
        while (name.isEmpty()){
            System.out.println("Enter account holder's name: ");
            name = scanner.nextLine().trim();
            if(name.isEmpty()){
                throw new IllegalArgumentException("Name cannot be empty.");
            }
        }

        boolean validDeposit = false;
        while(!validDeposit){
            System.out.println("Enter initial deposit amount: ");
            if(scanner.hasNextDouble()){
                initialDeposit = scanner.nextDouble();
                if(initialDeposit >= 0){
                    validDeposit = true;
                }
                else{
                    throw new IllegalArgumentException("Deposit amount cannot be negative");
                }
            }
            else{
                scanner.next();
                throw new IllegalArgumentException("Invalid input. Please enter a numeric value");
            }
        }

        //Return the newly constructed BankAccout object with valid parameters
        return new BankAccount(name, initialDeposit);
    }
}
