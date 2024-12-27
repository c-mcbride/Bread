import java.util.Scanner;
import java.math.BigDecimal;


//Here the user can create budget items
public class UserAccountMenu {
    private Scanner scanner = new Scanner(System.in);
    private UserAccount userAccount;

    public UserAccountMenu(UserAccount userAccount){
        this.userAccount = userAccount;
    }

    public void showUserMenu(){
        int choice = -1;

        do {
            System.out.println("\nUser Account Menu");
            System.out.println("1. Add Fixed Budget Item");
            System.out.println("2. Add Variable Budget Item");
            System.out.println("3. View Complete Budget");
            System.out.println("4. Exit");

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
                        addBudgetItem(BudgetType.FIXED);
                        break;
                    case 2:
                        addBudgetItem(BudgetType.VARIABLE);
                        break;
                    case 3:
                        viewCompleteBudget();
                        break;
                    default:
                        System.out.println("Invalid choice. Please select a valid option");
                }
            } catch (IllegalArgumentException e){
                System.out.println("Error: " + e.getMessage());
            }
        }
        while (choice != 4);
    }

    /**
     * Takes the item, looks at the enum and adds it to the appropriate list based on FIXED or VARIABLE type
     * @param type enum budget type FIXED or VARIABLE
     */
    private void addBudgetItem(BudgetType type){
        System.out.println("Adding a  " + type + " Budget Item: ");
        String itemName = getValidItemName();
        BigDecimal amount = getValidAmount();


        if(type == BudgetType.FIXED){
            userAccount.addFixedBudgetItem(itemName, amount);
        }
        if(type == BudgetType.VARIABLE){
            userAccount.addVariableBudgetItem(itemName, amount);
        }

        System.out.println(type + " Budget Item added succesfully");
    }

    /**
     * Prints both the variable and fixed budget sections
     */
    private void viewCompleteBudget(){
        System.out.println("\nFixed Expenses:");
        for (BudgetItem item : userAccount.getFixedExpenses()) {
            System.out.printf("%-20s $%.2f%n", item.getBudgetItemName(), item.getAmountToSpend());
        }

        System.out.println("\nVariable Expenses:");

        for (BudgetItem item : userAccount.getVariableExpenses()) {
            System.out.printf("%-20s $%.2f%n", item.getBudgetItemName(), item.getAmountToSpend());
        }
    }

    /**
     * Validates budget Item name to make sure it is not zero
     * @return non-empty string
     */
    private String getValidItemName(){
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
     * Validates inital amount to make sure that it is not negative
     * @return non-negative big decimal
     */
    private BigDecimal getValidAmount(){
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

}
