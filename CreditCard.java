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
public class CreditCard extends BankCard{
    //instance variables
    private int cardExpiration; //3 or 4 digit integer in myy or mmyy form,cannot start with 0
    protected double cardCreditLimit;   //in dollars
    
    //Constructors and instance methods
    public CreditCard(String cardHolder, long cardNumber, int expiration, double limit){
        super(cardHolder, cardNumber);
        cardExpiration = expiration;
        cardCreditLimit = limit;
    }
    
    public CreditCard(String cardHolder, long cardNumber, int expiration){
        super(cardHolder, cardNumber);
        this.cardExpiration = expiration;
        cardCreditLimit = 500;
    }
    
    public double limit(){
        return cardCreditLimit;
    }
    
    public double availableCredit(){
        return limit() - this.currentBalance;
    }
    
    public String expiration(){
        if(cardExpiration > 999){
            return (cardExpiration / 100) + "/" + (cardExpiration % 100);
        }
        else{
            return (cardExpiration / 100) + "/" + (cardExpiration % 100);
        }
    }
    
    public String toString(){
        return "Card # " + this.number() + "\nExpiration: " + expiration() +"\nBalance: $" + this.balance();
    }
    
    public boolean addTransaction(Transaction t){
        if(this.fraudTest(this) == true){
            return false;
        }
        else if("debit".equals(t.type()) && t.amount() <= availableCredit()){
            this.currentBalance += t.amount();
            this.ListOfTransactions.add(t);
            return true;
        }
        else if("debit".equals(t.type()) && !(t.amount() <= availableCredit()))
            return false;
        else if("credit".equals(t.type())){
            this.currentBalance += t.amount();
            this.ListOfTransactions.add(t);
            return true;
        }
        else
            return false;
    }
    
    public boolean getCashAdvance(double requestAmount){
        double fee = requestAmount * 0.05;
                if(this.fraudTest(this) == true){          
            return false;
        }
        else if((requestAmount + fee) < this.cardCreditLimit){
            this.currentBalance += requestAmount;
            this.currentBalance += fee;
            this.ListOfTransactions.add(new Transaction("advance","CSEBank",requestAmount));
            this.ListOfTransactions.add(new Transaction("fee","Cash advance fee",fee));
            return true;
        }
        else{
            return false;
        }
    }
    
    public void printStatement(){
        System.out.println("\n\n---------------------------------------------------------");
        System.out.println("Cardholder: " + this.cardHolder() + "\nCard # " + this.number() + "\nExpiration: " + expiration() + "\nbalance: " + this.balance());
    }
}
