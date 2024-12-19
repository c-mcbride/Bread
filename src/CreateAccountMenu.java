import java.util.Scanner;

//Creates account and sends the data to the BankAccount class.
public class CreateAccountMenu {
    private Scanner scanner = new Scanner(System.in);

    public BankAccount createAccount() throws IllegalArgumentException{
        String name = getValidName();
        double initialDeposit = getValidInitialDeposit();
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

    private double getValidInitialDeposit(){
        while(true){
            System.out.println("Enter initial deposit amount: ");
            if(scanner.hasNextDouble()){
                double amount = scanner.nextDouble();
                scanner.nextLine(); //Consume newline

                if(amount >= 0){
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
