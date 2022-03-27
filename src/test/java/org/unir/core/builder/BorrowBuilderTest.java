package org.unir.core.builder;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.unir.core.dto.Borrow;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;

public class BorrowBuilderTest {

    private BorrowBuilder builder;
    String[] args = new String[] {"bookId", "borrowerID", "01/01/2000", "1", "No comments."};

    @BeforeEach
    public void setUp() {
        builder = BorrowBuilder.getInstance();
    }

    @Test
    public void validateBookId () {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> builder.build(new String[] {null, "borrowerID", "01/01/2000", "1", "No comments."})
        );
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> builder.build(new String[] {"", "borrowerID", "01/01/2000", "1", "No comments."})
        );
        builder.build(new String[] {"bookId", "borrowerID", "01/01/2000", "1", "No comments."});
    }

    @Test
    public void validateBorrowerId () {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> builder.build(new String[] {"bookId", null, "01/01/2000", "1", "No comments."})
        );
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> builder.build(new String[] {"bookId", "", "01/01/2000", "1", "No comments."})
        );
        builder.build(new String[] {"bookId", "borrowerID", "01/01/2000", "1", "No comments."});
    }

    @Test
    public void validateBorrowDateId () {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> builder.build(new String[] {"bookId", "borrowerID", null, "1", "No comments."})
        );
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> builder.build(new String[] {"bookId", "borrowerID", "", "1", "No comments."})
        );
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> builder.build(new String[] {"bookId", "borrowerID", "1/1/00", "1", "No comments."})
        );
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> builder.build(new String[] {"bookId", "borrowerID", "1-1-2000", "1", "No comments."})
        );
        builder.build(new String[] {"bookId", "borrowerID", "01/01/2000", "1", "No comments."});
    }

    @Test
    public void validateBorrowDaysId () {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> builder.build(new String[] {"bookId", "borrowerID", "01/01/2000", null, "No comments."})
        );
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> builder.build(new String[] {"bookId", "borrowerID", "01/01/2000", "", "No comments."})
        );
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> builder.build(new String[] {"bookId", "borrowerID", "01/01/2000", "a", "No comments."})
        );
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> builder.build(new String[] {"bookId", "borrowerID", "01/01/2000", "-10", "No comments."})
        );
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> builder.build(new String[] {"bookId", "borrowerID", "01/01/2000", "100", "No comments."})
        );
        Borrow borrow = builder.build(new String[] {"bookId", "borrowerID", "01/01/2000", "1", "No comments."});
        LocalDate startDate = borrow.getBorrowDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = borrow.getBorrowEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Assert.assertEquals(startDate.until(endDate).getDays(), 1);
        borrow = builder.build(new String[] {"bookId", "borrowerID", "01/01/2000", "2", "No comments."});
        startDate = borrow.getBorrowDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        endDate = borrow.getBorrowEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Assert.assertEquals(startDate.until(endDate).getDays(), 2);
        borrow = builder.build(new String[] {"bookId", "borrowerID", "01/01/2000", "4", "No comments."});
        startDate = borrow.getBorrowDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        endDate = borrow.getBorrowEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Assert.assertEquals(startDate.until(endDate).getDays(), 4);
        borrow = builder.build(new String[] {"bookId", "borrowerID", "01/01/2000", "8", "No comments."});
        startDate = borrow.getBorrowDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        endDate = borrow.getBorrowEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Assert.assertEquals(startDate.until(endDate).getDays(), 8);
        borrow = builder.build(new String[] {"bookId", "borrowerID", "01/01/2000", "30", "No comments."});
        startDate = borrow.getBorrowDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        endDate = borrow.getBorrowEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Assert.assertEquals(startDate.until(endDate).getDays(), 30);
    }

    @Test
    public void cloneArrayTest () {
        int[] str1 = builder.getAllowedDays();
        int[] str2 = builder.getAllowedDays();
        Assert.assertEquals(str1.length, str2.length);
        Assert.assertNotEquals(str1.hashCode(), str2.hashCode());
    }
}