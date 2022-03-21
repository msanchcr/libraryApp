package org.unir.core.dto;

import java.util.Date;

public class Borrow {

    private final static String NO_COMMENT = "No comment";

    private final String idBook;
    private final String idReader;
    private final Date borrowDate;
    private final Date borrowEndDate;
    private final String comment;

    public Borrow(String idBook, String idReader, Date borrowDate, Date borrowEndDate, String comment) {
        this.idBook = idBook;
        this.idReader = idReader;
        this.borrowDate = borrowDate;
        this.borrowEndDate = borrowEndDate;
        this.comment = (comment == null || comment.isEmpty())? NO_COMMENT : comment;
    }

    public String getIdBook() {
        return idBook;
    }

    public String getIdReader() {
        return idReader;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public Date getBorrowEndDate() {
        return borrowEndDate;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return "Borrow{" +
                "idBook='" + idBook + '\'' +
                ", idReader='" + idReader + '\'' +
                ", borrowDate=" + borrowDate +
                ", borrowEndDate=" + borrowEndDate +
                ", comment='" + comment + '\'' +
                '}';
    }
}
