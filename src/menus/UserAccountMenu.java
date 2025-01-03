package menus;
import java.util.Scanner;
import java.math.BigDecimal;

import accounts.BudgetType;
import accounts.Transaction;
import accounts.UserAccount;
import accounts.BankAccount;
import service.BankAccountService;
import service.UserAccountService;
import utils.DateUtils;
import utils.MoneyUtils;
import service.TransactionService;
import java.time.LocalDate;

//Here the user can create budget items
public class UserAccountMenu {
    private Scanner scanner = new Scanner(System.in);
    private UserAccount userAccount;
    private UserAccountService userAccountService;
    private TransactionService transactionService;

    public UserAccountMenu(UserAccount userAccount){
        this.userAccountService = new UserAccountService(userAccount);
    }

    public void showUserMenu(){
        int choice = -1;

        do {
            System.out.println("\nUser Account Menu");
            System.out.println("1. Add Bank Account");
            System.out.println("2. Add Fixed Budget Item");
            System.out.println("3. Add Variable Budget Item");
            System.out.println("4. View Complete Budget");
            System.out.println("5. Add Transaction");
            System.out.println("6. Exit");

            if(scanner.hasNextInt()){
                choice = scanner.nextInt();
                scanner.nextLine();
            }
            else{
                System.out.println("Invalid input. Please enter a number");
                continue;
            }

            try{
                switch (choice){
                    //Add fixed Budget Item
                    case 1:
                        addBankAccount();
                        break;
                    case 2:
                        addBudgetItem(BudgetType.FIXED);
                        break;
                    case 3:
                        addBudgetItem(BudgetType.VARIABLE);
                        break;
                    case 4:
                        viewCompleteBudget();
                        break;
                    case 5:
                        addTransaction();
                        break;
                    case 6:
                        System.out.println("Exiting account menu.....");
                        break;
                    default:
                        System.out.println("Invalid choice. Please select a valid option");
                }
            } catch (IllegalArgumentException e){
                System.out.println("Error: " + e.getMessage());
            }
        }
        while (choice != 6);
    }

    private void addTransaction(){
        BigDecimal inflow = null;
        BigDecimal outflow = null;

        System.out.println("Enter transaction details: ");

        //Get date
        LocalDate date = getTransactionDate();

        //Collecting bank account
        BankAccount bankAccount = selectBankAccount();

        //Collecting payee
        String payee = getValidPayee();

        //Collecting category
        String category = getValidCategoryName();

        //Collecting memo
        String memo = getValidMemo();

        String transactionType = getTransactionType();
        BigDecimal transactionAmount = getTransactionAmount();

        if(transactionType == "inflow"){
            inflow = transactionAmount;
        }
        else{
            outflow = transactionAmount;
        }

        //Create the transaction
        try{
            transactionService.createTransaction(transactionType, bankAccount, payee, category, memo, inflow, outflow);
            System.out.println("Transaction added succesfully.");
        }
        catch(IllegalArgumentException e){
            System.out.println("Error: ");
        }
    }

    private BigDecimal getTransactionAmount(){
        while(true){
            System.out.println("Transaction amount: ");

            if(scanner.hasNextBigDecimal()){
                BigDecimal transactionAmount = scanner.nextBigDecimal();
                scanner.nextLine();

                if(MoneyUtils.isValidAmount(transactionAmount)){
                    return MoneyUtils.round(transactionAmount);
                }
                else{
                    System.out.println("Amount must be a non-negative value");
                }
            }
            else{
                System.out.println("Invalid input. Please enter a valid decimal value. ");
                scanner.nextLine()
            }
        }
    }

    private String getTransactionType(){
        while(true){
            System.out.println("Is this an inflow or outflow? (Enter 'inflow' or 'outflow')");
            String type = scanner.nextLine().trim().toLowerCase();

            if(type.equals("inflow") || type.equals("outflow")){
                return type;
            }
            else{
                System.out.println("Invalid input. Please enter 'inflow' or 'outflow'.");
            }
        }
    }

    private LocalDate getTransactionDate(){
        System.out.println("Enter the date of the transaction(MM-dd-yyyy): ");
        String dateStr = scanner.nextLine().trim();
        return DateUtils.getDateOrDefault(dateStr);
    }

    private String getValidCategoryName(){
        while(true){
            userAccountService.printCategories();
            System.out.println("Enter BudgetCategory: ");
            String category = scanner.nextLine().trim();

            if(userAccountService.isValidCategory(category)){
                return category;
            }
            else{
                System.out.println("Invalid category. Please try again.")
            }
        }
    }


