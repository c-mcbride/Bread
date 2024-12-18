//Single responsiblity: hold account info and is able to provide balance, deposit orwithdraw.
public class BankAccount {
    private String accountHolderName;
    private double balance;

    public BankAccount(String accountHolderName, double initialBalance) {
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    //Refactor to return values or throw an exception so that print statements are coupled with core functions
    public double deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            return amount;
        } else {
            throw new IllegalArgumentException("Invalid deposit mount. ");
        }
    }

    public double withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return amount;
        } else {
            throw new IllegalArgumentException("Invalid or insufficent funds. ");
        }
    }
}
