import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

/*
* The primary function of this class is to work with numbers in bankers format
* This will hopefully ensure that the doubles that are entered are operated on
* in a way that is consistent with money.
*/
public class MoneyUtils {
    private static final int SCALE = 2; //Two decimal places
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;


    /*
     * Rounds a BigDecimal to two decimal places using HALF_EVEN which is bankers rounding
     * @param value to be rounded
     * @returns rounded value
     * @throws illegal argument exception if param is null
     */
    public static BigDecimal round (BigDecimal value){
        if (value == null){
            throw new IllegalArgumentException("Value cannot be null.");
        }

        return value.setScale(SCALE, ROUNDING_MODE);
    }

     /*
     * Takes a number and formats it to a dollar value
     * @param value to be formatted as currency
     * @returns entered valued as currency
     * @throws illegal argument exception if param is null
     */
    public static String formatCurrency(BigDecimal value){
        if (value == null){
            throw new IllegalArgumentException("Value cannot be null. ");
        }

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        return currencyFormat.format(value);
    }

     /*
     * Takes a value and checks to make sure it is a dollar value is not negative
     * @param value to be checked
     * @returns boolean indicating if it is non-negative.
     */
    public static boolean isValidAmount(BigDecimal value){
        return value != null && value.compareTo(BigDecimal.ZERO) >= 0;
    }
}
