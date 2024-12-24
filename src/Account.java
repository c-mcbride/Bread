import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

/**
 * This class stores a user account, it has a name,
 * Bank Account list, variable and fixed expenses lists
 */
public class Account {
    private String accountName;
    private List<BankAccount> bankAccounts;
    private List<BudgetItem> fixedExpenses; //List of fixed expenses budget items
    private List<BudgetItem> variableExpenses;

    public Account(String accountName){
        this.accountName = accountName;
        this.bankAccounts = new ArrayList<>(); //Empty List
        this.fixedExpenses = new ArrayList<>();
        this.variableExpenses = new ArrayList<>();
    }

    //Getters
    public String getAccountName(){
        return accountName;
    }

    public List<BankAccount> getBankAccounts(){
        return bankAccounts;
    }

    /**
     * Adds to the fixed expenses budget Item list
     * @param name name of the budget item
     * @param amount amount of money to add to the item on creation
     */
    public void addFixedBudgetItem(String name, BigDecimal amount){
        fixedExpenses.add(new BudgetItem(name, BudgetType.FIXED, amount));
    }

    /**
     * Adds to the variable expenses category
     * @param name name of the budget item
     * @param amount amount of money to add once the item is created
     */
    public void addVariableBudgetItem(String name, BigDecimal amount){
        variableExpenses.add(new BudgetItem(name, BudgetType. VARIABLE, amount));
    }



    //Methods to add/remove bank accounts or budget Items
    public void addBankAccount(BankAccount bankAccount){
        bankAccounts.add(bankAccount);
    }

    public void removeBankAccount(BankAccount bankAccount){
        bankAccounts.remove(bankAccount);
    }

}
