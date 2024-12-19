import java.math.BigDecimal;

//Single responsiblity: hold account info and is able to provide balance, deposit or withdraw.
public class BankAccount {
    private String accountHolderName;
    private BigDecimal balance;

    public BankAccount(String accountHolderName, BigDecimal initialBalance) {
        if(accountHolderName == null || accountHolderName.trim().isEmpty()){
            throw new IllegalArgumentException("Account holder name cannot be null or empty");
        }
        if(initialBalance.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Initial balance cannot be negative. ");
        }
        this.accountHolderName = accountHolderName;
        this.balance = MoneyUtils.round(initialBalance); //Use the MoneyUtils class
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getFormattedBalance(){
        return MoneyUtils.formatCurrency(balance);
    }

    //isValidAmount makes sure that the entered amount is greater than zero
    public BigDecimal deposit(BigDecimal amount){
        if(!MoneyUtils.isValidAmount(amount)){ //If isValidAmount returns false, meaning value is less than zero
            throw new IllegalArgumentException("Deposit amount must be greater than zero.");
        }
        balance = balance.add(MoneyUtils.round(amount));
        return MoneyUtils.round(amount);
    }

    public BigDecimal withdraw(BigDecimal amount){
        if(!MoneyUtils.isValidAmount(amount)){
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero.");
        }
        if(amount.compareTo(balance) > 0){
            throw new IllegalArgumentException("Insufficient funds for this withdrawal.");
        }
        balance = balance.subtract(MoneyUtils.round(amount));
        return MoneyUtils.round(amount);
    }

}
