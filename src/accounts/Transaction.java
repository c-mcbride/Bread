package accounts;

import java.math.BigDecimal;
import java.time.LocalDate;



public class Transaction {
    private LocalDate date;
    private BankAccount bankAccount;
    private BudgetItem budgetItem;
    private String payee;
    private String memo; //Optional details for the transaction
    private BigDecimal inflow;
    private BigDecimal outflow;

    /**
     * Transaction object, when it is constructed, it will either be an inflow or outflow transaction, not both
     * The Constructer inforces this concept directly and defaults the opposite (i.e. inflow or outflow) to zero
     * @param date The date of the transaction, should default to today's date in service layer
     * @param bankAccount The bankaccount where the money is coming from or going to
     * @param payee The person or buisness reciving or sending the money
     * @param category What budget category is this associated with? i.e. which amount should be changed
     * @param memo Optional memo to leave about the transaction
     * @param inflow Money coming in
     * @param Outflow money going out
     */
    public Transaction(LocalDate date, BankAccount bankAccount, String payee, BudgetItem budgetItem, String memo, BigDecimal inflow, BigDecimal outflow){
        //Transaction cannot be both inflow and outflow, has to be one or the other
        if(inflow != null && outflow != null && inflow.compareTo(BigDecimal.ZERO) > 0 && outflow.compareTo(BigDecimal.ZERO) > 0){
            throw new IllegalArgumentException("A transaction cannot have both inflow and outflow values.");
        }
        if(inflow != null && inflow.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Inflow must be a non-negative value");
        }
        if(outflow != null && outflow.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Outflow must be a non-negative value. ");
        }
        if(inflow == null && outflow == null) {
            throw new IllegalArgumentException("Exactly one of inflow or outflow must be non-null.");
        }

        this.date = date;
        this.bankAccount = bankAccount;
        this.payee = payee;
        this.budgetItem = budgetItem;
        this.memo = (memo == null) ? "" : memo; //Memo optional, if it is null the string is "" else the memo is what is entered
        this.inflow = inflow == null ? BigDecimal.ZERO : inflow; //If inflow parameter is null, this.inflow = 0 else this.inflow = inflow
        this.outflow = outflow == null ? BigDecimal.ZERO : outflow; //If outflow is null, this.outflow = 0 else this.outflow = outflow
    }

    // Getters
    public LocalDate getDate() {
        return date;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public String getPayee() {
        return payee;
    }


    public String getMemo() {
        return memo;
    }

    public BigDecimal getInflow() {
        return inflow;
    }

    public BigDecimal getOutflow() {
        return outflow;
    }

    // For UI: Returns whether this transaction is an inflow or outflow
    public boolean isInflow() {
        return inflow.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isOutflow() {
        return outflow.compareTo(BigDecimal.ZERO) > 0;
    }
}
