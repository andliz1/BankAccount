package andliz1;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Klass som håller koll på kundens transaktioner
 * När en transaktion görs så skapas ett objekt av denna klass för att ha koll på saldo, tid, datum, summa
 * @author Andreas Linder, andliz-1
 */

public class Transactions implements Serializable {

    private static final long serialVersionUID = 1L;

    private double balance;
    private double amount;
    private String strDate;
    private String numberStr;
    

    /**
     * Constructor, när ett objekt skapas så sparas saldot, summan på transaktionen samt datum och tid
     */ 
    public Transactions(double balance, double amount)
    {
        this.balance = balance;
        this.amount = amount;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.strDate = sdf.format(new Date());        
    }

    /**
     * Denna kallas på från andra klasser när info om transaktionerna önskas
     * @return String med info om transaktioner
     */ 
    public String toString()
    {
        return strDate + " " + getNumberFormat(amount) + " Saldo: " + getNumberFormat(balance);
    }
    
    /**
     * Metod för att göra om nummer format till ett snyggare utskrivningssätt.
     * @return String med rätt utrskrivningsformat
     */ 
    private String getNumberFormat (double number)
    {
        numberStr = NumberFormat.getCurrencyInstance().format(number);
        return numberStr;
    } 
    
}
