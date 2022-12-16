package andliz1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Klass som håller koll på kundens konto
 * I denna klass kan man få info om kontot
 * Man kan sätta in pengar, ta ut pengar
 * Beräknar räntan
 * @author Andreas Linder, andliz-1
 */

public abstract class Account implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private static int lastAssignedNumber = 1000;
    private int accountNumber = 0;
    protected double balance;
    protected double interest;

    private String percentStr;
    private String balanceStr;
    private String calculatedInterestStr;

    protected ArrayList<String> transactionList = new ArrayList<>();


    /**
     * Constructor
     * @param accountNumber kundens kontonummer
     * Innan kontot skapas så plussas 1 på för att alla konton ska vara unika.
     */

    public Account ()
    {
        lastAssignedNumber ++;
        this.accountNumber = lastAssignedNumber;
    }

    /**
     * Metod som används i Customer för att jämföra om kontonr är samma 
     * @return true om det är samma, annars false
     */
    public Boolean compareAccountNumber(int customerAccountNumber)
    {
        if (customerAccountNumber == accountNumber)
            return true;
        else
            return false;
    } 

    /**
     * Info om kundens konto
     * @return String om info om kundens konto
     */
    public abstract String toString();

    /**
     * Info om kontot som ska stängas
     * @return info om kontot
     */
    public abstract String closeAccountToString();


    /**
     * Gör om balance till curreny 
     * (kod från inlämningsuppgift 1 från canvas)
     * @return saldo på konto
     */
    public String getBalance(double balance) // double balance
    {
        balanceStr = NumberFormat.getCurrencyInstance().format(balance);
        return balanceStr;
    } 

    /**
     * @return Kontonummer
     */
    public int getAccountNumber()
    {
        return accountNumber;
    }
    
    /**
     * Info om kontots ränta
     * @return interest rate
     */
    public abstract double getInterest();

    /**
     * Info om kontots saldo
     * @return kontot saldo
     */
    public abstract double getDoubleBalance();

    /**
     * @return Kontotyp
     */
    public abstract String getAccountType();


    /**
     * Gör om räntan till rätt format
     * (Kod från inlämningsuppgift 1 på canvas)
     * @return räntan som String
     */
    public String getInterestRate(double interest) //double interest
    {
        NumberFormat percentFormat = NumberFormat.getPercentInstance();
        percentFormat.setMaximumFractionDigits(1); // Anger att vi vill ha max 1 decimal
        percentStr = percentFormat.format(interest/100);
        return percentStr;
    } 
    
    /**
     * Metod för att sätta in pengar på kontot
     * Beloppet som sätts in måste vara större än 0
     * @return True om transaktionen gick igenom, annars false
     */
    public Boolean deposit(double depositAmount)
    {
        if (depositAmount > 0)
        {
            balance += depositAmount;
            Transactions transaction = new Transactions(balance, depositAmount);
            transactionList.add(transaction.toString());
            return true;
        }
        else
        {
            return false;
        }
    }


    /**
     * Metod för att ta ut pengar från kontot
     * @return True om transaktionen gick igenom, annars false
     */
    public abstract Boolean withdraw(double withdrawAmount); 

    /**
     * Metod för att beräkna räntan i kr som används när användare stänger konto
     * @return Räntan i kr som String
     */
    protected String calculateInterest(double interest, double balance)
    {
        double calculatedInterest = (double) interest * balance / 100;
        calculatedInterestStr = NumberFormat.getCurrencyInstance().format(calculatedInterest);
        return calculatedInterestStr;
    }    

    /**
     * Metod för att returnera lista över transaktioner som har gjorts
     * @return ArrayList med transaktioner
     */
    protected ArrayList<String> getTransactions()
    {
        return transactionList;
    }

    /**
     * Funktion för att läsa in objekt, tar även in sista kontonr som har använts
     * Tagen från Canvas
     */
    private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException   
    {
    // Läser in objektet från filen
    in.defaultReadObject();
    // Hantera nästa kontonummer
    lastAssignedNumber = accountNumber;
    
    }
}
