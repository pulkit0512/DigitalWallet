package validation;

import dataStore.DataBase;
import models.Account;

public class WalletValidations {

    public void validateCreateAccount(String accountHolderName, double amount) throws Exception {
        if(amount<0){
            throw new Exception("ERROR, Amount can't be NEGATIVE");
        }

        if(DataBase.getInstance().getAccount(accountHolderName) != null) {
            throw new Exception("ERROR, Account Already exist");
        }
    }

    public void validateTransferMoney (String accountHolderName1, String accountHolderName2, double amount) throws Exception {
        if(amount<0.0001){
            throw new Exception("ERROR, Transfer Amount can't be less then 0.0001");
        }

        if(accountHolderName1.equals(accountHolderName2)) {
            throw new Exception("ERROR, Both accounts can't be same");
        }

        Account account1 = DataBase.getInstance().getAccount(accountHolderName1);

        if(account1 == null) {
            throw new Exception("ERROR, Account: " + accountHolderName1 + " doesn't exist");
        }else if(account1.getTotalBalance() < amount) {
            throw new Exception("ERROR, Insufficient balance");
        }

        if(DataBase.getInstance().getAccount(accountHolderName2) == null) {
            throw new Exception("ERROR, Account: " + accountHolderName2 + " doesn't exist");
        }

    }

    public void validateGetStatement (String accountHolderName) throws Exception {
        if(DataBase.getInstance().getAccount(accountHolderName) == null) {
            throw new Exception("ERROR, Account: " + accountHolderName + " doesn't exist");
        }
    }

    public void validateFixedDeposit (String accountHolderName, double amount) throws Exception {
        if(amount<0){
            throw new Exception("ERROR, Amount can't be NEGATIVE");
        }

        if(DataBase.getInstance().getAccount(accountHolderName) == null) {
            throw new Exception("ERROR, Account Doesn't exist");
        }

        if(DataBase.getInstance().getAccount(accountHolderName).getTotalBalance() < amount) {
            throw new Exception("ERROR, Insufficient balance for FD");
        }
    }
}
