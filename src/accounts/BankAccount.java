package accounts;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import utils.MoneyUtils;

/**
 * Represents a bank account with basic operations for managing balance
 * Responsiblities:
 * -Store account holder name
 * -Handle deposits and withdrawl operations with validation
 */
public class BankAccount {
    private String accountName;
    private BigDecimal balance;
    private List<Transaction> transactions;

    //Bank account constructor with validation. Makes sure that the starting balance is greater than or equal to 0
    public BankAccount(String accountName, BigDecimal initialBalance) {
        if(accountName == null || accountName.trim().isEmpty()){
            throw new IllegalArgumentException("Account holder name cannot be null or empty");
        }
        if(initialBalance.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Initial balance cannot be negative. ");
        }
        this.accountName = accountName;
        this.balance = MoneyUtils.round(initialBalance); //Use the MoneyUtils class
        this.transactions = new ArrayList<>();
    }

    public String getName() {
        return accountName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getFormattedBalance(){
        return MoneyUtils.formatCurrency(balance);
    }

    /**
     * Adds money to the account of a given amount
     * @param amount to deposit, must be non-negative
     * @returns the deposited amount if sucessfull
     * @throws illegal argument exception if param is less than 1
     */
    public BigDecimal deposit(BigDecimal amount){
        if(!MoneyUtils.isValidAmount(amount)){ //If isValidAmount returns false, meaning value is less than zero
            throw new IllegalArgumentException("Deposit amount must be greater than zero.");
        }
        balance = balance.add(MoneyUtils.round(amount));
        return MoneyUtils.round(amount);
    }

    /**
     * Takes money out of the account of the given amount
     * @param amount to withdraw, must be non-negative
     * @returns the withdrawn amount if sucessfull
     * @throws illegal argument exception if the amount is less than 1 or if the balance is too low
     */
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

    public void addTransaction(Transaction transaction){
        transactions.add(transaction);
    }
}
