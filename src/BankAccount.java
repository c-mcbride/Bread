public class BankAccount {
    private String name;
    private int balance;

    public BankAccount(String name, int balance){
        this.name = name;
        this.balance = balance;
    }

    //Getters and setters
    public String getName(){
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public int getBalance(){
        return balance;
    }

    public void setBalance(int balance){
        this.balance = balance;
    }


    //Interface to update the balance of the account based on transactions
    //Used to reduce money from account
    public void subtractFromBalance(int amount){
        if(this.balance >= amount){
            this.balance -= amount;
        }
        else{
            System.out.println("You don't have enough money for this transaction");
        }
    }

    //Add money to account
    public void addToBalance (int amount){
        if(amount > 0){
            this.balance += amount;
        }
        else{
            System.out.println("Please enter a number greater than zero! ");
        }
    }

    public void displayBalance(){
        System.out.println("Account Balance: " + getBalance() + "$");
    }
}
