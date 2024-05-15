package web.base;

import net.datafaker.Faker;
import web.test_data.AppDataDocuments;
import web.utils.FileOperations;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class TestUtils {
    public static final String UPPER_AND_LOWER_LETTERS = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";
    public static final String NUMBERS = "1234567890";
    public static final String NUMBERS_WITHOUT_NULL = "123456789";
    public static final String SPECIAL_SYMBOLS = "!?#$%^*+=-()";
    private final static String HEADER = ".navbar";
    public static int convertStringToInt(String text) {

        return Integer.parseInt(text);
    }

    public static String getSubstring(String text, String separator) {
        int index = text.indexOf(separator);

        return text.substring(0, index);
    }

    public static List<String> getSortedList(List<String> elements) {

        return elements.stream().sorted().collect(Collectors.toList());
    }


    public static String getSaltString(int length) {

        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) { // length of the random string.
            int index = (int) (rnd.nextFloat() * UPPER_AND_LOWER_LETTERS.length());
            salt.append(UPPER_AND_LOWER_LETTERS.charAt(index));
        }
        return salt.toString();
    }

    public static String getSaltNumbers(int length) {

        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) { // length of the random string.
            int index = (int) (rnd.nextFloat() * NUMBERS.length());
            salt.append(NUMBERS.charAt(index));
        }
        return salt.toString();
    }

    public static String getSaltStringAndNumbers(int length) {
        Faker faker = new Faker();
        return faker.regexify("[a-zA-Z0-9]{" + length + "}");
    }

    public static String getSaltNumbersWithoutZero(int length) {

        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) { // length of the random string.
            int index = (int) (rnd.nextFloat() * NUMBERS_WITHOUT_NULL.length());
            salt.append(NUMBERS_WITHOUT_NULL.charAt(index));
        }
        return salt.toString();
    }

    public static String getSaltSpecialSymbols(int length) {

        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SPECIAL_SYMBOLS.length());
            salt.append(SPECIAL_SYMBOLS.charAt(index));
        }
        return salt.toString();
    }


    public static String getLastPartOfUrl(String url) {
        int lastIndex = url.lastIndexOf('/');
        return url.substring(lastIndex + 1);
    }

    public static Object[][] combineTwoDArrays(Object[][] array1, Object[][] array2, int columns) {
        Object[][] combinedTwoDArray = new Object[array1.length + array2.length][columns];

        System.arraycopy(array1, 0, combinedTwoDArray, 0, array1.length);
        System.arraycopy(array2, 0, combinedTwoDArray, array1.length, array2.length);

        return combinedTwoDArray;
    }

    public static String generateRandomFormattedPhoneNumber() {
        Random random = new Random();

        // Generate random numbers for each part of the phone number
        int areaCode = 100 + random.nextInt(900); // Random 3-digit area code
        int firstThreeDigits = 100 + random.nextInt(900); // Random 3-digit exchange code
        int lastFourDigits = 1000 + random.nextInt(9000); // Random 4-digit line number

        return String.format("(%03d) %03d-%04d", areaCode, firstThreeDigits, lastFourDigits);
    }

    public static String cleanPhoneNumber(String phoneNumber) {
        // Use regular expression to remove non-numeric characters
        return phoneNumber.replaceAll("[^0-9]", "");
    }

    public static String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() == 10) {
            // Extract area code, exchange code, and line number
            String areaCode = phoneNumber.substring(0, 3);
            String exchangeCode = phoneNumber.substring(3, 6);
            String lineNumber = phoneNumber.substring(6, 10);

            // Format the parts into the desired format (xxx) xxx-xxxx
            return String.format("(%s) %s-%s", areaCode, exchangeCode, lineNumber);
        } else {
            // Handle invalid input
            return "Invalid phone number format";
        }
    }

    public static String generateTestDocument(String newDocumentName){
        String originalFilePath = AppDataDocuments.RESOURCES_FILE_PATH + AppDataDocuments.DOCUMENT_NAME_FROM_DISK;
        String newFilePath = AppDataDocuments.RESOURCES_FILE_PATH + newDocumentName;

        FileOperations.copyAndRenameFile(originalFilePath, newFilePath,newDocumentName);

        return newFilePath;
    }

    public static String removeDash(String number) {
        return number.replace("-", "");
    }

    public static String addDashAfterTwoDigits(String number) {
        return number.substring(0, 2) + "-" + number.substring(2);
    }
}
