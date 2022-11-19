package com.example.BlazianApp;

public class TransactionModel {
    String transactionDate;
    String transactionLocation;
    String fromCurrency;
    String toCurrency;
    String fees;
    String fromFlag;
    String toFlag;

    public TransactionModel(String transactionDate,
                            String transactionLocation,
                            String fromCurrency,
                            String toCurrency,
                            String fees,
                            String fromFlag, String toFlag) {
        this.transactionDate = transactionDate;
        this.transactionLocation = transactionLocation;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.fees = fees;
        this.fromFlag = fromFlag;
        this.toFlag = toFlag;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public String getTransactionLocation() {
        return transactionLocation;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public String getFees() { return fees; }

    public String getFromFlag() {
        return fromFlag;
    }

    public String getToFlag() {
        return toFlag;
    }
}
