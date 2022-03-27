package org.unir.view;

import org.unir.core.builder.BorrowBuilder;
import org.unir.core.dto.Borrow;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.logging.Logger;

@SuppressWarnings({"FieldCanBeLocal", "SpellCheckingInspection"})
public class BorrowView extends JFrame {

    private static BorrowView instance;
    private final static Logger LOGGER = Logger.getLogger(BorrowView.class.getName());

    private final JComboBox<Integer> daysToBorrow;

    private final JCheckBox requestConfirmationCheck;

    private final JPanel actionPanel;
    private final JPanel bookIdPanel;
    private final JPanel borrowDatePanel;
    private final JPanel borrowDaysPanel;
    private final JPanel borrowerIdPanel;
    private final JPanel formPanel;
    private final JPanel commentPanel;
    private final JPanel mainPanel;
    private final JPanel requestConfirmationPanel;

    private final JButton submitButton;
    private final JButton clearButton;

    private final JLabel bookIdLabel;
    private final JLabel borrowDateLabel;
    private final JLabel borrowDaysLabel;
    private final JLabel borrowerIdLabel;
    private final JLabel commentLabel;

    private final String BOOKID = "Book ID:";
    private final String BORROWDATE = "Borrow date:";
    private final String BORROWDAYS = "Borrow days:";
    private final String BORROWERID = "Borrower ID:";
    private final String CLEAR = "Clear";
    private final String COMMENT = "Comment:";
    private final String REQUEST_CONFIRMATION = "Request confirmation:";
    private final String SUBMIT = "Submit";

    private final JTextArea commentArea;

    private final JTextField bookIdField;
    private final JTextField borrowDateField;
    private final JTextField borrowerIdField;

    private BorrowView () {
        //Main panel
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        mainPanel.setLayout(new BorderLayout(0, 0));
        setContentPane(mainPanel);

        //Form
        formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.PAGE_AXIS));

        //BookID
        bookIdPanel = new JPanel();
        formPanel.add(bookIdPanel);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        bookIdLabel= new JLabel(BOOKID);
        bookIdPanel.add(bookIdLabel);

        bookIdField = new JTextField();
        bookIdField.setText("");
        bookIdField.setColumns(10);
        bookIdPanel.add(bookIdField);

        borrowerIdPanel = new JPanel();
        formPanel.add(borrowerIdPanel);

        borrowerIdLabel = new JLabel(BORROWERID);
        borrowerIdPanel.add(borrowerIdLabel);

        borrowerIdField = new JTextField();
        borrowerIdField.setText("");
        borrowerIdField.setColumns(10);
        borrowerIdPanel.add(borrowerIdField);

        //BorrowDate
        borrowDatePanel = new JPanel();
        formPanel.add(borrowDatePanel);

        borrowDateLabel = new JLabel(BORROWDATE);
        borrowDatePanel.add(borrowDateLabel);

        borrowDateField = new JTextField();
        borrowDateField.setText("");
        borrowDatePanel.add(borrowDateField);
        borrowDateField.setColumns(10);

        //Borrowdays
        borrowDaysPanel = new JPanel();
        formPanel.add(borrowDaysPanel);

        borrowDaysLabel = new JLabel(BORROWDAYS);
        borrowDaysPanel.add(borrowDaysLabel);

        BorrowBuilder builder = BorrowBuilder.getInstance();
        daysToBorrow = new JComboBox<>();
        for (int i: builder.getAllowedDays()) {
            daysToBorrow.addItem(i);
        }
        borrowDaysPanel.add(daysToBorrow);

        //request confirmation
        requestConfirmationPanel = new JPanel();
        formPanel.add(requestConfirmationPanel);

        requestConfirmationCheck = new JCheckBox(REQUEST_CONFIRMATION);
        requestConfirmationPanel.add(requestConfirmationCheck);

        //Comment
        commentPanel = new JPanel();
        commentPanel.setLayout(new BoxLayout(commentPanel, BoxLayout.PAGE_AXIS));
        formPanel.add(commentPanel);

        commentLabel = new JLabel(COMMENT);
        commentPanel.add(commentLabel);

        commentArea = new JTextArea();
        commentArea.setBounds(10, 10, 100, 100);
        commentPanel.add(commentArea);

        //Action panel
        actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout());
        mainPanel.add(actionPanel, BorderLayout.SOUTH);

        submitButton = new JButton();
        submitButton.setText(SUBMIT);
        actionPanel.add(submitButton);
        this.submitButton.addActionListener( e -> submit());

        clearButton = new JButton();
        clearButton.setText(CLEAR);
        actionPanel.add(clearButton);
        this.clearButton.addActionListener( e -> clear());
    }

    public static BorrowView getInstance() {
        if (instance == null) {
            instance = new BorrowView();
        }
        return instance;
    }

    private void clear () {
        this.bookIdField.setText("");
        this.borrowerIdField.setText("");
        this.borrowDateField.setText("");
        //borrowDays
        this.commentArea.setText("");
    }

    private void submit() {
        LOGGER.fine("About to submit form.");
        BorrowBuilder builder = BorrowBuilder.getInstance();
        try {
            int input = 0;
            if (requestConfirmationCheck.isSelected()) {
                LOGGER.fine("About to borrow book with confirmation.");
                input = JOptionPane.showConfirmDialog(
                        null,
                        "Do you want to borrow this book?",
                        "Borrow confirmation",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );
            }
            if (input == 0) {
                LOGGER.fine("About to borrow book confirmation.");
                Borrow borrow = builder.build(new String[] {
                        this.bookIdField.getText(),
                        this.borrowerIdField.getText(),
                        this.borrowDateField.getText(),
                        "1",
                        this.commentArea.getText()
                });
                LOGGER.info(
                        String.format(
                                "A new book has been borrowed --> %s",
                                borrow.toString()
                        )
                );
                JOptionPane.showMessageDialog(
                        null,
                        borrow.toString(),
                        "A new book has been borrowed.",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        } catch (IllegalArgumentException ex) {
            LOGGER.severe(
                    String.format(
                            "An error occurred while building new borrow --> %s",
                            ex.getMessage()
                    ));
        }
    }
}
