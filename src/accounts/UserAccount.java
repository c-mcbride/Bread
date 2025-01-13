package accounts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.math.BigDecimal;

/**
 * This class stores a user account, it has a name,
 * Bank Account list, variable and fixed expenses lists
 */
public class UserAccount {
    private String accountName;
    private String hashedPin;
    private List<BankAccount> bankAccounts;
    private List<BudgetItem> fixedExpenses; //List of fixed expenses budget items
    private List<BudgetItem> variableExpenses;
    private BudgetItem toBeBudgted; //Holds money that needs to be delegated to budget items

    public UserAccount(String accountName, String hashedPin){
        this.accountName = accountName;
        this.hashedPin = hashedPin;
        this.bankAccounts = new ArrayList<>(); //Empty List
        this.fixedExpenses = new ArrayList<>();
        this.variableExpenses = new ArrayList<>();
        //When object is constructed, we created a To Be Budgeted item, of ENUM:TOBEBUDGTED, with zero dollars
        this.toBeBudgted = new BudgetItem("To Be Budgeted", BudgetType.TOBEBUDGTED);
    }

    //Getters
    public String getAccountName(){
        return accountName;
    }

    //Getters for both expenses list
    public List<BudgetItem> getFixedExpenses(){
        return Collections.unmodifiableList(fixedExpenses);
    }

    public List<BudgetItem> getVariableExpenses(){
        return Collections.unmodifiableList(variableExpenses);
    }

    //Returning an unmodifiable view to prevent external code from modifying this directly
    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    //Get the entire toBeBudgeted object
    public BudgetItem getToBeBudgted(){
        return toBeBudgted;
    }

    /**
     * Adds to the fixed expenses budget Item list
     * @param name name of the budget item
     * @param amount amount of money to add to the item on creation
     */
    public void addFixedBudgetItem(String category){
        validateBudgetItem(category);
        fixedExpenses.add(new BudgetItem(category, BudgetType.FIXED));
    }

    /**
     * Adds to the variable expenses category
     * @param name name of the budget item
     * @param amount amount of money to add once the item is created
     */
    public void addVariableBudgetItem(String category){
        validateBudgetItem(category);
        variableExpenses.add(new BudgetItem(category, BudgetType.VARIABLE));
    }

    //Methods to add/remove bank accounts or budget Items
    public void addBankAccount(BankAccount bankAccount){
        bankAccounts.add(bankAccount);
    }

    /**
     * Looks for bankaccount to remove
     * @param bankAccount bankAccount to remove
     */
    public void removeBankAccount(BankAccount bankAccount){
        if(!bankAccounts.remove(bankAccount)){
            throw new IllegalArgumentException("Bank account not found");
        }
        bankAccounts.remove(bankAccount);
    }

    /**
     * Used to modify the toBeBudget object variable
     * @param toBeBudgted the amount to add to the toBeBudgeted category
     */
    public void addAmountToBeBudgted(BigDecimal amountToAdd){
        toBeBudgted.addMoneyToCategory(amountToAdd);
    }

    public void subtractToBeBudgeted(BigDecimal amountToSubtract){
        toBeBudgted.subtractMoneyFromCategory(amountToSubtract)
    }

    /**
     * Goes to the amount to be budgeted budget item and gets the amount left to spend
     * @return amount to spend in the to be budgeted category
     */
    public BigDecimal getAmountToBeBudgeted(){
        return toBeBudgted.getAmountToSpend();
    }


    private void validateBudgetItem(String category){
        if (category == null || category.isBlank()) {
            throw new IllegalArgumentException("Budget item name cannot be null or blank");
        }
    }

    /**
     * Return the hashed pin to the caller
     * @return hashedPin as is
     */
    public String getHashedPin(){
        return hashedPin;
    }
}
