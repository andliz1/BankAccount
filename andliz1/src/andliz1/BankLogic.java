package andliz1;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Klass som kallar på objekten som ska utföra operationerna
 * Bygger på en Arraylist customerList som har koll på kunderna
 * Metoderna kallas från denna klass (och i viss del vidare från customer till account) 
 * @author Andreas Linder, andliz-1
 */

public class BankLogic implements Serializable{

    private ArrayList<Customer> customerList = new ArrayList<Customer>(0);

    private static final long serialVersionUID = 1L;

    /**
     * Metod för att söka igenom arrayen customerList för att hitta en specifik kund med asvseende på pNo
     * Loopar igenom customList och om pNo stämmer överrens med den vi letar efter så skapar vi ett referensobjekt till objektet
     * Vi skapar alltså inget nytt objekt utan bara en ny referens till det objekt som redan är skapat
     * @param existingCustomer Referensobjekt till objektet vi söker efter med ett visst pNo
     * @return Referensobjektet
     */ 
    public Customer searchExistingCustomer (String pNo)
    {
        for (Customer customer: customerList)
        {
            if (customer.comparePNo(pNo))
            {
                return customer;
            }
        }
        return null;
    }

    /**
     * Metod för att få info om alla kunder
     * Loopar igenom Array customList och översätter alla kunder till rätt utskriftsformat med toString()
     * @param allCustomers Array med info om alla kunder
     * @return Array med info om alla kunder
     */ 
    public ArrayList<String> getAllCustomers()
    {
        ArrayList<String> allCustomers = new ArrayList<String>();

        for (Customer customerList: customerList)
        {
        String customerListToAllCustomer = customerList.toString();
        allCustomers.add(customerListToAllCustomer);
        }
        
        return allCustomers;
    }
    
