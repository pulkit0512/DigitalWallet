package controller;

import models.Response;
import service.WalletService;
import service.impl.WalletServiceImpl;
import validation.WalletValidations;

public class DigitalWalletController {
    public static final boolean FALSE = false;
    WalletValidations walletValidations = new WalletValidations();
    WalletService walletService = new WalletServiceImpl();

    public Response createAccount(String accountHolderName, double account) {
        try {
            walletValidations.validateCreateAccount(accountHolderName, account);

            return walletService.createAccount(accountHolderName, account);
        } catch (Exception e) {
            Response response = new Response();
            response.setSuccess(FALSE);
            response.setMessage(e.getMessage());

            return response;
        }
    }

    public Response transferMoney(String accountHolderName1, String accountHolderName2, double account) {
        try {
            walletValidations.validateTransferMoney(accountHolderName1, accountHolderName2, account);

            return walletService.transferMoney(accountHolderName1, accountHolderName2, account);
        } catch (Exception e) {
            Response response = new Response();
            response.setSuccess(FALSE);
            response.setMessage(e.getMessage());

            return response;
        }
    }

    public Response getStatement(String accountHolderName) {
        try {
            walletValidations.validateGetStatement(accountHolderName);

            return walletService.getStatement(accountHolderName);
        } catch (Exception e) {
            Response response = new Response();
            response.setSuccess(FALSE);
            response.setMessage(e.getMessage());

            return response;
        }
    }

    public Response getOverview() {
        return walletService.getOverview();
    }

    public Response applyOffer2() {
        return walletService.applyOffer2();
    }

    public Response fixedDeposit(String accountHolderName, double amount) {
        try {
            walletValidations.validateFixedDeposit(accountHolderName, amount);

            return walletService.fixedDeposit(accountHolderName, amount);
        } catch (Exception e) {
            Response response = new Response();
            response.setSuccess(FALSE);
            response.setMessage(e.getMessage());

            return response;
        }
    }
}
