import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

//This class will be used to cleanly convert numeric representations in a format that is used for money
public class MoneyUtils {
    private static final int SCALE = 2; //Two decimal places
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;

    //Rounds a BigDecimal to two decimal places using HALF_EVEN which is bankers rounding
    public static BigDecimal round (BigDecimal value){
        if (value == null){
            throw new IllegalArgumentException("Value cannot be null.");
        }

        return value.setScale(SCALE, ROUNDING_MODE);
    }

    //Formats BigDecimal as currency
    public static String formatCurrency(BigDecimal value){
        if (value == null){
            throw new IllegalArgumentException("Value cannot be null. ");
        }

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        return currencyFormat.format(value);
    }

    //Validates a monetary value(e.g, non-negative)
    public static boolean isValidAmount(BigDecimal value){
        return value != null && value.compareTo(BigDecimal.ZERO) >= 0;
    }
}
