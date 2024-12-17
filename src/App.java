public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to Bread!");

        //Create bankAccountObject
        BankAccount bankAccount = new BankAccount("New Account", 3000);

        //Show menu to modify the bankAccountObject Created above
        BankAccountMenu bankAccountMenu = new BankAccountMenu(bankAccount);
        bankAccountMenu.showMenu();
        return;
    }
}
