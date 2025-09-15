/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import creditcardfrauddetector.CardTransaction;
import creditcardfrauddetector.FraudDetection;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author aditisharma
 */
public class CardFraudTest {
    
    public CardFraudTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * test for a single transaction with fraud
     */
    @Test
    public void testOneTransactionWithFraud() {

        double threshold = 80;
        CardTransaction cardTransaction = new CardTransaction("10d7ce2f43e35fa57d1bbf8b1e2", FraudDetection.parseStringDateTime("2014-04-29T13:15:54"), 100);
        List<CardTransaction> transactionList = new ArrayList<CardTransaction>();
        transactionList.add(cardTransaction);

        FraudDetection fraudDetection = new FraudDetection();
        Set<String> fraudCardList = fraudDetection.detectFraud(transactionList, threshold );

        assertEquals("Fraud list should contain 1 detected fraud card number", 1, fraudCardList.size());
    }
    
    /**
     * test for a single transaction without fraud
     */
    @Test
    public void testOneTransactionWithoutFraud() {

        double threshold = 100;
        CardTransaction cardTransaction = new CardTransaction("10d7ce2f43e35fa57d1bbf8b1e2", FraudDetection.parseStringDateTime("2014-04-29T13:15:54"), 50);
        List<CardTransaction> transactionList = new ArrayList<CardTransaction>();
        transactionList.add(cardTransaction);

        FraudDetection fraudDetection = new FraudDetection();
        Set<String> fraudCardList = fraudDetection.detectFraud(transactionList, threshold );

        assertEquals("Fraud list should contain no detected fraud card number", 0, fraudCardList.size());
    }
    
    /**
     * test for a number of transactions with 3 fraud cards and threshold amount : 150
     */
    @Test
    public void testFraudCase1() throws DateTimeParseException, NumberFormatException, IOException {

        double threshold = 150;
        FraudDetection fraudDetection = new FraudDetection();
        
		List<CardTransaction> transactionList = null;
		try {
			transactionList = fraudDetection.readTransactionsFromFile("resources/transaction_sample_file");
		} catch (DateTimeParseException e) {
			System.err.println("Incorrect datetime format in the file");
			System.exit(1);
		} catch (NumberFormatException e) {
			System.err.println("Threshold amount is in incorrect format in the sample file");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("File not found, provide a valid path");
			System.exit(1);
                }
                
        Set<String> fraudCardList = null;
        fraudCardList = fraudDetection.detectFraud(transactionList, threshold);
        
        
        assertEquals("Fraud list should contain 3 detected fraud card numbers", 3, fraudCardList.size());
    }
    
    /**
     * test for a number of transactions with 4 fraud cards and threshold amount : 115
     */
    @Test
    public void testFraudCase2() throws DateTimeParseException, NumberFormatException, IOException {

        double threshold = 115;
        FraudDetection fraudDetection = new FraudDetection();
        
		List<CardTransaction> transactionList = null;
		try {
			transactionList = fraudDetection.readTransactionsFromFile("resources/transaction_sample_file");
		} catch (DateTimeParseException e) {
			System.err.println("Incorrect datetime format in the file");
			System.exit(1);
		} catch (NumberFormatException e) {
			System.err.println("Threshold amount is in incorrect format in the sample file");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("File not found, provide a valid path");
			System.exit(1);
                }
                
        Set<String> fraudCardList = null;
        fraudCardList = fraudDetection.detectFraud(transactionList, threshold);
        
        
        assertEquals("Fraud list should contain 4 detected fraud card numbers", 4, fraudCardList.size());
    }
    
    /**
     * test for a number of transactions with an incorrect datetime format entry
     */
    @Test(expected = Exception.class)
    public void testTransactionsWithIncorrectDateTimeFormat() throws DateTimeParseException, IOException {

        double threshold = 115;
        FraudDetection fraudDetection = new FraudDetection();

        List<CardTransaction> transactionList = null;

        transactionList = fraudDetection.readTransactionsFromFile("resources/incorrect_date_sample_file");

        Set<String> fraudCardList = null;
        fraudCardList = fraudDetection.detectFraud(transactionList, threshold);

    }
    
    
    /**
     * test for a number of transactions with an incorrect amount format 
     */
    @Test()
    public void testTransactionsWithIncorrectAmountFormat() throws IOException {

        FraudDetection fraudDetection = new FraudDetection();
        boolean foundException = false;
        try {
            fraudDetection.readTransactionsFromFile("resources/incorrect_amount_sample_file");
        } catch (DateTimeParseException e) {

        } catch (NumberFormatException e) {
            foundException = true;
        }
        assertTrue(foundException);

    }
}
