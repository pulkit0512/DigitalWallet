package dataStore;

import models.Account;

import java.util.*;
import java.util.stream.Collectors;

public class DataBase {

    Comparator<Account> transactionsComparator = Comparator.comparing(account -> account.getTransactionsMap().size());
    Comparator<Account> balanceComparator = Comparator.comparingDouble(Account::getTotalBalance);
    Comparator<Account> idComparator = Comparator.comparingInt(Account::getAccountId).reversed();

    static class Sort implements Comparator<Account> {

        @Override
        public int compare(Account o1, Account o2) {
            int compareBalance = Double.compare(o1.getTotalBalance(), o2.getTotalBalance());

            if(o1.getTransactionsMap().size() != o2.getTransactionsMap().size()) {
                return o1.getTransactionsMap().size() - o2.getTransactionsMap().size();
            }else if(compareBalance != 0) {
                return compareBalance;
            }else{
                return o1.getAccountId() - o2.getAccountId();
            }
        }
    }


    private static DataBase dataBase = null;

    private final Map<String, Account> db; // Name vs Account Database, considering names will be distinct.

    private DataBase() {
        db = new LinkedHashMap<>();
    }

    // Static method to create instance of Singleton class
    public static DataBase getInstance() {
        if (dataBase == null)
            dataBase = new DataBase();

        return dataBase;
    }

    public void insertDatabase(String accountHolderName, Account account) {
        account.setAccountId(db.size() + 1); // Set account ID

        db.put(accountHolderName, account);
    }

    public void updateDatabase(String accountHolderName, Account account) {
        db.put(accountHolderName, account);
    }

    public Account getAccount(String accountHolderName) {
        // returns User Account if user exist in DB Else returns null
        return db.getOrDefault(accountHolderName, null);
    }

    public List<String> getOverviewDetails() {
        List<String> overviewDetails = new ArrayList<>();
        db.forEach((k, v) -> overviewDetails.add(k + ", Total Balance: " + v.getTotalBalance() +
                ", FD Amount: " + v.getFdAmount() + ", Remaining Transactions: " + (5- v.getFdTransactions())));

        return overviewDetails;
    }

    public List<Account> getTop3AccountsUsingStream() {
        // Alternate Approach using streams for sorting
        return db.values().stream()
                .sorted(transactionsComparator)
                .sorted(balanceComparator)
                .sorted(idComparator)
                .limit(3)
                .collect(Collectors.toList());


    }

    public List<Account> getTop3Accounts() {
        // db.values().forEach(account -> System.out.println(account.getAccountHolderName() + " " + account.getTotalBalance() + " " + account.getTransactionsMap()));

        PriorityQueue<Account> accountPriorityQueue = new PriorityQueue<>(new Sort());

        for(Account account : db.values()) {
            accountPriorityQueue.add(account);

            if(accountPriorityQueue.size()>3) {
                accountPriorityQueue.poll();
            }
        }

        List<Account> accountList = new ArrayList<>();
        while(!accountPriorityQueue.isEmpty()) {
            accountList.add(accountPriorityQueue.poll());
        }

        return accountList;
    }
}
