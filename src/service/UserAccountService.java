package service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

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
     * Creates budget item object and adds it to user list
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

    public void addBankAccount(BankAccount bankAccount){
        userAccount.addBankAccount(bankAccount);
    }

    /**
     * Creates bankaccount for the user and updates the starting balance in one go
     * addAmountToBeBudgeted goes from accountService -> UserAccount -> BudgetItem
     * @param bankAccount with account name and starting balance which will be used as inflow
     */
    public void createBankAccountAndUpdateBudget(BankAccount bankAccount){
        userAccount.addBankAccount(bankAccount);
        BigDecimal startingAmountToBeBudgeted = bankAccount.getBalance();
        userAccount.addAmountToBeBudgted(startingAmountToBeBudgeted);
    }

    public List<BankAccount> getBankAccounts(){
        return userAccount.getBankAccounts();
    }


    /**
     * Check to see if the string category that is passed in exists in the fixed or variable budgetItem list
     * @param category name of the category field of the budgetItem we are looking for
     * @return boolean describing if the budget item category exists in the BudgetItem list of the user account
     */
    public boolean isCategoryPresent(String category){
        List<BudgetItem> fixedExpenses = userAccount.getFixedExpenses();
        List<BudgetItem> variableExpenses = userAccount.getVariableExpenses();

        for(BudgetItem item : fixedExpenses){
            if(item.getBudgetItemCategory().equalsIgnoreCase(category)){
                return true;
            }
        }

        for(BudgetItem item : variableExpenses){
            if(item.getBudgetItemCategory().equalsIgnoreCase(category)){
                return true;
            }
        }

        return false;
    }

    /**
     * Search the user account fixed and variable expenses list to find if a budget item name matches param string
     * @param category budgetItem category string to search the budgetItem list for
     * @return budgetItem object if it is found, null if it is not
     */
    public BudgetItem getBudgetItemByCategory(String category){
        List<BudgetItem> fixedExpenses = userAccount.getFixedExpenses();
        List<BudgetItem> variableExpenses = userAccount.getVariableExpenses();

        for(BudgetItem budgetItem : fixedExpenses){
            if(budgetItem.getBudgetItemCategory().equalsIgnoreCase(category)){
                return budgetItem;
            }
        }

        for(BudgetItem budgetItem : variableExpenses){
            if(budgetItem.getBudgetItemCategory().equals(category)){
                return budgetItem;
            }
        }

        return null;
    }

    /**
     * Checks to see if there is an account with the given name in the list, if not returns null..if so return that account
     * @param name entered name for the bank account
     * @return the bankaccount with the given name if it exists
     */
    public BankAccount getBankAccountByName(String accountName){
        for(BankAccount account : userAccount.getBankAccounts()){
            if(account.getName().equalsIgnoreCase(accountName)){
                return account;
            }
        }
        System.out.println("bank account sending null");
        return null;
    }

    /**
     * Validates if a BankAccount with the given name exists in UserAccount
     * @param name name of the bank account to check for
     * @return boolean of it the account exists or not
     */
    public boolean hasBankAccount(String name){
        for(BankAccount account : userAccount.getBankAccounts()){
            if(account.getName().equalsIgnoreCase(name)){
                return true;
            }
        }

        return false;
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


    /**
     * Takes Fixed and Variable expenses from Account class and formats them for console print
     * @return budgetString formated for viewing
     */
    public String viewBankAccountAndBudget(){
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

    /**
     * Displays the amount to be budgeted.
    * @return String formatted with the amount to be budgeted.
    */
    public String viewAmountToBeBudgeted() {
        BigDecimal amountToBeBudgeted = userAccount.getAmountToBeBudgeted();
        return String.format("\nAmount to Be Budgeted: $%.2f\n", amountToBeBudgeted);
    }

    /**
     * Displays the complete budget, including bank accounts, fixed/variable expenses, and tobebudgeted
     * and the amount to be budgeted.
     * @return budgetString formatted for viewing.
     */
    public String viewCompleteBudget() {
        StringBuilder budgetSummary = new StringBuilder();

        // Add bank accounts section
        budgetSummary.append("\nBank Accounts:\n");
        budgetSummary.append(String.format("%-20s %-20s%n", "Account Name", "Balance"));
        budgetSummary.append("-------------------------------------------------\n");
        for (BankAccount account : userAccount.getBankAccounts()) {
            budgetSummary.append(String.format("%-20s $%.2f%n", account.getName(), account.getBalance()));
        }

        // Add amount to be budgeted
        budgetSummary.append(viewAmountToBeBudgeted());

        // Add fixed expenses section
        budgetSummary.append("\nFixed Expenses:\n");
        budgetSummary.append(String.format("%-20s %-20s%n", "Item Name", "Amount"));
        budgetSummary.append("-------------------------------------------------\n");
        for (BudgetItem item : userAccount.getFixedExpenses()) {
            budgetSummary.append(String.format("%-20s $%.2f%n", item.getBudgetItemName(), item.getAmountToSpend()));
        }

        // Add variable expenses section
        budgetSummary.append("\nVariable Expenses:\n");
        budgetSummary.append(String.format("%-20s %-20s%n", "Item Name", "Amount"));
        budgetSummary.append("-------------------------------------------------\n");
        for (BudgetItem item : userAccount.getVariableExpenses()) {
            budgetSummary.append(String.format("%-20s $%.2f%n", item.getBudgetItemName(), item.getAmountToSpend()));
        }

        return budgetSummary.toString();
    }

    /**
     * Method to build a string that formats the balance nicely to the console
     * @return String showing bank account summaries
     */
    public String viewBankAccountsString() {
        StringBuilder bankAccountSummary = new StringBuilder();
        List<BankAccount> bankAccounts = userAccount.getBankAccounts();

        if (bankAccounts.isEmpty()) {
            bankAccountSummary.append("No bank accounts available\n");
        } else {
            bankAccountSummary.append("\nAccounts\t\tBalance\n");
            bankAccountSummary.append("------------------------------------\n");
            for (BankAccount account : bankAccounts) {
                bankAccountSummary.append(String.format("%-20s $%.2f%n", account.getName(), account.getBalance()));
            }
        }
        return bankAccountSummary.toString();
    }

    /**
    * Formats and prints the valid categories associated with the user account.
    */
    public void printCategories() {
        Set<String> categories = userAccount.getCategories();

        if (categories == null || categories.isEmpty()) {
            System.out.println("No categories available.");
            return;
        }

        System.out.println("\nCategories:");
        System.out.println("--------------------------------");
        for (String category : categories) {
            System.out.println("- " + category);
        }
        System.out.println("--------------------------------");
    }
}
