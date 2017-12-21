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
public class RewardsCard extends CreditCard{
    //instance variables
    protected int currentRewardPoints;
    
    //Constructors and instance methods
    public RewardsCard(String holder, long number, int expiration, double limit){
        super(holder,number,expiration,limit);
        currentRewardPoints = 0;
    }
    
    public RewardsCard(String holder, long number, int expiration){
        super(holder,number,expiration);
        this.cardCreditLimit = 500;
        currentRewardPoints = 0;
    }
    
    public int rewardPoints(){
        return currentRewardPoints;
    }
    
    public boolean redeemPoints(int points){
        double val = points/100;
        double max = this.currentBalance;
        double pointsMax = this.currentBalance*100;
        if(points <= rewardPoints()){
            if((this.currentBalance - val) >= 0){
                this.currentBalance -= val;
                currentRewardPoints -= points;
                this.ListOfTransactions.add(new Transaction("redemption","CSEBank",val));
                return true;
            }
            else{
                this.currentBalance -= max;
                currentRewardPoints -= pointsMax;
                this.ListOfTransactions.add(new Transaction("redemption","CSEBank",max));
                return true;
            }
        }
        else
            return false;
    }
    
    public String toString(){
        return "Card # " + this.number() + "\nExpiration: " + this.expiration() +"\nBalance: $" + this.balance() + "\nRewardPoints: " + rewardPoints();
    }
    
    public boolean addTransaction(Transaction t){
        if("debit".equals(t.type()) && t.amount() <= availableCredit()){
            this.currentBalance += t.amount();
            this.ListOfTransactions.add(t);
            currentRewardPoints += (int)(100 * t.amount());
            return true;
        }
        if("debit".equals(t.type()) && !(t.amount() <= availableCredit()))
            return false;
        if("credit".equals(t.type())){
            this.currentBalance += t.amount();
            this.ListOfTransactions.add(t);
            return true;
        }
        else
            return false;
    }

    public void printStatement(){
        System.out.println("\n\n---------------------------------------------------------");
        System.out.println("Cardholder: " + this.cardHolder() + "\nCard # " + this.number() + "\nExpiration: " + this.expiration() + "\nbalance: " + this.balance() + "\nRewardPoints: " + rewardPoints());
    }
}

