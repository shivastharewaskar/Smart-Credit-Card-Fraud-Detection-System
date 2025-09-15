/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package creditcardfrauddetector;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

/**
 *
 * @author aditisharma
 * 
 * This class implements credit card fraud detection
 */
public class FraudDetection {
    
    // datetime format for card transactions
    public final static DateTimeFormatter TRANSACTION_DATETIME_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    
    // map that holds card and sum of amounts in any 24 hour sliding window
    protected Map<String, Double> cardMap = new HashMap<String, Double>();
    
    // set holds detected fraud credit card hashed numbers
    protected Set<String> fraudCardNumbers = new HashSet<String>();
    
    /**
     * This methods will read & parse sample file, 
     * and returns list of card transactions in file
     */
    public List<CardTransaction> readTransactionsFromFile(String inputPath) throws IOException, DateTimeParseException, NumberFormatException 
    {
        List<String> lines = Collections.emptyList();
        try {
            lines = Files.readAllLines(Paths.get(inputPath), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("error while reading lines from file "+ e);
        }
        
        List<CardTransaction> transactionList = new ArrayList<CardTransaction>();
        
        lines.forEach(line -> {
            CardTransaction item = parseTransactionSequence(line);
            transactionList.add(item);
        });

       return transactionList; 
    }
    
    /**
     * This method will parse a single transaction in the file
     * and returns an object of CardTransaction
     */ 
    public CardTransaction parseTransactionSequence(String line) {
		String[] items = line.replaceAll("\\s+", "").split(",");
		String cardNumber = items[0];
		LocalDateTime transactionDate = parseStringDateTime(items[1]);
		double amount = Double.parseDouble(items[2]);

		return new CardTransaction(cardNumber,transactionDate, amount);
	}
    
    // This method will parse string datetime into LocaldateTime 
    public static LocalDateTime parseStringDateTime(String dateString) {

        final LocalDateTime date = LocalDateTime.parse(dateString, TRANSACTION_DATETIME_FORMAT);
        return date;
	}
    
    // This method will detect fraud for a card in 24 hour sliding window
    public Set<String> detectFraud(List<CardTransaction> transactionList, double thresholdAmount)
    {
        int i = 0;
        int l = transactionList.size();
        boolean flag = false;
        for( CardTransaction cardTransaction : transactionList)
        {
            LocalDateTime start = cardTransaction.getTimestamp();
            LocalDateTime end = cardTransaction.getTimestamp().plusHours(24);
             
            flag = checkFraudInSlidingWindow(transactionList.subList(i, l), thresholdAmount, start, end, cardTransaction);
            
            if(flag)
            {
                fraudCardNumbers.add(cardTransaction.getHashCardNumber());
            }
        i++;
        }
        return fraudCardNumbers;
    }
    
    
    /**
     * This method will accept sublist of transaction i.e. excluding previous processed credit card transaction(s) to current transaction in the file,
     * threshold amount, start and end datetime of 24 hour sliding window for current transaction,
     * current transaction
     * and returns boolean value of fraud detection
     */ 
    public boolean checkFraudInSlidingWindow(List<CardTransaction> transactionSubList, double thresholdAmount, LocalDateTime start, LocalDateTime end, CardTransaction card)
    {
        cardMap.clear();
        
        for( CardTransaction cardTransaction : transactionSubList)
        {
            if(cardTransaction.getHashCardNumber().equals(card.getHashCardNumber()) && (cardTransaction.getTimestamp().isAfter(start) || cardTransaction.getTimestamp().equals(start)) && (cardTransaction.getTimestamp().isBefore(end) || cardTransaction.getTimestamp().equals(end)))
            {
                String cardNumber = cardTransaction.getHashCardNumber();
                double transactionAmount = cardTransaction.getTransactionAmount();
                addCardAndAmountToMap(cardNumber, transactionAmount);
            }
            
        }
        
        if (cardMap.get(card.getHashCardNumber()) > thresholdAmount) {
            return true;
        } else {
            return false;
        }
        
    }
    
    public void addCardAndAmountToMap(String cardNumber, double transactionAmount) {
        if (isCardInMap(cardNumber)) {
            double oldTotalAmount = cardMap.get(cardNumber);
            double newTotalAmount = transactionAmount + oldTotalAmount;
            cardMap.put(cardNumber, newTotalAmount);
        } else {
            cardMap.put(cardNumber, transactionAmount);
        }

    }
    
    private boolean isCardInMap(String cardNumber) {
        if (cardMap.get(cardNumber) == null) {
            return false;
        }
        return true;
    }
    
}
