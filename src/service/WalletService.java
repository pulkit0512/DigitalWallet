package service;

import models.Response;

public interface WalletService {

    Response createAccount(String accountHolderName, double amount);

    Response transferMoney(String account1, String account2, double amount);

    Response getStatement(String accountHolderName);

    Response getOverview();

    Response applyOffer2();

    Response fixedDeposit(String accountHolderName, double amount);
}