    /**
     * Metod för att skapa ny kund
     * Skapar ett nytt objekt av klassen Customer
     * @return True om konto skapas, annars false
     */ 
    public boolean createCustomer(String name, String surname, String pNo)
    {

        if (searchExistingCustomer(pNo) == null)
        {
            Customer Createdcustomer = new Customer(name, surname, pNo);
            customerList.add(Createdcustomer);
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Metod för att få info om kund och dess konton
     * @param customerAndAccountInformation Array där första platsen är för kundens info, resterande för dess konto
     * @param existingCustomer Referensobjekt till kunden (objektet) vi söker
     * @return Array med info med kunder och konto, null om kund ej finns
     */ 
    public ArrayList<String> getCustomer(String pNo)
    {
        ArrayList<String> customerAndAccountInformation = new ArrayList<String>();
        Customer existingCustomer = searchExistingCustomer(pNo);

            if (existingCustomer != null)
            {
                customerAndAccountInformation.add(existingCustomer.toString()); // Lägger til Personen och uppgifterna därifrån
                customerAndAccountInformation.addAll(existingCustomer.getAllAccounts()); // Lägger till all info från accountet
                return customerAndAccountInformation;
            }
            else
                return null;    
    } 

    /**
     * Metod för att få info om kund och dess konton i en 2D array för att kunna använda till JTable i GUI
     * @param arrToTable Array med info med kunder och konto
     * @return Array med info med kunder och konto, null om kund ej finns
     */ 
    public String[][] accountInfoToTable(String pNo)
    {
        Customer customer = searchExistingCustomer(pNo);

        if (customer != null)
        {
            String [][] arrToTable = customer.getAccountArray();
            return arrToTable;
        }
        else
            return null;
    }

    /**
     * Metod för att ändra namn hos kund
     * @param namedToBeChanged Referensobjekt till kunden (objektet) vi söker
     * @return True om namn ändrats, annars false
     */ 
    public boolean changeCustomerName(String name, String surname, String pNo)
    {
        Customer namedToBeChanged = searchExistingCustomer(pNo);

        if (namedToBeChanged != null)
        {
            // returnerar false ifall båda är tomma
            if (surname == "" && name == "") 
            {
                return false;
            }

            // Om namn inte är tom så byter den namn
            if (name != "") 
            {
                namedToBeChanged.setName(name);
            }

            // Om efternamn inte är tom byter den efternamn
            if (surname != "") 
            {
                namedToBeChanged.setSurname(surname);
            }
            return true;
        }
        else  
            return false;
    }
    

    /**
     * Metod för att skapa kreditkonto till kund
     * @param createAccount Referensobjekt till kunden (objektet) vi söker
     * @return returnerar kontoNr på nya kontot eller -1 om kund ej finns
     */
    public int createCreditAccount(String pNo)
    {
        Customer createAccount = searchExistingCustomer(pNo);
        
        if (createAccount != null)
        {
            int accountNumber = createAccount.createCreditAccount();
            return accountNumber;
        }
        else 
            return -1;
    }

    /**
     * Metod för att skapa sparkonto till kund
     * @param createAccount Referensobjekt till kunden (objektet) vi söker
     * @return returnerar kontoNr på nya kontot eller -1 om kund ej finns
     */
    public int createSavingsAccount(String pNo)
    {
        Customer createAccount = searchExistingCustomer(pNo);
        
        if (createAccount != null)
        {
            int accountNumber = createAccount.createSavingsAccount();
            return accountNumber;
        }
        else 
            return -1;
    }

    /**
     * Metod för att få info om ett specifikt konto
     * @param accountInformation String med info från kontot
     * @param getAccountInformation Referensobjekt till kunden (objektet) vi söker
     * @return String med info om konto
     */
     public String getAccount(String pNo, int accountId)
    {
        Customer getAccountInformation = searchExistingCustomer(pNo);

        if (getAccountInformation != null)
        {
            String accountInformation = getAccountInformation.getAccountInformation(accountId);
            return accountInformation;
        }
        else
        {
            return null;            
        }
    } 

    /**
     * Metod för att sätta in pengar på ett konto
     * @param depositAccount Referensobjekt till kunden (objektet) vi söker
     * @param account Referensobjekt till kontot (objektet i objektet) vi söker
     * @return True om transaktionen gick igenom, annars false
     */
     public boolean deposit(String pNo, int accountId, int amount)
    {
        Customer depositAccount = searchExistingCustomer(pNo);
        Account account = depositAccount.searchExistingAccount(accountId);
        Boolean depositdrawWentThrough = false;
    
        if (depositAccount != null && account != null) 
        {
            depositdrawWentThrough= account.deposit((double)amount);
            return depositdrawWentThrough;
        }
        else
            return depositdrawWentThrough;
    }


    /**
     * Metod för att ta ut pengar från ett konto
     * @param depositAccount Referensobjekt till kunden (objektet) vi söker
     * @param account Referensobjekt till kontot (objektet i objektet) vi söker
     * @return True om transaktionen gick igenom, annars false
     */
     public boolean withdraw(String pNo, int accountId, int amount)
    {
        Customer withdrawAccount = searchExistingCustomer(pNo);
        Account account = withdrawAccount.searchExistingAccount (accountId);
        Boolean withdrawWentThrough = false;
    
        if (withdrawAccount != null && account != null)
        {
            withdrawWentThrough= account.withdraw((double)amount); // Om transaktionen går igenom ändras det till true
            return withdrawWentThrough;
        }
        else 
            return withdrawWentThrough;
    }

    /**
     * Metod för att stänga ett konto
     * @param closeAccountCustomer Referensobjekt till kunden (objektet) vi söker
     * @param closeAccountInformation string info med konto som avslutas
     * @return String med info om konto som avslutas
     */
     public String closeAccount(String pNo, int accountId)
    {
        Customer closeAccountCustomer = searchExistingCustomer(pNo);

        if (closeAccountCustomer != null)
        {
            String closeAccountInformation = closeAccountCustomer.getClosingAccountInformation(accountId);
            return closeAccountInformation;
        }
        else
        {
            return null;
        }
    } 

    /**
     * Metod för att ta bort en kund
     * @param deleteCustomer Referensobjekt till kunden (objektet) vi söker
     * @param deleteCustomerInformation Array info med kund och konto som avslutas
     * @return Array med info om kund och konto som avslutats
     */
    public ArrayList<String> deleteCustomer(String pNo)
    {
        ArrayList<String> deleteCustomerInformation = new ArrayList<String>();
        Customer customer = searchExistingCustomer(pNo);

        if (customer != null)
        {
            deleteCustomerInformation.add(customer.toString());                     
            deleteCustomerInformation.addAll(customer.getAllClosingAccounts());     
            customerList.remove(customer);                                         
        }
        else
        {
            return null;
        }

        return deleteCustomerInformation;
    }     

    /**
     * Metod för att få information om alla transaktioner
     * @param customer Referensobjekt till kunden (objektet) vi söker
     * @param account Referensobjekt till kontot (objektet) vi söker
     * @param transactionInformation ArrayList med info om transaktionerna
     * @return Array med info om transaktionerna
     */

    public ArrayList<String> getTransactions(String pNo, int accountId)
    {
        Customer customer = searchExistingCustomer(pNo);
        Account account = customer.searchExistingAccount(accountId);
        ArrayList<String> transactionInformation = new ArrayList<>();

        if (customer != null && account != null)
        {
            transactionInformation = account.getTransactions();
            return transactionInformation;
        }    
        else
            return null;
    }

    /**
     * Metod för att få information om kundens för- och efternamn
     * @param customer Referensobjekt till kunden (objektet) vi söker
     * @param names String med namn på kunden
     * @return String med information om namn
     */
    public String getNameInformation(String pNo)
    {
        Customer customer = searchExistingCustomer(pNo);

        String names = (customer.getName() + " " + customer.getSurname());

        return names;
    }

}
