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
    private List<BankAccount> bankAccounts;
    private List<BudgetItem> fixedExpenses; //List of fixed expenses budget items
    private List<BudgetItem> variableExpenses;

    public UserAccount(String accountName){
        this.accountName = accountName;
        this.bankAccounts = new ArrayList<>(); //Empty List
        this.fixedExpenses = new ArrayList<>();
        this.variableExpenses = new ArrayList<>();
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
        return Collections.unmodifiableList(bankAccounts);
    }

    /**
     * Adds to the fixed expenses budget Item list
     * @param name name of the budget item
     * @param amount amount of money to add to the item on creation
     */
    public void addFixedBudgetItem(String name, BigDecimal amount){
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("Budget item name cannot be null or blank");
        }
        if(amount == null || amount.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Amount must be a non-negative value");
        }
        fixedExpenses.add(new BudgetItem(name, BudgetType.FIXED, amount));
    }

    /**
     * Adds to the variable expenses category
     * @param name name of the budget item
     * @param amount amount of money to add once the item is created
     */
    public void addVariableBudgetItem(String name, BigDecimal amount){
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("Budget item name cannot be null or blank");
        }
        if(amount == null || amount.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Amount must be a non-negative value");
        }
        variableExpenses.add(new BudgetItem(name, BudgetType. VARIABLE, amount));
    }

    //Methods to add/remove bank accounts or budget Items
    public void addBankAccount(BankAccount bankAccount){
        bankAccounts.add(bankAccount);
    }

    public void removeBankAccount(BankAccount bankAccount){
        if(!bankAccounts.remove(bankAccount)){
            throw new IllegalArgumentException("Bank account not found");
        }
        bankAccounts.remove(bankAccount);
    }
}
