/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

/**
 *
 * @author 42kyl
 */
// Driver class for the final project
import java.util.*;
import java.io.*;

public class TransactionProcessor {

    public static BankCard convertToCard(String data) {
        Scanner stdin = new Scanner(data);
        long cardNumber = stdin.nextLong();
        String type = "";
        if (getCardType(cardNumber) != null) {
            type = getCardType(cardNumber);
            if (type.equals("CreditCard")) {
                CreditCard credit;
                if (stdin.hasNextDouble()) {
                    credit = new CreditCard(stdin.next().replace('_', ' '), cardNumber, stdin.nextInt(), stdin.nextDouble());
                } else {
                    credit = new CreditCard(stdin.next().replace('_', ' '), cardNumber, stdin.nextInt());
                }
                return credit;
            } else if (type.equals("RewardsCard")) {
                RewardsCard rewards;
                if (stdin.hasNextDouble()) {
                    rewards = new RewardsCard(stdin.next().replace('_', ' '), cardNumber, stdin.nextInt(), stdin.nextDouble());
                } else {
                    rewards = new RewardsCard(stdin.next().replace('_', ' '), cardNumber, stdin.nextInt());
                }
                return rewards;
            } else if (type.equals("PrepaidCard")) {
                PrepaidCard prepaid;
                if (stdin.hasNextDouble()) {
                    prepaid = new PrepaidCard(stdin.next().replace('_', ' '), cardNumber, stdin.nextDouble());
                } else {
                    prepaid = new PrepaidCard(stdin.next().replace('_', ' '), cardNumber);
                }
                return prepaid;
            }
        } else {
            stdin.nextLine();
        }
        return null;
    }

    public static CardList loadCardData(String fileName) {
        CardList listCards = new CardList();
        try {
            File data = new File(fileName);
            Scanner stdin = new Scanner(data);
            while (stdin.hasNextLine()) {
                listCards.add(convertToCard(stdin.nextLine()));
            }
            return listCards;
        } catch (IOException i) {
            return null;
        }
    }

    public static void processTransactions(String filename, CardList listCards){
        try{
            int rewardPoints = 0;
            long cardNumber = 0;
            double balance = 0;
            
            File list = new File(filename);
            Scanner stdin = new Scanner(list);
            
            while(stdin.hasNextLine()){
                String[] line = stdin.nextLine().split(" ");
                long cardNum = Long.parseLong(line[0]);
                int index = listCards.indexOf(cardNum);
                String type = line[1];
                if(type.equals("redeem")){
                    rewardPoints = Integer.parseInt(line[2]);
                    RewardsCard rewards = (RewardsCard)listCards.get(index);
                    rewards.redeemPoints(rewardPoints);
                }
                else if(type.equals("top-up")){
                    balance = Double.parseDouble(line[2]);
                    PrepaidCard prepaid = (PrepaidCard)(listCards.get(index));
                    prepaid.addFunds(balance);
                }
                else if(type.equals("advance")){
                    balance = Double.parseDouble(line[2]);
                    CreditCard credit = (CreditCard) (listCards.get(index)); 
                    credit.getCashAdvance(balance);
                }
                else{
                    Transaction trans = new Transaction(line[1],line[3].replaceAll("_", " "),Double.parseDouble(line[2]));
                    BankCard bCard = listCards.get(index);
                    bCard.addTransaction(trans);
                }
            }
        }
        catch(FileNotFoundException f){
            System.out.println("File not found");
            System.exit(0);
        }
    }

    private static String getCardType(long number) {
        // Return a String indicating whether 'number' belongs to a 
        // CreditCard, RewardsCard, or a PrepaidCard (or null if it's none
        // of the three)

        String result;

        int firstTwo = Integer.parseInt(("" + number).substring(0, 2));

        switch (firstTwo) {
            case 84:
            case 85:
                result = "CreditCard";
                break;
            case 86:
            case 87:
                result = "RewardsCard";
                break;
            case 88:
            case 89:
                result = "PrepaidCard";
                break;
            default:
                result = null; // invalid card number
        }

        return result;
    }
    
    public static void main(String[] args){
        //Scanner stdin = new Scanner(System.in);
        //System.out.print("Please enter the card data file name: ");
        //String cardFile = stdin.next();
        //String transactionFile;
        //CardList list = loadCardData(cardFile);
        //if cardlist doesn't load exception will catch and end the program
        //System.out.print("Please enter the transaction data file name: ");
        //transactionFile = stdin.next();
        //processTransactions(transactionFile, list);
        
        
        //for testing
       //String cardFile = "carddata1.txt";
       CardList list = loadCardData("carddata1.txt");
       processTransactions("transactiondata1.txt", list);

       for(int i = 0; i < list.size();i++){
            list.get(i).printStatement();
            System.out.println("\nTransactions For This Billing Period Are:");
            for(int j = 0; j < list.get(i).ListOfTransactions.size();j++){
                System.out.println(list.get(i).ListOfTransactions.get(j).toString());
            }
            if(list.get(i).fraudTest(list.get(i)) == true){
                System.out.println("** Account frozen due to suspected fraud **");
            }
       }
    }
}
