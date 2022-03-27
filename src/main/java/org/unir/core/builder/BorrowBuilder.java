package org.unir.core.builder;

import org.unir.core.dto.Borrow;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class BorrowBuilder {

    private final static int[] allowedBorrowDates = {1, 2, 4, 8, 30};
    private final static Logger LOGGER = Logger.getLogger(BorrowBuilder.class.getName());
    private final static String ID_REGEX = "[a-zA-Z0-9]+";
    private final static String DATE_REGEX = "([0-9]{2})/([0-9]{2})/([0-9]{4})";
    private final static String DATE_FORMAT = "dd/MM/yyyy";
    private final static String NUMBER_REGEX = "[0-9]+";

    private static BorrowBuilder instance;

    /**
     * Main method of this builder. It receives the parameters from the view, in a String[], validates them and,
     * if they are all correct, it creates a Borrow and return it.
     * The validations include:
     *  1.- 5 arguments may be received.
     *  2.- 1st argument is the book id. It should match regular expression [a-zA-Z]+.
     *  3.- 2nd argument is the borrower id. It should match regular expression [a-zA-Z]+.
     *  4.- 3rd argument is the date whe the book will be borrowed. It should be written as dd/MM/yyyy.
     *  5.- 4th argument is the number of days the book will be borrowed. See getAllowedDays() for more information
     *  about the number of days a book can be borrowed.
     *  6.- 5th argument is the comment.
     *
     * @param args The parameters in order to create the Borrow.
     * @return The created Borrow.
     * @throws IllegalArgumentException if any validation is not succed, this exception iw thrown.
     */
    public Borrow build(String[] args) throws IllegalArgumentException {
        Borrow borrow = null;
        Date borrowDate = null;
        int borrowDays = 0;
        Date endBorrowDate = null;

        LOGGER.fine("About to validate inputs.");
        LOGGER.finer("About to validate args length.");
        if ( args.length != 5 ) {
            String errorMessage = String.format(
                    "Expected five (5) parameters to build a borrow, but got %s.",
                    args.length
            );
            LOGGER.severe(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        LOGGER.finer("About to validate args[1] --> idReader.");
        if (args[0] == null || args[0].isEmpty() || !args[0].matches(ID_REGEX)) {
            String errorMessage = "Error, expected book id.";
            LOGGER.severe(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        LOGGER.finer("About to validate args[1] --> idReader.");
        if (args[1] == null || args[1].isEmpty() || !args[1].matches(ID_REGEX)) {
            String errorMessage = "Error, expected borrower id.";
            LOGGER.severe(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        LOGGER.finer("About to validate args[2] --> borrowDate.");
        if (args[2] == null || args[2].isEmpty() || !args[2].matches(DATE_REGEX)) {
            String errorMessage = String.format(
                    "Expected borrow date (%s).",
                    DATE_FORMAT
            );
            LOGGER.severe(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        } else {
            try {
                borrowDate = new SimpleDateFormat(DATE_FORMAT).parse(args[2]);
            } catch (ParseException ex) {
                String errorMessage = String.format(
                        "Expected borrow date (%s).",
                        DATE_FORMAT
                );
                throw new IllegalArgumentException(errorMessage);
            }
        }
        LOGGER.finer("About to validate args[3] --> borrowEndDate");
        if (args[3] == null || args[3].isEmpty() || !args[3].matches(NUMBER_REGEX)) {
            String errorMessage = "Expected number of borrow dates.";
            LOGGER.severe(errorMessage);
            throw new IllegalArgumentException();
        } else {
            try {
                borrowDays = Integer.parseInt(args[3]);
                int finalBorrowDays = borrowDays;
                if (!IntStream.of(allowedBorrowDates).anyMatch(e -> e == finalBorrowDays)) {
                    String errorMessage = "Expected number of borrow dates.";
                    LOGGER.severe(errorMessage);
                    throw new IllegalArgumentException();
                }
                Calendar cal = Calendar.getInstance();
                cal.setTime(borrowDate);
                cal.add(Calendar.DATE, borrowDays);
                endBorrowDate = cal.getTime();
            } catch (NumberFormatException ex) {
                LOGGER.severe("Error while parsing int for borrow dates.");
                throw new IllegalArgumentException("Expected number of borrow dates.");
            }
        }
        borrow = new Borrow(
                args[0],
                args[1],
                borrowDate,
                endBorrowDate,
                (args[4] == null)? "" : args[4]
        );
        return borrow;
    }

    /**
     * This class uses singleton, this method gets the instance of the builder.
     *
     * @return the instance of the builder.
     */
    public static BorrowBuilder getInstance() {
        if (instance == null) {
            instance = new BorrowBuilder();
        }
        return instance;
    }

    /**
     * This method retruns an int[] with all the allowed days tah a book can be borrowed.
     *
     * @return the array with the allowed borrow days.
     */
    public int[] getAllowedDays () {
        return allowedBorrowDates.clone();
    }
}
