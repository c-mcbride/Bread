import java.math.BigDecimal;

/**
 * Budget items are used as categories that the user is budgeting for
 * Responsiblities:
 * -Contains the name of the item (groceries, gas etc)
 * -Contains the category (fixed, variable)
 * -Contains amount left
 */
public class BudgetItem {
    private String budgetItemName; //What is this item called?
    private String category; //Fixed or variable?
    private BigDecimal amountToSpend; //How much is left?

    public BudgetItem(String budgetItemName, String category, BigDecimal amountToSpend){
        if(budgetItemName == null || budgetItemName.trim().isEmpty()){
            throw new IllegalArgumentException("Budget item name cannot be null or empty")
        }
        if(category == null || category.trim().isEmpty()){
            throw new IllegalArgumentException("category cannot be empty or null");
        }
        if(amountToSpend == null || amountToSpend.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Amount to spend cannot be null or negative when budget item is created");
        }

        this.budgetItemName = budgetItemName;
        this.category = category;
        this.amountToSpend = BigDecimal.ZERO;
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
     * @return the budget item category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Get the remaining amount to spend for this item.
     * @return the amount to spend
     */
    public BigDecimal getAmountToSpend() {
        return amountToSpend;
    }
}
