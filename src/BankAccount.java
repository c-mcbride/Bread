import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;


//Single responsiblity: hold account info and is able to provide balance, deposit orwithdraw.
public class BankAccount {
    private String accountHolderName;
    private BigDecimal balance;

    public BankAccount(String accountHolderName, BigDecimal initialBalance) {
        if(accountHolderName == null || accountHolderName.trim(). isEmpty()){
            throw new IllegalArgumentException("Account holder name cannot be null or empty");
        }
        if(initialBalance.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Initial balance cannot be negative. ")
        }
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance.setScale(2, RoundingMode.HALF_EVEN);
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public BigDecimal getBalance() {
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
