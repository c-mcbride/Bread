# Bread Java Project

This is a simple Java project named "Bread". It starts as a console application and will later integrate JavaFX for a graphical user interface. The goal is to create a budget application
useful for budgeting decisions moment to moment. This will be presented with a clean ui and darkmode, hopefully with a dracula like theme.

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/bread.git
   ```
2. Make sure you have JDK 11 or later installed.
3. Open the project in VS Code and run it using the "Run" button.

## Usage

- You can modify the `Main.java` file to start adding more functionality.
- The project currently prints "Hello, Bread!" to the console.

## License

This project is licensed under the MIT License.


##Class descriptions
###BankAccount
-Stores account holder information and balance
-Handles deposits and withdrawl operations using 'BigDecimal'

##BankAccountMenu
-Provides functionality for account management for console application
-Used to seperate core functionality from menu controls
-Allows users to view balance, deposit, withdraw or exit

###BreadApp
-Holds logic for console menu

###CreateAccountMenu
-Interacts with the user to create a new account
-Validates user input for name and initial deposit

###MoneyUtils
-Contains utility methods for finincial operations
-Ensures proper rounding and formatting for currency
