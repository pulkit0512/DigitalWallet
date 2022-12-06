package models;

import java.util.Map;

public class Account {
    private String accountHolderName;
    private int accountId;
    private double totalBalance;
    private double fdAmount;
    private boolean isFdCreated;
    private int fdTransactions;
    private Map<String, Double> transactionsMap;

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public double getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(double totalBalance) {
        this.totalBalance = totalBalance;
    }

    public double getFdAmount() {
        return fdAmount;
    }

    public void setFdAmount(double fdAmount) {
        this.fdAmount = fdAmount;
    }

    public boolean isFdCreated() {
        return isFdCreated;
    }

    public void setFdCreated(boolean fdCreated) {
        isFdCreated = fdCreated;
    }

    public int getFdTransactions() {
        return fdTransactions;
    }

    public void setFdTransactions(int transactions) {
        this.fdTransactions = transactions;
    }

    public Map<String, Double> getTransactionsMap() {
        return transactionsMap;
    }

    public void setTransactionsMap(Map<String, Double> transactionsMap) {
        this.transactionsMap = transactionsMap;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}
