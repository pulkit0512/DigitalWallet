import controller.DigitalWalletController;
import models.Response;

import java.util.Scanner;

/*
Sample Input
CreateWallet Harry 100
CreateWallet Ron 95.7
CreateWallet Hermione 104
CreateWallet Albus 200
CreateWallet Draco 500
Overview
TransferMoney Albus Draco 30
TransferMoney Hermione Harry 2
TransferMoney Albus Ron 5
Overview
Statement Harry
Statement Albus
Offer2
Overview
 */

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DigitalWalletController server = new DigitalWalletController();

        label:
        while(true) {
            String input = sc.nextLine();
            String[] inputs = input.split(" ");

            Response response;

            switch (inputs[0]) {
                case "CreateWallet":
                    response = server.createAccount(inputs[1], Double.parseDouble(inputs[2]));
                    System.out.println(response.getMessage());
                    break;
                case "TransferMoney":
                    response = server.transferMoney(inputs[1], inputs[2], Double.parseDouble(inputs[3]));
                    System.out.println(response.getMessage());
                    break;
                case "Statement":
                    response = server.getStatement(inputs[1]);
                    System.out.println(response.getMessage());
                    response.getOutput().forEach(System.out::println);
                    break;
                case "Overview":
                    response = server.getOverview();
                    System.out.println(response.getMessage());
                    response.getOutput().forEach(System.out::println);
                    break;
                case "Offer2":
                    response = server.applyOffer2();
                    System.out.println(response.getMessage());
                    break;
                case "FixedDeposit":
                    response = server.fixedDeposit(inputs[1], Double.parseDouble(inputs[2]));
                    System.out.println(response.getMessage());
                    break;
                case "QUIT":
                    break label;
                default:
                    System.out.println("Invalid Command.");
                    break;
            }
        }
    }
}