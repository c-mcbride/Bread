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
    private String budgetItemCategory; //What is this item called?
    private BudgetType type; //Enum list for allowed categories
    private BigDecimal amountToSpend; //How much is left?

    public BudgetItem(String budgetItemCategory, BudgetType type){
        if(budgetItemCategory == null || budgetItemCategory.trim().isEmpty()){
            throw new IllegalArgumentException("Budget item name cannot be null or empty");
        }
        if(type == null){
            throw new IllegalArgumentException("Type cannot be null");
        }

        this.budgetItemCategory = budgetItemCategory;
        this.type = type;
        this.amountToSpend = BigDecimal.ZERO; //When the budget item is created, should default to 0
    }

    /**
     * Add money to budget category
     * @param amount to add to category
     * @returns updated amount avaliable for the category
     */
    public BigDecimal addMoneyToCategory(BigDecimal amountToAdd){
        if(!MoneyUtils.isValidAmount(amountToAdd)){
            throw new IllegalArgumentException("Amount to add must be greater than zero");
        }
        amountToSpend = amountToSpend.add(MoneyUtils.round(amountToAdd));
        return amountToSpend;
    }

    /**
     * Take money from budget category. Can be negative
     * @param amount to subtract from category
     * @return updated amount avaliable for category
     */
    public BigDecimal subtractMoneyFromCategory(BigDecimal amountToSubtract){
        if (amountToSubtract == null || !MoneyUtils.isValidAmount(amountToSubtract)){
            throw new IllegalArgumentException("Amount to spend must be greater than 0");
        }

        //This subtracts the money left over for the category to spend and return it, this can be negative
        amountToSpend = amountToSpend.subtract(MoneyUtils.round(amountToSubtract));
        return amountToSpend;
    }


     /**
     * Get the name of the budget item.
     * @return the budget item name
     */
    public String getBudgetItemCategory(){
        return budgetItemCategory;
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
}
