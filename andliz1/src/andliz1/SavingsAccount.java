package andliz1;


/**
 * Klass som håller koll på kundens sparkonto
 * I denna klass kan man få info om kontot
 * Man kan sätta in pengar, ta ut pengar
 * Beräknar räntan
 * @author Andreas Linder, andliz-1
 */


public class SavingsAccount extends Account {

    private String accountType = "Sparkonto";
    private double interest = 1.2;
    private Boolean firstWithdraw = true;


    /**
     * Constructor som ärver allt från Account-klassen
     */
    public SavingsAccount()
    {
        super();
    }

    @Override
    public String toString()
    {
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

    /**
     * Metod som används för att ta ut pengar från konto
     * @param firstWithdraw Om det är första gången ett uttag görs så sätts den till false
     * @return true om det gick igenom, annars false
     */
    @Override
    public Boolean withdraw(double withdrawAmount) // Den här borde vi kunna förkorta
    {
    if (firstWithdraw)
    {
        firstWithdraw = false;
    }
    else 
        withdrawAmount *=(1+0.02);
    
    if (withdrawAmount <= balance && withdrawAmount > 0)
        {
            balance -= withdrawAmount;
            Transactions transaction = new Transactions(balance, withdrawAmount*-1);
            transactionList.add(transaction.toString());
            return true;
        }
        else
        {
            return false;
        }
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
