package service;

import java.math.BigDecimal;
import accounts.BankAccount;

public class BankAccountService {
    private BankAccount bankAccount;

    public BankAccountService (BankAccount bankAccount){
        this.bankAccount = bankAccount;
    }

    /**
     * Factory method to create bank account, allows service layer to generate an account
     * @param accountName name for the bankaccount
     * @param initialDeposit the starting amount for the bankaccount
     * @return BankAccount Object
     */
    public static BankAccount createNewBankAccount(String accountName, BigDecimal initialDeposit){
        if(initialDeposit.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Initial Deposit must be non-negative");
        }

        BankAccount newAccount = new BankAccount(accountName, initialDeposit);
        return newAccount;
    }

    public String getAccountName(){
        return bankAccount.getName();
    }

    public BigDecimal getBalance(){
        return bankAccount.getBalance();
    }

    public String getFormattedBalance(){
        return bankAccount.getFormattedBalance();
    }

    /**
     * Deposits an amount in the bank account
     * @param amount Amount to deposit
     * @return Amount Deposited
     * @throws IllegalArgumentExcpetion if the deposited amount is invalid
     */
    public BigDecimal deposit(BigDecimal amount){
        return bankAccount.deposit(amount);
    }

    /**
     * Withdraws an amount from the bank account
     * @param amount to withdraw
     * @return amount withdrawn
     * @throws IllegalArgument Exception if the withdrawal amount is invalid or exceeds balance
     */
    public BigDecimal withdraw(BigDecimal amount){
        return bankAccount.withdraw(amount);
    }
}
