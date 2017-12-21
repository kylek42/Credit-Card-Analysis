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
import java.util.ArrayList;

public class CardList {
    //instance variables
    private ArrayList<BankCard> listOfBankCards = new ArrayList<>();
    
    //Constructors and instance methods
    public CardList(){
        //BankCard b = null;
        //listOfBankCards.add(b);
    }
    
    public void add(BankCard b){
        listOfBankCards.add(b);
    }
    
    public void add(int index, BankCard b){
        if(index >= 0 && index <= size()){
            listOfBankCards.add(index, b);
        }
        else{
            listOfBankCards.add(b);
        }
    }
    
    public int size(){
        return listOfBankCards.size();
    }
    
    public BankCard get(int index){
        if(index >= 0 && index <= this.listOfBankCards.size()){
            return listOfBankCards.get(index);
        }
        else{
            return null;
        }
    }
    
    public int indexOf(long cardNumber){
        for(int i = 0; i < size();i++){
            if(this.listOfBankCards.get(i).cardNumber == cardNumber){
                return i;
            }
        }
        return -1;
    }
}
