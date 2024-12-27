package accounts;
import java.math.BigDecimal;
import utils.MoneyUtils;

/**
 * Budget items are used as categories that the user is budgeting for
 * Responsiblities:
 * -Contains the name of the item (groceries, gas etc)
 * -Contains the category (fixed, variable)
 * -Contains amount left
 */
public class BudgetItem {
    private String budgetItemName; //What is this item called?
    private BudgetType type; //Enum list for allowed categories
    private BigDecimal amountToSpend; //How much is left?

    public BudgetItem(String budgetItemName, BudgetType type, BigDecimal amountToSpend){
        if(budgetItemName == null || budgetItemName.trim().isEmpty()){
            throw new IllegalArgumentException("Budget item name cannot be null or empty");
        }
        if(type == null){
            throw new IllegalArgumentException("Type cannot be null");
        }
        if(amountToSpend == null || amountToSpend.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Amount to spend cannot be null or negative when budget item is created");
        }

        this.budgetItemName = budgetItemName;
        this.type = type;
        this.amountToSpend = amountToSpend; //Keep an eye on this later, this may produce unwanted behavior
    }

    /**
     * Add money to budget category
     * @param amount to add to category
     * @returns updated amount avaliable for the category
     */
    public BigDecimal addMoneyToCategory(BigDecimal amountToAdd){
        if(!MoneyUtils.isValidAmount(amountToAdd)){
            throw new IllegalArgumentException("Amount to add must be greater than zero")
        }
        amountToSpend = amountToSpend.add(amountToAdd);
        return amountToSpend;
    }

    /**
     * Take money from budget category. Can be negative
     * @param amount to subtract from category
     * @return updated amount avaliable for category
     */
    public BigDecimal subtractMoneyFromCategory(BigDecimal amountToSpend){
        if (amountToSpend == null || !MoneyUtils.isValidAmount(amountToSpend)){
            throw new IllegalArgumentException("Amount to spend must be greater than 0");
        }

        //This subtracts the money left over for the category to spend and return it, this can be negative
        amountToSpend = this.amountToSpend.subtract(MoneyUtils.round(amountToSpend));
        return amountToSpend;
    }


     /**
     * Get the name of the budget item.
     * @return the budget item name
     */
    public String getBudgetItemName() {
        return budgetItemName;
    }

    /**
     * Get the category of the budget item.
     * @return the budget item Type
     */
    public BudgetType getType() {
        return type;
    }

    /**
     * Get the remaining amount to spend for this item.
     * @return the amount to spend
     */
    public BigDecimal getAmountToSpend() {
        return amountToSpend;
    }

    @Override
    public String toString() {
        return "BudgetItem{" +
                "budgetItemName='" + budgetItemName + '\'' +
                ", type=" + type +
                ", amountToSpend=" + amountToSpend +
                '}';
    }
}
