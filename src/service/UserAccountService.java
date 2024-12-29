package service;
import java.math.BigDecimal;
import java.util.List;

import accounts.BudgetItem;
import accounts.BudgetType;
import accounts.UserAccount;
import accounts.BankAccount;

//Service Layer for User Accounts to seperate buisness logic from Inputs
public class UserAccountService {
    private UserAccount userAccount;

    public UserAccountService(UserAccount userAccount){
        this.userAccount = userAccount;
    }

    /**
     *
     * @param budgetItemName Name of the budget item
     * @param type ENUM type: Fixed or Variable
     * @param amountToSpend Inital amount availble for budget item
     */
    public void addBudgetItem(String budgetItemName, BudgetType type, BigDecimal amountToSpend){
        if(type == BudgetType.FIXED){
            userAccount.addFixedBudgetItem(budgetItemName, amountToSpend);
        }
        else if (type == BudgetType.VARIABLE){
            userAccount.addVariableBudgetItem(budgetItemName, amountToSpend);
        }
    }

    /**
     * Takes Fixed and Variable expenses from Account class and formats them for console print
     * @return budgetString formated for viewing
     */
    public String viewCompleteBudget(){
        StringBuilder budgetSummary = new StringBuilder();

        budgetSummary.append("\nFixed Expenses: \n");
        for (BudgetItem item : userAccount.getFixedExpenses()){
            budgetSummary.append(String.format("%-20s $%.2f%n", item.getBudgetItemName(), item.getAmountToSpend()));
        }

        budgetSummary.append("\nVariable Expenses:\n");
        for(BudgetItem item : userAccount.getVariableExpenses()){
            budgetSummary.append(String.format("%-20s $%.2f%n", item.getBudgetItemName(), item.getAmountToSpend()));
        }

        return budgetSummary.toString();
    }

    public void addBankAccount(BankAccount bankAccount){
        userAccount.addBankAccount(bankAccount);
    }

    public List<BankAccount> getBankAccounts(){
        return userAccount.getBankAccounts();
    }

    /**
     * Method to build a string that formats the balance nicely to the console
     * @return String showing bank account summaries
     */
    public String viewBankAccounts(){
        StringBuilder bankAccountSummary = new StringBuilder();
        List<BankAccount> bankAccounts = userAccount.getBankAccounts();

        if(bankAccounts.isEmpty()){
            bankAccountSummary.append("No bank accounts available\n");
        }
        else{
            bankAccountSummary.append("\nAccounts\t\tBalance\n");
            bankAccountSummary.append("------------------------------------\n");
            for(BankAccount account : bankAccounts){
                bankAccountSummary.append(String.format("%-20s $%.2f%n", account.getName(), account.getBalance()));
            }
        }
        return bankAccountSummary.toString();
    }
}
