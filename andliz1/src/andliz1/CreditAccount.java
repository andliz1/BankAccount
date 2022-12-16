package andliz1;

/**
 * Klass som håller koll på kundens kreditkonto
 * I denna klass kan man få info om kontot
 * Man kan sätta in pengar, ta ut pengar
 * Beräknar räntan
 * @author Andreas Linder, andliz-1
 */

public class CreditAccount extends Account {

    private String accountType = "Kreditkonto";
    private double interest = 0.5;
    private int creditLimit = -5000;

    /**
     * Constructor som ärver allt från Account-klassen
     */
    public CreditAccount()
    {
        super();
    }
 
    @Override
    public String toString()
    {
        if (balance < 0)
        {
            interest = 7.0;
        }
        String info = getAccountNumber() + " " + getBalance(balance)  + " " + getAccountType() + " " + getInterestRate(interest);
	    return info;
	}


    @Override
    public String closeAccountToString() 
    {
        String info = getAccountNumber() + " " + getBalance(balance)  + " " + getAccountType() + " " + calculateInterest(interest, balance);
		return info;
    }

    @Override
    public String getAccountType() 
    {
        return accountType;
    }

    @Override
    public Boolean withdraw(double withdrawAmount) 
    {
        if (balance - withdrawAmount >= creditLimit)
        {
            balance -= withdrawAmount;
            Transactions transaction = new Transactions(balance, withdrawAmount*-1);
            transactionList.add(transaction.toString());
            return true;
        }
        else
            return false;
    }

    @Override
    public double getInterest ()
    {
        return interest;
    }

    @Override
    public double getDoubleBalance() {
        return balance;
    }
}
    
