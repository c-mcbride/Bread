import java.util.Scanner;

//Creates account and sends the data to the BankAccount class.
public class CreateAccountMenu {
    private Scanner scanner = new Scanner(System.in);

    public BankAccount createAccount() {
        System.out.println("Enter account holder's name:");
        String name = scanner.nextLine();

        System.out.println("Enter initial deposit amount:");
        double initialDeposit = scanner.nextDouble();

        BankAccount account = new BankAccount(name, initialDeposit);
        System.out.println("Account created successfully!");

        return account;
    }
}
