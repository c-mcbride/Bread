package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

// Parsing takes String inputs and changes it into a programmatic LocalDate object.
// Format takes the LocalDate Object and changes it into a String 
public class DateUtils {
    //Define the default date format
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM-dd-yyyy");

    /**
     * Parse a date string into a LocalDate object using the default format.
     * Converts: String "12-31-2024" -> LocalDate Object
     * Why do this? When you want to work with date programmatically
     * @param dateStr the date string to parse (format: MM-dd-yyyy)
     * @return a LocalDate object
     * @throws DateTimeParseException
     */
    public static LocalDate parseDate(String dateStr) throws DateTimeParseException{
        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }

    /**
     * Formats a LocalDateObject int a string using the default format.
     * Converst LocalDateObject -> String
     * Used for when you want to display a date to the user or save it as text
     * @param date the LocalDate object to format
     * @return a formatted date string (format: MM-dd-yyyy)
     */
    public static String formatDate(LocalDate date){
        return date.format(DATE_FORMATTER);
    }

    /**
     * Validates if a string is a valid date in the expected format
     * @param dateStr the date string to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidDate(String dateStr){
        try{
            parseDate(dateStr);
            return true;
        }
        catch(DateTimeParseException e){
            return false;
        }
    }

    /**
     * Gets the current date as a Local Date
     * @return a formatted date string (format: MM-dd-yyyy)
     */
    public static LocalDate getCurrentDate(){
        return LocalDate.now();
    }

    /**
     * Returns a properly formatted date string for today's date.
     * @return a formatted date string (format: MM-dd-yyyy)
     */
    public static String getFormattedCurrentDate(){
        return formatDate(getCurrentDate());
    }

    /**
     * Return the default date(current date if input is invalid or null)
     * @param dateStr the date string to parse
     * @return a LocalDate object (either parsed data or today's date)
     */
    public static LocalDate getDateOrDefault(String dateStr){
        if(isValidDate(dateStr)){
            return parseDate(dateStr);
        }
        return getCurrentDate(); //Fallback to today's date
    }

    /**
     * Ensures a properly formatted date string is returned.
     * If the input is invalid, it defaults to today's date and formats it.
     * @param dateStr the date string to validate and format
     * @return a properly formatted date string (format: MM-d-yyyy)
     */
    public static String getFormattedDateOrDefault(String dateStr){
        return formatDate(getDateOrDefault(dateStr));
    }
}
