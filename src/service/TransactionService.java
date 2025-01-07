package service;

import accounts.Transaction;
import accounts.UserAccount;
import accounts.BankAccount;
import accounts.BudgetItem;
import utils.DateUtils;


import java.math.BigDecimal;
import java.time.LocalDate;


public class TransactionService {
    //Instead of allowing the transaction service to access the user account directly, we will use the account service
    private final UserAccountService userAccountService;

    public TransactionService(UserAccountService userAccountService){
        if(userAccountService == null){
            throw new IllegalArgumentException("UserAccountService must not be null");
        }
        this.userAccountService = userAccountService;
    }

    /**
     *
     * @param dateStr string date, will be fixed via DateUtils, defaults to today it something is off
     * @param bankAccountName bankAccount that will hold the created transaction, entered as a string which is used to find the object
     * @param payee Who gets the money or provides the inflow
     * @param category What budget item will provide the money. Left as a string so the front end doesnt have to worry about finding it
     * @param memo optional memos
     * @param inflow if money comes in it is inflow
     * @param outflow if money is outflow, it takes away from the category
     * @return transaction object
     */
    public Transaction createTransaction(String dateStr, String bankAccountName, String payee, String category, String memo, BigDecimal inflow, BigDecimal outflow){
        if(bankAccountName == null || payee == null){
            throw new IllegalArgumentException("UserAccount, BankAccount, and Payee must not be null ");
        }
        //Validate and parse date using DateUtils. If valid, date = date, else date = current date
        LocalDate date = DateUtils.getDateOrDefault(dateStr);

        if(!userAccountService.isCategoryPresent(category)){
            throw new IllegalArgumentException("Invalid category: " + category);
        }

        //Find the bank account object
        BankAccount bankAccount = userAccountService.getBankAccountByName(bankAccountName);
        if(bankAccount == null){
            throw new IllegalArgumentException("Bank Account not found: " + bankAccountName);
        }

        //We need to get the budgetItem to create a transaction object
        BudgetItem budgetItem = userAccountService.getBudgetItemByCategory(category);
        if(budgetItem == null){
            throw new IllegalArgumentException("BudgetItem not found for category: " + category);
        }

        //Create the transaction to store in the bank account list
        Transaction transaction = new Transaction(date, bankAccount, payee, budgetItem, memo, inflow, outflow);



        return transaction;
    }

    private LocalDate parseDate(String dateStr){
        return DateUtils.getDateOrDefault(dateStr);
    }



}
