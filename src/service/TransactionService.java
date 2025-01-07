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
        if(bankAccountName == null || payee == null || category == null){
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

        //Apply the transaction so bank account balance and budget item amount remaining are updated
        applyTransaction(transaction, bankAccount, budgetItem);

        return transaction;
    }

    /**
     * Applies a transaction to the bank account and budget item.
     * If the transaction is inflow, money is added to both the bank account the budgetItem amount to spend
     * if the transaction is outflow, money is withdrawn from the bank account and subtracted from the budget item
     *
     * @param transaction transaction object will be added to bankaccount list
     * @param bankAccount the bankAccount to which this transaction needs to be applied
     * @param budgetItem the budgetItem itself so we can see if we need to add or subtract from amount left to spend
     */
    private void applyTransaction(Transaction transaction, BankAccount bankAccount, BudgetItem budgetItem){
        //Add the transaction to the bank account list
        bankAccount.addTransaction(transaction);

        //If the budget item is inflow, we add money to the budget item amount left to spend
        if(transaction.isInflow()){
            bankAccount.deposit(transaction.getInflow());
            budgetItem.addMoneyToCategory(transaction.getInflow());
        }
        else if (transaction.isOutflow()){
            bankAccount.withdraw(transaction.getOutflow());
            budgetItem.subtractMoneyFromCategory(transaction.getOutflow())
        }
    }
}
