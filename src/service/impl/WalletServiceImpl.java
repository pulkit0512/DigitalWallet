package service.impl;

import dataStore.DataBase;
import models.Account;
import models.Response;
import service.WalletService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class WalletServiceImpl implements WalletService {
    private static final double OFFER_1_REWARD = 10.0;
    private static final double FD_REWARD = 10.0;
    private static final boolean TRUE = true;

    @Override
    public Response createAccount(String accountHolderName, double amount) {
        Account account = new Account();
        account.setAccountHolderName(accountHolderName);
        account.setTotalBalance(amount);
        account.setTransactionsMap(new LinkedHashMap<>());
        account.getTransactionsMap().put("Account Created", amount);

        DataBase.getInstance().insertDatabase(accountHolderName, account);

        Response response = new Response();
        response.setSuccess(TRUE);
        response.setMessage("Account created Successfully");

        return response;
    }

    @Override
    public Response transferMoney(String accountHolder1, String accountHolder2, double amount) {
        Account account1 = DataBase.getInstance().getAccount(accountHolder1);
        Account account2 = DataBase.getInstance().getAccount(accountHolder2);

        account1.setTotalBalance(account1.getTotalBalance() - amount);
        account1.getTransactionsMap().put(accountHolder2 + " debit", amount);

        account2.setTotalBalance(account2.getTotalBalance() + amount);
        account2.getTransactionsMap().put(accountHolder1 + " credit", amount);

        String message = "Transfer Money Successful!!";

        if(account1.isFdCreated()) {
            isFdInterestEligible(account1);
        }

        if(account2.isFdCreated()) {
            isFdInterestEligible(account2);
        }

        if(Double.compare(account1.getTotalBalance(), account2.getTotalBalance()) == 0) {
            applyOffer1(account1, account2);
            message += " Offer 1 also applied, both accounts received 10.0 FkRupee";
        }

        DataBase.getInstance().updateDatabase(accountHolder1, account1);
        DataBase.getInstance().updateDatabase(accountHolder2, account2);

        Response response = new Response();
        response.setSuccess(TRUE);
        response.setMessage(message);
        return response;
    }

    private void isFdInterestEligible(Account account) {
        if(Double.compare(account.getTotalBalance(), account.getFdAmount())<0) {
            account.setFdTransactions(0);
            account.setFdCreated(false);
            account.setFdAmount(0);
        }else{
            account.setFdTransactions(account.getFdTransactions() + 1);
            if (account.getFdTransactions()==5) {
                account.setTotalBalance(account.getTotalBalance() + FD_REWARD);
                account.getTransactionsMap().put("FD Interest credit", FD_REWARD);
                account.setFdTransactions(0);
            }
        }
    }

    private void applyOffer1(Account account1, Account account2) {
        account1.setTotalBalance(account1.getTotalBalance() + OFFER_1_REWARD);
        account1.getTransactionsMap().put("offer1 credit", OFFER_1_REWARD);

        account2.setTotalBalance(account2.getTotalBalance() + OFFER_1_REWARD);
        account2.getTransactionsMap().put("offer1 credit", OFFER_1_REWARD);
    }

    @Override
    public Response getStatement(String accountHolderName) {
        Account account = DataBase.getInstance().getAccount(accountHolderName);
        List<String> statementDetails = new ArrayList<>();
        account.getTransactionsMap().forEach((k, v) -> statementDetails.add(k +" "+ v));
        statementDetails.add("FD Amount: " + account.getFdAmount() + " , Remaining Transactions: " + (5 - account.getFdTransactions()));

        Response response = new Response();
        response.setSuccess(TRUE);
        response.setMessage("Statement fetched successfully!!");
        response.setOutput(statementDetails);

        return response;
    }

    @Override
    public Response getOverview() {
        Response response = new Response();
        response.setSuccess(TRUE);
        response.setMessage("Overview fetched successfully!!");
        response.setOutput(DataBase.getInstance().getOverviewDetails());

        return response;
    }

    @Override
    public Response applyOffer2() {
        List<Account> accountList = DataBase.getInstance().getTop3Accounts();

        accountList.get(0).setTotalBalance(accountList.get(0).getTotalBalance() + 2.0);
        accountList.get(0).getTransactionsMap().put("offer2 credit", 2.0);

        accountList.get(1).setTotalBalance(accountList.get(1).getTotalBalance() + 5.0);
        accountList.get(1).getTransactionsMap().put("offer2 credit", 5.0);

        accountList.get(2).setTotalBalance(accountList.get(2).getTotalBalance() + 10.0);
        accountList.get(2).getTransactionsMap().put("offer2 credit", 10.0);

        accountList.forEach(account -> DataBase.getInstance().updateDatabase(account.getAccountHolderName(), account));

        Response response = new Response();
        response.setSuccess(TRUE);
        response.setMessage("Offer2 applied successfully!!");

        return response;
    }

    @Override
    public Response fixedDeposit(String accountHolderName, double amount) {
        Account account = DataBase.getInstance().getAccount(accountHolderName);

        account.setFdAmount(amount);
        account.setFdCreated(true);
        account.setFdTransactions(0);

        DataBase.getInstance().updateDatabase(accountHolderName, account);

        Response response = new Response();
        response.setSuccess(TRUE);
        response.setMessage("Fixed Deposit successful!!");

        return response;
    }
}
