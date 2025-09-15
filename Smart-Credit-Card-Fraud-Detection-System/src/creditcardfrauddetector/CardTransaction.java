/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package creditcardfrauddetector;

import java.time.LocalDateTime;

/**
 *
 * @author aditisharma
 * 
 * This class represents a single transaction in file
 */
public class CardTransaction {

    private String hashCardNumber;
    private LocalDateTime timestamp;
    private double transactionAmount;

    public CardTransaction(String hashCardNumber, LocalDateTime timestamp, double transactionAmount) {
        this.hashCardNumber = hashCardNumber;
        this.timestamp = timestamp;
        this.transactionAmount = transactionAmount;
    }

    public String getHashCardNumber() {
        return hashCardNumber;
    }

    public void setHashCardNumber(String hashCardNumber) {
        this.hashCardNumber = hashCardNumber;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

}
