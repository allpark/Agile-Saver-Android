package com.example.agilesavev2.fragments.TransactionActivity;

import com.example.agilesavev2.models.transactions.Transaction;

import java.util.List;

public interface TransactionActivityActivityContract {
    interface spendingActivityView{
        void displayTransactions(List<Transaction> transactionList);
        void hideTransaction();
    }

    interface spendingActivityPresenter{
        void getTransactions();
    }
}
