package service;

import java.math.BigDecimal;
import accounts.BankAccount;
import utils.MoneyUtils;

public class BankAccountService {
    private BankAccount bankAccount;

    public BankAccountService (BankAccount bankAccount){
        this.bankAccount = bankAccount;
    }

    public String getAccountName(){
        return bankAccount.getAccountHolderName();
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
