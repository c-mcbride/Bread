package service;

import accounts.Transaction;
import accounts.UserAccount;
import utils.DateUtils;
import accounts.BankAccount;


import java.math.BigDecimal;
import java.time.LocalDate;


public class TransactionService {
    private final UserAccount userAccount;

    public TransactionService(UserAccount userAccount){
        if(userAccount == null){
            throw new IllegalArgumentException("UserAccount must not be null");
        }
        this.userAccount = userAccount;
    }

    public Transaction createTransaction(String dateStr, BankAccount bankAccount, String payee, String category, String memo, BigDecimal inflow, BigDecimal outflow){
        if(userAccount == null || bankAccount == null || payee == null){
            throw new IllegalArgumentException("UserAccount, BankAccount, and Payee must not be null ");
        }
        //Validate and parse date using DateUtils. If valid, date = date, else date = current date
        LocalDate date = DateUtils.getDateOrDefault(dateStr);

        if(!userAccount.isValidCategory(category)){
            throw new IllegalArgumentException("Invalid category: " + category);
        }

        //Create the transaction
        Transaction transaction = new Transaction(date, bankAccount, payee, category, memo, inflow, outflow);

        //Add the transaction to the BankAccount's transaction list
        bankAccount.addTransaction(transaction);

        return transaction;
    }
}
