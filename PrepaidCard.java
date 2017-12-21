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
public class PrepaidCard extends BankCard{
    
    public PrepaidCard(String cardHolder, long cardNumber, double balance){
        super(cardHolder, cardNumber);
        this.currentBalance = balance;
    }
    
    public PrepaidCard(String cardHolder, long cardNumber){
        super(cardHolder, cardNumber);
        this.currentBalance = 0;
    }
    
    public boolean addTransaction(Transaction t){
        if(this.fraudTest(this) == true){
            return false;
        }
        else if("debit".equals(t.type()) && t.amount() <= this.currentBalance){
            this.currentBalance -= t.amount();
            this.ListOfTransactions.add(t);
            return true;
        }
        else if("debit".equals(t.type()) && t.amount() > this.currentBalance){
            return false;
        }
        else if("credit".equals(t.type())){
            this.currentBalance -= t.amount();
            this.ListOfTransactions.add(t);
            return true;
        }
        else
            return false;
    }
    
    public boolean addFunds(double amount){
        if(this.fraudTest(this) == true){
            return false;
        }
        
        else if(amount > 0){
            this.currentBalance += amount;
            this.ListOfTransactions.add(new Transaction("top-up","User payment",amount));
            return true;
        }
        else
            return false;
    }
    
    public String toString(){
        return "Card # " + number() + "\nBalance: $" + this.currentBalance;
    }
    
    public void printStatement(){
        System.out.println("\n\n---------------------------------------------------------");
        System.out.println("Cardholder: " + this.cardHolder() + "\nCard # " + this.number() + "\nbalance: " + this.balance());
    }
    
}
