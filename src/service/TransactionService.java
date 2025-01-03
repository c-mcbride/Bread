package service;

import accounts.Transaction;
import accounts.UserAccount;
import utils.DateUtils;
import accounts.BankAccount;

import java.math.BigDecimal;
import java.time.LocalDate;


public class TransactionService {
    public Transaction createTransaction(UserAccount userAccount, String dateStr, BankAccount bankAccount, String payee, String category, String memo, BigDecimal inflow, BigDecimal outflow){
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
