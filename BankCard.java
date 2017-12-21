/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import java.util.ArrayList;
import java.text.DecimalFormat;
/**
 *
 * @author 42kyl
 */
public abstract class BankCard {
    //Instance Variables
    private String cardholderName;
    protected long cardNumber;  //16digit int
    protected double currentBalance; //cards current balance
    protected ArrayList<Transaction> ListOfTransactions = new ArrayList<>();
    protected int index;
    protected boolean fraud;
    //Constructors
    public BankCard(String cardholderName, long cardNumber){
        this.cardholderName = cardholderName;
        this.cardNumber = cardNumber;
        currentBalance = 0;
    }
    
    public boolean fraudTest(BankCard t){
        for(int i = 0; i < t.ListOfTransactions.size()-1;i++){
            if(t.ListOfTransactions.get(i).amount() <= 1.5 && t.ListOfTransactions.get(i).type().equals("debit")){
                if(t.ListOfTransactions.get(i+1).amount() <= 1.5 && t.ListOfTransactions.get(i+1).type().equals("debit")){
                    index = i;
                    return true;
                }
            }
        }
        return false;
    }
    
    public String balance(){
        DecimalFormat df = new DecimalFormat("#.00");
        return "$" + df.format(currentBalance);
    }
    
    public long number(){
        return cardNumber;
    }
    
    public String cardHolder(){
        
        return cardholderName;
    }
    
    public String toString(){
        return "Card # " + number() + "\nBalance: $" + balance();
    }
    
    public abstract boolean addTransaction(Transaction t);
    
    public abstract void printStatement();
}
