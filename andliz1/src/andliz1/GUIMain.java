package andliz1;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.io.FileWriter;

/**
 * Klass som designar GUI och sköter kommunikationen till bankLogic 
 * @author Andreas Linder, andliz-1
 */

public class GUIMain extends JFrame implements ActionListener 
{
    private BankLogic logic = new BankLogic();
    private CardLayout cardLayout = new CardLayout();
    private JPanel cardPanel = new JPanel();
    private JLabel topLabel;
    private JTextField nameField; 
    private JTextField surnameField;
    private JTextField pNoField;
    private JTextField loginPNoField;
    private JTextField depositField;
    private JTextField withdrawField;
    private String customerPNo;
    private int accountNumber = -1;
    private JTable table;
    private JPanel cardPanel1;
    private JPanel cardPanel2;
    private JScrollPane centerPanel;


    /**
     * Constructor
     * Varje gång ett objekt skapas av klassen så startas Log in Layouten på GUIn
     */

    GUIMain()
    {
        buildFrame();
        buildLoginLayout();
        buildMenu();
        add(cardPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Funktion för att bygga upp ramen i GUI där vi sätter storlek, plats och väljer huvudlayout etc.
     */ 
    private void buildFrame()
    {
        setSize(450,400);
        setLocation(650, 250);
        setResizable(false);
        cardPanel.setLayout(cardLayout);
        ImageIcon image = new ImageIcon("logo.jpg");
        setIconImage(image.getImage()); // Change Icon of frame
        setTitle("Andreas Bank"); // title
           
    }

    /**
     * Funktion för att bygga upp inloggningsfönstret i GUI.
     * GridBagLayout används för att placera ut komponenterna
     * Knappar görs i egna funktioner och blir kallade på
     */ 
    private void buildLoginLayout()
    {
        cardPanel1 = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        cardPanel1.setBackground(new Color(192,214,226));

        JLabel header = new JLabel ("Register");
        header.setFont(new Font("Calibri", Font.PLAIN,25));
        header.setHorizontalAlignment(SwingConstants.CENTER);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;  
        c.gridwidth = 2;
        cardPanel1.add(header,c);

        JLabel loginHeader = new JLabel ("Log In");
        loginHeader.setFont(new Font("Calibri", Font.PLAIN,25));
        loginHeader.setHorizontalAlignment(SwingConstants.CENTER);
        c.gridy = 5; c.gridx = 0; 
        c.gridwidth = 2;
        c.insets = new Insets(20, 5, 5, 5);
        cardPanel1.add(loginHeader,c);

        JLabel nameLabel = new JLabel("Name: ");
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.insets = new Insets(5, 75, 5, 5);
        cardPanel1.add(nameLabel,c);

        JLabel surnameLabel = new JLabel("Surname: "); 
        c.gridx = 0;
        c.gridy = 2;
        cardPanel1.add(surnameLabel,c);

        JLabel pNoLabel = new JLabel("Personal Number: ");
        c.gridx = 0;
        c.gridy = 3;
        cardPanel1.add(pNoLabel,c);

        JLabel pNoLoginLabel = new JLabel("Personal Number: ");
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 1;
        cardPanel1.add(pNoLoginLabel,c);

        nameField = new JTextField(15);
        c.gridx = 1;
        c.gridy = 1;
        c.insets = new Insets(5, 5, 5, 75);
        cardPanel1.add(nameField,c);

        surnameField = new JTextField(15);
        c.gridx = 1;
        c.gridy = 2;
        cardPanel1.add(surnameField,c);

        pNoField = new JTextField(15);
        c.gridx = 1;
        c.gridy = 3;
        cardPanel1.add(pNoField,c);

        loginPNoField = new JTextField(15);
        c.gridx = 1;
        c.gridy = 6;
        c.gridwidth = 1;
        cardPanel1.add(loginPNoField,c);

        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        c.insets = new Insets(5, 140, 5, 140);
        cardPanel1.add(customerButton(), c);

        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 2;
        c.insets = new Insets(5, 140, 5, 140);
        cardPanel1.add(logInButton(), c);

        cardPanel.add(cardPanel1, "1");
    }


    /**
     * Funktion för att bygga upp använderfönstret i GUI.
     * BorderLayout används som bas för layouten
     * GridBagLayout används för att centrera rubriken "Välkommen *användare*"
     * GridLayout används för att placera ut komponenterna i BorderLayout
     * Knappar görs i egna funktioner och blir kallade på från denna
     * JTable används för att lista upp använderens konton. Man gör även transaktioner genom att trycka på kontot i listan som ska användas
     * @param customerPNo String med info om kundens personnr som loggar in som används till andra metoder
     */ 
    private void buildCustomerLayout()
    {
        customerPNo = loginPNoField.getText();
        cardPanel2 = new JPanel(new BorderLayout(10,10));
        
        // Översta raden med välkomstext
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setPreferredSize(new Dimension(700,80));
        topLabel = new JLabel("You are logged in as " + logic.getNameInformation(customerPNo));
        topLabel.setFont(new Font("Calibri", Font.PLAIN,25)); // Ändrar font 
        topPanel.add(topLabel);
        
        // Högra panelen
        JPanel rightPanel = new JPanel(new BorderLayout(10,10));
        rightPanel.setPreferredSize(new Dimension(200,100));

        JPanel rightTopPanel = new JPanel(new GridLayout(0, 1, 30,10));  
        rightTopPanel.add(changeNameButton());
        rightTopPanel.add(changeSurnameButton());
        rightTopPanel.add(saveTransactionsButton());
        rightTopPanel.add(showTransactionsButton());
        rightPanel.add(rightTopPanel,BorderLayout.NORTH);

        JPanel southRightPanel = new JPanel(new GridLayout(0,1,30,10));
        southRightPanel.add(logOutButton());
        southRightPanel.add(deleteCustomerButton());
        rightPanel.add(southRightPanel,BorderLayout.SOUTH);
        rightPanel.setBorder(new EmptyBorder(0, 8, 8, 8)); 

        // Center panel
        JPanel midPanel = new JPanel(new BorderLayout(10,10));
        centerPanel = new JScrollPane();
        centerPanel.setViewportView(accounTable());
        midPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel midTopPanel = new JPanel(new GridLayout(0, 3, 30,10));
        midTopPanel.setPreferredSize(new Dimension(480,100));
        JLabel depositLabel = new JLabel();
        depositLabel.setText("Amount: ");
        depositField = new JTextField(15);
        JLabel withdrawLabel = new JLabel();
        withdrawLabel.setText("Amount: ");
        withdrawField = new JTextField(15);
        midTopPanel.add(depositLabel);
        midTopPanel.add(depositField);
        midTopPanel.add(depositButton());
        midTopPanel.add(withdrawLabel);
        midTopPanel.add(withdrawField);
        midTopPanel.add(withdrawButton());   
        midTopPanel.add(savingsAccountButton());
        midTopPanel.add(creditAccountButton());
        midTopPanel.add(deleteAccountButton());
        midPanel.add(midTopPanel, BorderLayout.NORTH);
        midPanel.setBorder(new EmptyBorder(0, 8, 8, 8));

        // Addera alla panel till CardPanel
        cardPanel2.add(topPanel, BorderLayout.NORTH);
        cardPanel2.add(midPanel, BorderLayout.CENTER);
        cardPanel2.add(rightPanel, BorderLayout.EAST);

        // Addera Cardpanelen till nr 2 
        cardPanel.add(cardPanel2, "2");
    }


    /**
     * Funktion för att ändra label när man ändrar namn
     */ 
    private void setTextCustomerLabel()
    {
        topLabel.setText("You are logged in as " + logic.getNameInformation(customerPNo));
    }

    /**
     * Funktion för att bygga knapp "Create Customer"
     * @return Create Customer knapp
     */ 
    private JButton customerButton()
    {
        JButton createCustomerButton = new JButton("Create customer");
        createCustomerButton.addActionListener((ActionEvent event) -> createCustomer());
        return createCustomerButton;
    }

    /**
     * Funktion för att bygga knapp "Delete Customer"
     * @return Delete customer knapp
     */
    private JButton deleteCustomerButton()
    {
        JButton createDeleteCustomerButton = new JButton ("Delete customer");
        createDeleteCustomerButton.addActionListener((ActionEvent event) -> deleteCustomer());
        return createDeleteCustomerButton;
    }

    /**
     * Funktion för att bygga knapp "Log In"
     * @return Log In knapp
     */
    private JButton logInButton()
    {
        JButton createLogInButton = new JButton("Log in");
        createLogInButton.addActionListener((ActionEvent event) -> login());
        return createLogInButton;
    }

    /**
     * Funktion för att bygga knapp "Deposit"
     * @return Deposit knapp
     */
    private JButton depositButton()
    {
        JButton createDepositButton = new JButton("Deposit");
        createDepositButton.addActionListener((ActionEvent event) -> {depositAccount();});
        return createDepositButton;
    }

    /**
     * Funktion för att bygga knapp "Withdraw"
     * @return Withdraw knapp
     */
    private JButton withdrawButton()
    {
        JButton createWithdrawButton = new JButton("Withdraw");
        createWithdrawButton.addActionListener((ActionEvent event) -> withdrawAccount());
        return createWithdrawButton;
    }

    /**
     * Funktion för att bygga knapp "Create savings account"
     * @return Create savings account knapp
     */
    private JButton savingsAccountButton()
    {
        JButton createSavingsAccountButton = new JButton("Create savings account");
        createSavingsAccountButton.addActionListener((ActionEvent event) -> newSavingsAccount()); 
        return createSavingsAccountButton;
    }

    /**
     * Funktion för att bygga knapp "Create credit account"
     * @return Create credit account knapp
     */
    private JButton creditAccountButton()
    {
        JButton createCreditAccountButton = new JButton("Create credit account");
        createCreditAccountButton.addActionListener((ActionEvent event) -> {newCreditAccount();});
        return createCreditAccountButton;
    }

    /**
     * Funktion för att bygga knapp "Log out"
     * @return Log Out knapp
     */
    private JButton logOutButton()
    {
        JButton createLogOutButton = new JButton("Log Out");
        createLogOutButton.addActionListener((ActionEvent event) -> logOut());
        return createLogOutButton;
    }
    
    /**
     * Funktion för att bygga knapp "Delete account"
     * @return Delete Account knapp
     */
    private JButton deleteAccountButton()
    {
        JButton createDeleteAccountButton = new JButton("Delete account");
        createDeleteAccountButton.addActionListener((ActionEvent event) -> deleteAccount());
        return createDeleteAccountButton;
    }

    
    /**
     * Funktion för att bygga knapp "Show Transactions"
     * @return Show Transactions knapp
     */
    private JButton showTransactionsButton()
    {
        JButton createShowTransactionsButton = new JButton("Show Transactions");
        createShowTransactionsButton.addActionListener((ActionEvent event) -> showTransactions());
        return createShowTransactionsButton;
    }

    /**
     * Funktion för att bygga knapp "Change Name"
     * @return Change Name knapp
     */
    private JButton changeNameButton()
    {
        JButton createChangeNameButton = new JButton("Change name");
        createChangeNameButton.addActionListener((ActionEvent event) -> changeName());
        return createChangeNameButton;
    }

    /**
     * Funktion för att bygga knapp "Change Surame"
     * @return Change Surame knapp
     */
    private JButton changeSurnameButton()
    {
        JButton createchangeSurnameButton = new JButton("Change surname");
        createchangeSurnameButton.addActionListener((ActionEvent event) -> changeSurname());
        return createchangeSurnameButton;
    }

    /**
     * Funktion för att bygga knapp "Save Transactions"
     * @return Save Transactions knapp
     */
    private JButton saveTransactionsButton()
    {
        JButton createSaveTransactionsButton = new JButton("Save transactions");
        createSaveTransactionsButton.addActionListener((ActionEvent event) -> saveTransactions());
        return createSaveTransactionsButton;
    }     

    /**
     * Funktion för att visa transaktioner i GUI:et
     */
    private void showTransactions()
    {
        try
        {
            String transactions = String.join(",",logic.getTransactions(customerPNo, accountNumber));
            showMessageDialog(transactions);
        }
  
        catch(NullPointerException ne)
        {
            showMessageDialog("You have to select an account from the table!");
        }
    }

    /**
     * Funktion för att radera konto
     * @param s innehåller information om det raderade kontot
     * @param customerPNo inloggade användarens personnr
     * @param accountNumber valda kontot från tabellen
     */
    private void deleteAccount()
    {
        String s = logic.closeAccount(customerPNo, accountNumber);

        if(s != null)
        {
            showMessageDialog(s);
            updateTable();
        }
            
        else
            showMessageDialog("Välj ett konto från listan!");
    }

    /**
     * Funktion för att ändra namn
     * @param name användarens input
     * @param surname befintliga efternamnet
     * @param customerPNo inloggade användarens personnr
     */
    private void changeName()
    {
        String surname = "";

        try 
        {
            String name = JOptionPane.showInputDialog(null,"Type in your new name:", "Change name", JOptionPane.QUESTION_MESSAGE);

            if (checkOnlyLetter(name) == true)
            {
                if (logic.changeCustomerName(name, surname, customerPNo))
                {
                    setTextCustomerLabel();
                    showMessageDialog("Name changed to " + name);
                }
                else
                    showMessageDialog("Name not changed!");
            }
            else
            {
                showMessageDialog("Your name can only contain letters!");
            }
        } catch (NullPointerException e) {
            showMessageDialog("Name not changed!");
        }


    }

    /**
     * Funktion för att ändra namn
     * @param name befintliga namnet
     * @param surname användarens input
     * @param customerPNo inloggades personnr
     * @param accountNumber valda kontot från tabellen
     */
    private void changeSurname()
    {
        String name = "";

        try 
        {
            String surname = JOptionPane.showInputDialog(null,"Type in your new surname:", "Change surname", JOptionPane.QUESTION_MESSAGE);

            if (checkOnlyLetter(surname) == true)
            {
                if(logic.changeCustomerName(name, surname, customerPNo) == true)
                {
                    setTextCustomerLabel();
                    showMessageDialog("Name changed to " + surname);
                }
                else
                    showMessageDialog("Surname not changed!");
            }
            else
            {
                showMessageDialog("Your name can only contain letters!");
            }
        } catch (NullPointerException e) {
            showMessageDialog("Surname not changed!");
        }    
    }

    /**
     * Funktion för att logga ut
     */
    private void logOut ()
    {
        setSize(500,400);
        cardLayout.show(cardPanel,"1");
    }

    /**
     * Metod för att sätta in pengar
     * @param depositAmount summan som ska sättas in läses av från TextField depositField
     * @param customerPNo inloggades personnr
     * @param accountNumber valda kontot från tabellen
     */
    private void depositAccount() 
    {

        try
        {
            int depositAmount = Integer.parseInt(depositField.getText()); // ändra double?
    
            if (accountNumber != -1)
            {
                if(logic.deposit(customerPNo , accountNumber , depositAmount))
                {
                    showMessageDialog("Deposit went through!");
                    updateTable();
                }
        
                depositField.setText("");
            }

            else
                showMessageDialog("You must select an account from the Table!");
            }
        catch(NumberFormatException nf)
        {
            showMessageDialog("That is not a number");
        }
                     
    }

    /**
     * Metod för att ta ut pengar
     * @param withdrawAmount summan som ska tas ut läses av från TextField withdrawField
     * @param customerPNo inloggades personnr
     * @param accountNumber valda kontot från tabellen
     */
    private void withdrawAccount() 
    {
        try{
            
            int withdrawAmount = Integer.parseInt(withdrawField.getText()); // ändra denna till double?
            
            
            if (accountNumber != -1)
            {
                if(logic.withdraw(customerPNo, accountNumber, withdrawAmount))
                {
                    showMessageDialog("Withdraw went through!");
                    updateTable();
                }
                else
                {
                    showMessageDialog("You don't have enough money!"); 
                }
        
                withdrawField.setText("");
            } 

            else
                showMessageDialog("You must select an account from the Table!");
            
            }
    
        catch (NumberFormatException nf)
            {
                showMessageDialog("That is not a number");
            }
    }

    /**
     * Funktion för att radera kund
     * @param output String som innehåller information om kund och dess konto
     * @param customerPNo inloggades personnr
     * Efter att kunden raderas så går cardLayout till inloggninsgvy
     */
    private void deleteCustomer()
    {
        String output = String.join(",",logic.deleteCustomer(customerPNo));
        setSize(500,400);
        cardLayout.show(cardPanel,"1");
        showMessageDialog(output);
    }

    /**
     * Funktion för att skapa nytt kreditkonto
     * @param customerPNo inloggades personnr
     * Efter att kontot blivit skapat så uppdateras tabellen via funktionen updateTable()
     */
    private void newCreditAccount()
    {
        logic.createCreditAccount(customerPNo);
        updateTable(); 
    }

    /**
     * Funktion för att skapa nytt sparkonto
     * @param customerPNo inloggades personnr
     * Efter att kontot blivit skapat så uppdateras tabellen via funktionen updateTable()
     */
    private void newSavingsAccount()
    {
        logic.createSavingsAccount(customerPNo);
        updateTable();
    }

    /**
     * Funktion för att logga in
     * @param loginPNoField personnr som försöker logga in
     * Om personnr inte finns varnas användaren med pop up fönster
     * Om personnr finns så loggas användaren in till nästa vy som blir större
     * Alla textField i inloggningsvyn sätts tomma
     */
    private void login()
    {
        if (logic.searchExistingCustomer(loginPNoField.getText()) != null)
        {
            setSize(900,500);
            buildCustomerLayout();
            nameField.setText ("");
            surnameField.setText ("");
            pNoField.setText ("");
            loginPNoField.setText ("");
            cardLayout.show(cardPanel,"2");
        }
        else
            showMessageDialog("Kund kunde inte hittas!");
    }

    /**
     * Funktion för att se alla kunder i banken
     * @param allCustomers String med info om alla kunder
     */
    private void showCustomers()
    {
        String allCustomers = String.join(",", logic.getAllCustomers());
        showMessageDialog(allCustomers);
    }

    /**
     * Metod för att se skapa kund
     * Sätter alla TextField till tomma efter att kunden blivit skapad
     */
    private void createCustomer()
    {
        String name = nameField.getText();
        String surname = surnameField.getText();
        String pNo = pNoField.getText();

        if (name.equals("") || surname.equals("") || pNo.equals(""))
        {
            showMessageDialog("You can't have an empty field!");
        }

        else if (!(checkOnlyLetter(name)))
        {
            showMessageDialog("You can only have letters in your name");
        }

        else if (!(checkOnlyLetter(surname)))
        {
            showMessageDialog("You can only have letters in your surname");
        }

        else if(!checkOnlyNumbers(pNo))
        {
            showMessageDialog("Your personal number can only contain numbers");
        }

        else
        {
            if (logic.createCustomer(name, surname, pNo))
            {
                showMessageDialog("Welcome to the bank " + name);
                setEmptyFields();
            }
    
            else
                showMessageDialog("Customer already Exist!");
        }
    }

    /**
     * Metod för att visa MessageDialog men det meddelande som den funktion som kallar metoden har
     */
    private void showMessageDialog(String info)
    {
        JOptionPane.showMessageDialog(null, info, "Information" , JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Funktion för att skapa en tabell som innehar information om kundens konto
     * MouseListener som tar värdet i accountNumber kolumnen och använder som input i andra funktioner
     * @return Table med info om kundens konton
     */
    private JTable accounTable()
    {
        String [][] accountInfo = logic.accountInfoToTable(customerPNo);

        String [] columnNames = {"Account number", "Balance", "Account type", "Interest"};

        table = new JTable(accountInfo, columnNames);

        // Kod från nätet med egen modifikation: https://stackoverflow.com/questions/5488023/how-to-get-cell-value-of-jtable-depending-on-which-row-is-clicked

        table.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseClicked(final MouseEvent e) 
            {
                if (e.getClickCount() == 1) 
                {
                    final JTable target = (JTable)e.getSource();
                    final int row = target.getSelectedRow();
                    final int column = 0;
                    accountNumber = Integer.parseInt((String) target.getValueAt(row, column));
                }
            }
        });

        return table;

    }

    /**
     * Funktion för att uppdatera tabellen med info om kundens konto
     * Uppdateras varje gång en kund sätter in pengar, tar ut pengar, skapar nytt konto eller tar bort bef. konto
     * @param accountNumber sätts till -1 igen så inget konto är valt
     */
    private void updateTable()
    {
        //centerPanel.remove(table);
        accountNumber = -1;
        centerPanel.setViewportView(accounTable());
    }

    /**
     * Funktion för att skapa upp meny
     * @param loadItem Här ska användaren kunna ladda in banken
     * @param saveItem Här ska användaren kunna spara in banken
     * @param exitItem Här kan användaren avsluta programmet
     * @param showCustomersItem Här kan användaren se info om alla kunder i banken
     */
    private void buildMenu()
    {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenu helpMenu = new JMenu("Help");

        JMenuItem loadItem = new JMenuItem("Load Bank"); //Load Bank har ingen funktion ännu utan får det i nästa inlämningsuppgift.
        JMenuItem saveItem = new JMenuItem("Save Bank"); //Save Bank har ingen funktion ännu utan får det i nästa inlämningsuppgift.
        JMenuItem exitItem = new JMenuItem("Exit Bank");

        JMenuItem showCustomersItem = new JMenuItem("Show All Customers");

        fileMenu.add(loadItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);

        helpMenu.add(showCustomersItem);

        fileMenu.setMnemonic(KeyEvent.VK_F); // alt f for load
        helpMenu.setMnemonic(KeyEvent.VK_H); // alt h for exit

        loadItem.setMnemonic(KeyEvent.VK_L); // l for load
        saveItem.setMnemonic(KeyEvent.VK_S); // s for save
        exitItem.setMnemonic(KeyEvent.VK_E); // e for exit
        helpMenu.setMnemonic(KeyEvent.VK_S); // s to show all customers


        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        this.setJMenuBar(menuBar);

        exitItem.addActionListener((ActionEvent event) -> {closeProgram();});
        saveItem.addActionListener((ActionEvent event) -> {saveBank();});
        loadItem.addActionListener((ActionEvent event) -> {loadBank();});
        showCustomersItem.addActionListener((ActionEvent event) -> showCustomers());
    }

    /**
     * Funktion för att avsluta programmet
     */
    private void closeProgram()
    {  
        System.exit(0);
    }

    /**
     * Funktion för att undersöka om det bara är bokstäver i namnet
     * @return true om det bara är bokstäver, annars false
     */
    private Boolean checkOnlyLetter(String name)
    {
        boolean result = name.matches("[a-öA-Ö\\s]+");
        return result;
    }

    /**
     * Funktion för att sätta textfälten tomma på loginpage
     */
    private void setEmptyFields()
    {
        nameField.setText ("");
        surnameField.setText ("");
        pNoField.setText ("");
    }

    /**
     * Funktion för att undersöka om det bara är siffror i personnr
     */
    private Boolean checkOnlyNumbers(String pNo)
    {
        boolean result = pNo.matches("[0-9]+");
        return result;
    }

    /**
     * Funktion för att spara transaktioner till en textfil
     */
    private void saveTransactions() 
    {

        ArrayList<String> transactions = logic.getTransactions(customerPNo, accountNumber);

        if (transactions!= null)
        {
            JFileChooser fileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
            fileChooser.setDialogTitle("Save Transactions");
            fileChooser.setFileFilter(new FileNameExtensionFilter("txt files (*.txt)", "txt"));
            int response = fileChooser.showSaveDialog(null);

            if (response == JFileChooser.APPROVE_OPTION)
            {
    
                if(fileChooser.getSelectedFile().exists())
                {
                    int answer = JOptionPane.showConfirmDialog(this,"File already exist, do you want to override?","Existing file",JOptionPane.YES_NO_CANCEL_OPTION);
    
                    if (answer == JOptionPane.YES_OPTION)
                    {
                        try 
                        {
                            FileWriter writer = new FileWriter(fileChooser.getSelectedFile());
                            writer.write("Here are all of the transactions from account number " + accountNumber + ": \n");
                            
                            for(String str: transactions) 
                            {
                                writer.write(str + System.lineSeparator());
                            }

                            writer.close();
                            showMessageDialog("File saved!");
                        }

                        catch(FileNotFoundException e)
                        {
                            showMessageDialog("You do not have permisson to save this file, try with another name");
                        }

                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        showMessageDialog("Nothing saved!");
                    }
                }
                else
                {
                    try 
                    {
                        FileWriter writer = new FileWriter(fileChooser.getSelectedFile() + ".txt");
                        writer.write("Here are all of the transactions from account number " + accountNumber + ": \n");
                        
                        for(String str: transactions) 
                        {
                            writer.write(str + System.lineSeparator());
                        }

                        writer.close();
                        showMessageDialog("File saved!");
                    }

                    catch(FileNotFoundException e)
                    {
                        showMessageDialog("You do not have permisson to save this file, try with another name");
                    }

                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }

        else
            showMessageDialog("You have to select an account from the table!");            
    }

    /**
     * Funktion för att spara bank till en .dat fil
     */
    private void saveBank()
    {
        JFileChooser fileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
        fileChooser.setDialogTitle("Save Bank");
        fileChooser.setFileFilter(new FileNameExtensionFilter("dat files (*.dat)", "dat"));
        int response = fileChooser.showSaveDialog(null);

        if (response == JFileChooser.APPROVE_OPTION)
        {

            if(fileChooser.getSelectedFile().exists())
            {
                int answer = JOptionPane.showConfirmDialog(this,"File already exist, do you want to override?","Existing file",JOptionPane.YES_NO_CANCEL_OPTION);

                if (answer == JOptionPane.YES_OPTION)
                {
                    try 
                    {
                        FileOutputStream file = new FileOutputStream(fileChooser.getSelectedFile());
                        ObjectOutputStream output = new ObjectOutputStream(file);
                        output.writeObject(logic);
                        output.close();
                        file.close();
                        showMessageDialog("File saved!");
                    }

                    catch(FileNotFoundException e)
                    {
                        showMessageDialog("You do not have permisson to save this file, try with another name");
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    showMessageDialog("Nothing saved!");
                }
            }
            else
            {
                try 
                {
                    FileOutputStream file = new FileOutputStream(fileChooser.getSelectedFile() + ".dat");
                    ObjectOutputStream output = new ObjectOutputStream(file);
                    output.writeObject(logic);
                    output.close();
                    file.close();
                    showMessageDialog("File saved!");
                }

                catch(FileNotFoundException e)
                {
                    showMessageDialog("You do not have permisson to save this file, try with another name");
                }

                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    } 
            
    /**
     * Funktion för att ladda in bank
     */
    private void loadBank()
    {        
        JFileChooser fileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
        fileChooser.setDialogTitle("Load Bank");
        fileChooser.setFileFilter(new FileNameExtensionFilter("dat files (*.dat)", "dat"));
        int response = fileChooser.showOpenDialog(null);

        if (response == JFileChooser.APPROVE_OPTION)
        {
            try 
            {
                FileInputStream file = new FileInputStream(fileChooser.getSelectedFile());
                ObjectInputStream input = new ObjectInputStream(file);
                logic = (BankLogic) input.readObject();

                file.close();
                input.close();
            } 
            
            catch (FileNotFoundException e)
            {
                showMessageDialog("The file you are trying to open does not exist");
            } 

            catch(StreamCorruptedException e)
            {
                showMessageDialog("The file you are trying to open is in wrong format");
            }
            
            catch (IOException e) {
                e.printStackTrace();
            } 
            
            catch (ClassNotFoundException e)
            {
                e.printStackTrace(); 
            } 
        }

    }
    

    @Override
    public void actionPerformed(ActionEvent e) 
    {
       
    }
    
}
