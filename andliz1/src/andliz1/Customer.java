package andliz1;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Klass som hanterar kunden och som håller koll på kundens konto
 * @param accountList Array som sparar objekt som skapas. 
 * @author Andreas Linder, andliz-1
 */

public class Customer implements Serializable {

    private ArrayList<Account> accountList = new ArrayList<Account>(0);

    private static final long serialVersionUID = 1L;


    private String name;
    private String surname;
    private String pNo;

    /**
     * Constructor
     * @param name Förnamn
     * @param surname Efternamn
     * @param pNo personnr
     * Varje gång en kund skapas så måste förnamn, efternamn och personnr ges som info
     */
    public Customer(String name, String surname, String pNo)
    {
        this.name = name;
        this.surname = surname;
        this.pNo = pNo; 
    }

    /**
     * Metod för att söka igenom arrayen accountList för att hitta ett specifikt konto
     * Loopar igenom arrayList och om kontonr stämmer överrens med det vi letar efter så skapar vi ett referens objekt till objektet
     * Vi skapar alltså inget nytt objekt utan bara en ny referens till det objekt som redan är skapat
     * @param existingAccount Referensobjekt till objektet vi söker efter med ett visst kontonr
     * @return Referensobjektet
     */ 
    public Account searchExistingAccount (int accountNumber)
    {
        for (Account account: accountList)
        {
            if (account.compareAccountNumber(accountNumber))
            {
                return account;
            }
        }
        return null;
    }

    /**
     * Metod för att söka igenom Arrayen accountList för att se om sökt kontonr finns i arrayen
     * @param sameAccountNumer int som jämförs med accountNumber
     * @return True om det finns, annars false
     */ 
    private Boolean searchAccountNumber (int accountNumber)
    {
        Boolean alreadyExist = false;
        int sameAccountNumber;

        for (Account account: accountList)
        {
            sameAccountNumber = account.getAccountNumber();
            if (sameAccountNumber == accountNumber)
            {
                alreadyExist = true;
                break;
            }
        }
        return alreadyExist;
    }

    /**
     * Metod för att skapa nytt sparkonto. 
     * Ett nytt objekt skapas och läggs in i Arrayen accountList
     * @return Kontonummer på kontot som har skapats. 
     */ 
     public int createSavingsAccount()
    {
        SavingsAccount account = new SavingsAccount();
        accountList.add(account);
        return account.getAccountNumber();
    }

    /**
     * Metod som kallas på från BankLogic för att jämföra personnr
     * @return true om personnr är samma, annars false
     */ 
    public Boolean comparePNo(String pNo)
    {
        if (pNo.equals(getpNo()))
            return true;
        else
            return false;
    }
    
    /**
     * Metod för att skapa nytt kreditkonto. 
     * Ett nytt objekt skapas och läggs in i Arrayen accountList
     * @return Kontonummer på kontot som har skapats. 
     */ 
    public int createCreditAccount ()
    {
        CreditAccount account = new CreditAccount();
        accountList.add(account);
        return account.getAccountNumber();
    }
    
    
    /**
     * Metod för att få info om alla konton som finns hos en kund 
     * Loopar igenom arrayen och sparar info som en String och lägger sedan in det i en ny Array.
     * @param AllAccountInformation Array som innehåller info om konton hos kund
     * @return Array med info om alla konton hos kunden. 
     */ 
    public ArrayList<String> getAllAccounts()
    {
        ArrayList<String> AllAccountInformation = new ArrayList<String>();
        String accountInformation;

        for (Account account: accountList)
        {
            accountInformation = account.toString(); // toString () så vi får rätt utskriftsformat
            AllAccountInformation.add(accountInformation);
        }
        return AllAccountInformation;
    }

    
    /**
     * Metod för att få info om alla konton som finns hos en kund 
     * Loopar igenom arrayen och sparar info som en 2D array 
     * @param arrInfoc Array som innehåller info om konton hos kund
     * @return Array med info om alla konton hos kunden. 
     */ 
    public String [][] getAccountArray()
    {
        String [][] arrInfo = new String[accountList.size()][4];

        for (int i = 0; i < accountList.size(); i++) 
        {
            arrInfo[i][0] = Integer.toString(accountList.get(i).getAccountNumber());
            arrInfo[i][1] = accountList.get(i).getBalance(accountList.get(i).getDoubleBalance());
            arrInfo[i][2] = accountList.get(i).getAccountType();
            arrInfo[i][3] = accountList.get(i).getInterestRate(accountList.get(i).getInterest());
        }
        return arrInfo;
    }

    /**
     * Metod för att få info om alla konton som avslutas hos en kund
     * Loopar igenom arrayen och sparar info som en String och lägger sedan in det i en ny Array.
     * @param allClosingAccountInformation Arraysom innehåller info om konton hos kund som avslutas
     * @return Array med info om alla avslutande konton hos kunden. 
     */ 
    public ArrayList<String> getAllClosingAccounts()
    {
        ArrayList<String> allClosingAccountInformation = new ArrayList<String>();
        String closingAccountInformation;

        for (Account account: accountList)
        {
            closingAccountInformation = account.closeAccountToString(); // toString () så vi får rätt utskriftsformat
            allClosingAccountInformation.add(closingAccountInformation);
        }
        return allClosingAccountInformation;
    }

    /**
     * Metod för att få info om kunds konto
     * Loopar igenom arrayen tar ut info om kontot som söks
     * @return info om kontot. 
     */ 
     public String getAccountInformation(int accountNumber)
    {
        String accountInformation = "";
        int accountNumberInformation;

        if (searchAccountNumber(accountNumber) == false)
        {
            accountInformation = null;
        }
        else 
        {
            for (Account account: accountList)
            {
                accountNumberInformation = account.getAccountNumber();
    
                if (accountNumberInformation == accountNumber)
                {
                    accountInformation = account.toString();
                }
            }
        }
        return accountInformation;
    }

    /**
     * Metod för att få info om kunds konto som avslutas
     * Loopar igenom arrayen tar ut info om kontot som söks
     * Loppar med for (int i = 0; i < accountList.size(); i++) då jag inte kom på något bra sätt att ta bort från listan annars.
     * @return info om kontot. 
     */ 
    public String getClosingAccountInformation(int accountNumber)
    {
        String accountInformation = "";
        int accountNumberInformation =0;

        if (searchAccountNumber(accountNumber) == false)
        {
            accountInformation = null;
        }
        else 
        {
            for (int i = 0; i < accountList.size(); i++)
            {
                accountNumberInformation = accountList.get(i).getAccountNumber();
    
                if (accountNumberInformation == accountNumber)
                {
                    accountInformation = accountList.get(i).closeAccountToString();
                    accountList.remove(i);
                }
            }
        }
        return accountInformation;
    }

    // ------------ Setters -----------

    /**
     * Metod för att byta namn hos kund
     * @return nytt namn
     */ 
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Metod för att byta efternamn hos kund
     * @return nytt efternamnnamn
     */ 
    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    // ------------ Getters -----------

    /**
     * Metod för att få namn
     * @return namn
     */ 
    public String getName()
    {
        return name;
    }

    /**
     * Metod för att få efternamn
     * @return efternamn
     */ 
    public String getSurname()
    {
        return surname;
    }

    /**
     * Metod för att få personNr
     * @return personNr
     */ 
    public String getpNo()
    {
        return pNo;
    }

    /**
     * Metod för att få info om kund (personNr Namn Efternamn)
     * @return info om kund
     */ 
    public String toString()
	{
		String info = getpNo() + " " + getName()  + " " + getSurname();
        return info;
	}

}
