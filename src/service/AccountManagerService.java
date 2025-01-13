package service;

import accounts.UserAccount;

import java.util.HashMap;
import java.util.Map;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class AccountManagerService {
    private Map<String, UserAccount> userAccounts = new HashMap<>();


    /**
     * Adds a new user account with a hashed pin
     *
     * @param userAccountName The name of the user account.
     * @param pin              THe user's PIN(plain text, to be hashed )
     */
    public void addAccount(String userAccountName, String pin){
        if(userAccountName == null || pin == null){
            throw new IllegalArgumentException("User account name and PIN cannot be null.");
        }
        if(userAccounts.containsKey(userAccountName)){
            throw new IllegalArgumentException("Account already exists with this name: " + userAccountName);
        }
        String hashedPin = hashPin(pin);
        userAccounts.put(userAccountName, new UserAccount(userAccountName, hashedPin));
    }

    /**
     * Retrieve an account from the list (validated) and enter the pin. If everything checks out, the account object is returned
     *
     * @param userAccountName this is the account we are searching for
     * @param pin pin that validates the account
     * @return If the account is not on the list or the PIN is wrong, throw an error
     */
    public UserAccount login(String userAccountName, String pin){
        if(userAccountName == null || pin == null){
            throw new IllegalArgumentException("Account name and PIN cannot be null");
        }

        UserAccount userAccount = userAccounts.get(userAccountName);

        if(userAccount == null || !validatePin(pin, userAccount.getHashedPin())){
            throw new IllegalArgumentException("Invalid account name or PIN. ");
        }

        return userAccount;
    }

    /**
     * Hashes a PIN using SHA-256
     *
     * @param pin The plain text PIN to be hashed.
     * @return The hashed PIN as a hexidecimal string
     */
    private String hashPin(String pin){
        try{
            //Create a MessageDigest instance for SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            //Compute the has as bytes
            byte[] hashBytes = digest.digest(pin.getBytes());

            //Convert the hashbytes to a hexadecimalString
            StringBuilder hexString = new StringBuilder();
            for(byte b : hashBytes){
                String hex = Integer.toHexString(0xff & b);
                if(hex.length() == 1){
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        }
        catch(NoSuchAlgorithmException e){
            throw new RuntimeException("Error hashing PIN: SHA-256 algorithm not found.", e);
        }
    }

    /**
     * Validates a PIN by comparing its hash to the stored hash.
     *
     * @param inputPin  The plain text PIN provided by the user.
     * @param storedHash THe hashed PIN stored in the system.
     * @return true if the PIN is valid, false otherwise
     */
    private boolean validatePin(String inputPin, String storedHash){
        if(inputPin == null || storedHash == null){
            throw new IllegalArgumentException("Input PIN and stored has must not be null. ");
        }
        String hashedInput = hashPin(inputPin);
        return hashedInput.equals(storedHash);
    }

    /**
     * Retrieves a user account by name. Throws an exception if not found
     *
     * @param userAccountName The name of the user account to retrieve
     * @return THe user account object
     */
    public UserAccount getUserAccount(String userAccountName){
        if(userAccountName == null){
            throw new IllegalArgumentException("User account name cannot be null.");
        }

        UserAccount userAccount = userAccounts.get(userAccountName);
        if(userAccount == null){
            throw new IllegalArgumentException("Account not found with the name: " + userAccountName);
        }
        return userAccount;
    }

    /**
     * Deletes an account by name
     *
     * @param userAccountName The name of the user account to delete
     */
    public void deleteAccount(String userAccountName){
        if(userAccountName == null){
            throw new IllegalArgumentException("User account name cannot be null.");
        }

        if(!userAccounts.containsKey(userAccountName)){
            throw new IllegalArgumentException("No account found with name: " + userAccountName);
        }

        userAccounts.remove(userAccountName);
    }

    /**
     * Check if an account exists with the given name 
     *
     * @param userAccountName userAccountName The name of the account to check
     * @return true if the account exists, false otherwise
     */
    public boolean accountExists(String userAccountName){
        return userAccounts.containsKey(userAccountName);
    }
}
