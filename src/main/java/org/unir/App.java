package org.unir;

import org.unir.view.BorrowView;

public class App 
{
    public static void main( String[] args )
    {
        BorrowView view = BorrowView.getInstance();
        view.setVisible(true);
    }
}
