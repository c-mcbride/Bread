import java.util.ArrayList;
import java.util.List;

public class Account {
    private String accountName;
    private List<BankAccount> bankAccounts;
    private List<BudgetItem> budgetItems;

    public Account(String accountName){
        this.accountName = accountName;
        this.bankAccounts = new ArrayList<>(); //Empty List
        this.budgetItems = new ArrayList<>();
    }

    //Getters
    public String getAccountName(){
        return accountName;
    }

    public List<BankAccount> getBankAccounts(){
        return bankAccounts;
    }

    public List<BudgetItem> getBudgetItems(){
        return budgetItems;
    }

    //Methods to add/remove bank accounts or budget Items
    public void addBankAccount(BankAccount bankAccount){
        bankAccounts.add(bankAccount);
    }

    public void removeBankAccount(BankAccount bankAccount){
        bankAccounts.remove(bankAccount);
    }

    
}