    public String getValidMemo() {
        System.out.print("Enter a memo for the transaction (or press Enter to leave blank): ");
        String memo = scanner.nextLine();

        // Trim whitespace and check if the string is empty
        if (memo == null || memo.trim().isEmpty()) {
            return null; // Default to null if no memo is provided
        }

        return memo.trim(); // Return the cleaned-up memo
    }

    private String getValidPayee(){
        System.out.println("Enter the payee(person/buisness): ");
        return scanner.nextLine().trim();
    }

    private BankAccount selectBankAccount(){
        while(true){
            //Display bank accounts
            userAccountService.viewBankAccountsString();
            System.out.println("Enter the bank account name: ");
            String accountName = scanner.nextLine().trim();

            if(userAccountService.isValidBankAccount(accountName)){
                return userAccountService.getBankAccountByName(accountName);
            }
            else{
                System.out.println("Invalid bank account name. Please enter a valid bank account name. ");
            }
        }
    }

    /**
     * Adds bank account to user account list
     */
    private void addBankAccount(){
        System.out.println("Add Bank Account: ");
        String bankAccountName = getValidBankAccountName();
        BigDecimal initialDeposit = getValidStartingBalance();

        try{
            //Create the new bankaccount
            BankAccount newBankAccount = BankAccountService.createNewBankAccount(bankAccountName, initialDeposit);
            //BankAccountService bankAccountService = new BankAccountService(newBankAccount);
            userAccountService.createBankAccountAndUpdateBudget(newBankAccount);
            System.out.println("Bank account created successfully.");
        }catch(IllegalArgumentException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    private String getValidBankAccountName(){
        while(true){
            System.out.println("Enter Bank Account name: ");
            String itemName = scanner.nextLine().trim();
            if(!itemName.isEmpty()){
                return itemName;
            }
            System.out.println("Error: Name cannot be empty. Please try again");
        }
    }

    /**
     *
     * @return initialBalance for bankAccount
     */
    private BigDecimal getValidStartingBalance(){
        while(true){
            System.out.println("Enter Starting Balance for Bank Account: ");
            if (scanner.hasNextBigDecimal()){
                BigDecimal initialDeposit = scanner.nextBigDecimal();
                scanner.nextLine(); //Consume the newline character
                if(initialDeposit.compareTo(BigDecimal.ZERO) >= 0){
                    return initialDeposit;
                }
                else{
                    System.out.println("Error: Starting Balance must be non-negative");
                }
            }
            else{
                System.out.println("Invalid input. Please enter a valid decimal value. ");
                scanner.nextLine();
            }
        }
    }

    /**
     * Validates inital amount to make sure that it is not negative
     * @return non-negative big decimal
     */
    private BigDecimal getValidBudgetAmount(){
        while(true){
            System.out.println("Enter the starting amount for the budget item: ");
            if(scanner.hasNextBigDecimal()){
                BigDecimal amount = scanner.nextBigDecimal();
                scanner.nextLine(); // clear te newline character
                if(amount.compareTo(BigDecimal.ZERO) >= 0){
                    return amount;
                }
                else{
                    System.out.println("Error: amount must be non-negative");
                }
            }
            else{
                System.out.println("Invalid input. Please enter a valid decimal value.");
                scanner.nextLine(); //Clear invalid input
            }
        }
    }

     /**
     * Validates budget Item name to make sure it is not zero
     * @return non-empty string
     */
    private String getValidBudgetItemName(){
        while(true){
            System.out.println("Enter Budget item name: ");
            String itemName = scanner.nextLine().trim();
            if(!itemName.isEmpty()){
                return itemName;
            }
            System.out.println("Error: Name cannot be empty. Please try again");
        }
    }

    /**
     * Takes the item, looks at the enum and adds it to the appropriate list based on FIXED or VARIABLE type
     * User account service passes this to the UserAccount class, we dont want the menu to interact directly with the
     * User account class.
     * UserAccoutMenu -> UserAccountService -> UserAccount
     * @param type enum budget type FIXED or VARIABLE
     */
    private void addBudgetItem(BudgetType type){
        System.out.println("Adding a  " + type + " Budget Item: ");
        String itemName = getValidBudgetItemName();
        BigDecimal amount = getValidBudgetAmount();
        userAccountService.addBudgetItem(itemName, type, amount);
    }

     /**
     * Prints both the variable and fixed budget sections
     */
    private void viewCompleteBudget(){
        System.out.println(userAccountService.viewCompleteBudget());
    }

    /**
     * Prints bank account list from userAccount
     */
    public void viewBankAccounts(){
        System.out.println(userAccountService.viewBankAccounts());
    }

    public void viewAmountToBeBudgeted(){
        System.out.println(userAccountService.viewAmountToBeBudgeted());
    }

}
