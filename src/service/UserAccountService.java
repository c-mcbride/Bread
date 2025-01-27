package service;
import java.math.BigDecimal;
import java.util.List;

import accounts.BudgetItem;
import accounts.BudgetType;
import accounts.UserAccount;
import utils.MoneyUtils;
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
    public void addBudgetItem(String budgetItemName, BudgetType type){
        if(type == BudgetType.FIXED){
            userAccount.addFixedBudgetItem(budgetItemName);
        }
        else if (type == BudgetType.VARIABLE){
            userAccount.addVariableBudgetItem(budgetItemName);
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

        //If we need the solo to be Budgeted Category, we get it here
        if(category.equalsIgnoreCase("To Be Budgeted")){
            return userAccount.getToBeBudgted();
        }

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
     * Takes two strings, a source and destination, and moves money between the categories
     * Includes functionality to move money from to be budgeted to other places
     * @param sourceCategory the category providing the funds
     * @param targetCategory where the funds are going
     * @param amountToMove how much money to move
     */
    public void moveMoneyBetweenCategories(String sourceCategory, String targetCategory, BigDecimal amountToMove){
        if(sourceCategory == null || targetCategory == null || amountToMove == null){
            throw new IllegalArgumentException("When moving money, Source, target, and amount must not be null");
        }
        if(amountToMove.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Amount of money that you are moving must be greater than 0");
        }

        //Use the getBudgetItemByCategory method to find the object's from the string
        BudgetItem sourceBudgetItem = getBudgetItemByCategory(sourceCategory);
        BudgetItem targetBudgetItem = getBudgetItemByCategory(targetCategory);

        if(sourceBudgetItem == null){
            throw new IllegalArgumentException("Source category not found: " + sourceCategory);
        }

        if(targetBudgetItem == null){
            throw new IllegalArgumentException("Target category not found: " + targetCategory);
        }

        //Check to make sure that the source budget Item has enough avaliable to move
        BigDecimal availableAmount = sourceBudgetItem.getAmountToSpend();
        if(availableAmount.compareTo(amountToMove) < 0){
            throw new IllegalArgumentException("Insufficient funds in source category: Available " + availableAmount + ", Requested " + amountToMove);
        }

        //Perform the transfer i.e subtract from source and add to target
        sourceBudgetItem.subtractMoneyFromCategory(amountToMove);
        targetBudgetItem.addMoneyToCategory(amountToMove);
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
            budgetSummary.append(String.format("%-20s $%.2f%n", item.getBudgetItemCategory(), item.getAmountToSpend()));
        }

        budgetSummary.append("\nVariable Expenses:\n");
        for(BudgetItem item : userAccount.getVariableExpenses()){
            budgetSummary.append(String.format("%-20s $%.2f%n", item.getBudgetItemCategory(), item.getAmountToSpend()));
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
            budgetSummary.append(String.format("%-20s $%.2f%n", item.getBudgetItemCategory(), item.getAmountToSpend()));
        }

        // Add variable expenses section
        budgetSummary.append("\nVariable Expenses:\n");
        budgetSummary.append(String.format("%-20s %-20s%n", "Item Name", "Amount"));
        budgetSummary.append("-------------------------------------------------\n");
        for (BudgetItem item : userAccount.getVariableExpenses()) {
            budgetSummary.append(String.format("%-20s $%.2f%n", item.getBudgetItemCategory(), item.getAmountToSpend()));
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
        //Retrieve the fixed and variable budget items
        List<BudgetItem> fixedExpenses = userAccount.getFixedExpenses();
        List<BudgetItem> variableExpenses = userAccount.getVariableExpenses();

        //Check if there are any categories avaliable to print
        if((fixedExpenses == null || fixedExpenses.isEmpty()) && (variableExpenses == null || variableExpenses.isEmpty())){
            System.out.println("No categories avaliable");
            return;
        }

        //Print fixed categories
        System.out.println("\nFixed Expenses");
        System.out.println("--------------------------------");

        if(fixedExpenses != null && !fixedExpenses.isEmpty()){
            for(BudgetItem item : fixedExpenses){
                System.out.println("- " + item.getBudgetItemCategory() + " (" + item.getAmountToSpend() + ")");
            }
        }
        else{
            System.out.println("No fixed categories. ");
        }
        System.out.println("--------------------------------");

        //Print variable categories
        System.out.println("\nVariable Expenses");
        System.out.println("--------------------------------");

        if(variableExpenses != null && !variableExpenses.isEmpty()){
            for(BudgetItem item : variableExpenses){
                System.out.println("- " + item.getBudgetItemCategory()+ " (" + item.getAmountToSpend()+ ") ");
            }
        }
        else{
            System.out.println("No variable categories. ");
        }
        System.out.println("--------------------------------");
    }
}
