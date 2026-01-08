package fobb;
/**
 * CurrentAccount - A bank account subclass with overdraft facility
 * Extends Account class to provide business account features
 * 
 * @author Kang Jia Ying
 * @version 1.0
 */
public class CurrentAccount extends Account {        // Inherits from base class
    private double overdraftLimit;       // added 3 specific attributes
    private double monthlyFee;
    private boolean businessAccountFlag;
    
    // Constructor for subclass
    public CurrentAccount(String accountNumber, String accountHolder, double initialBalance, double overdraftLimit,
                         double monthlyFee, boolean businessAccountFlag) {
        super(accountNumber, accountHolder, initialBalance);      // Call to base class constructor
        this.overdraftLimit = overdraftLimit;                 // Initialize the specific attributes
        this.monthlyFee = monthlyFee;
        this.businessAccountFlag = businessAccountFlag;
    }
    
    // Getters and setters
    public double getOverdraftLimit() { 
        return overdraftLimit; 
    }

    public void setOverdraftLimit(double limit) { 
        if (limit >= 0) 
            overdraftLimit = limit; 
    }
    
    public double getMonthlyFee() { 
        return monthlyFee; 
    }

    public void setMonthlyFee(double fee) { 
        if (fee >= 0) 
            monthlyFee = fee; 
    }
    
    public boolean isBusinessAccount() { 
        return businessAccountFlag; 
    }

    public void setBusinessAccountFlag(boolean flag) { 
        businessAccountFlag = flag; 
    }
    
    
     //Override withdraw to allow overdraft
    @Override
    public boolean withdraw(double amount) {
        double available = getBalance() + overdraftLimit;    // To calculate total available money
        
        if (amount <= 0) {
            System.out.println("Error: Amount must be positive");
            return false;      // To prevent negative or zero withdrawals
        }
        
        if (amount <= available) {       // To check if withdrawal is within available funds
            double currentBalance = getBalance();      
            setInitialBalance(currentBalance - amount);        // Update balance after withdrawal
            
            if (getBalance() < 0) {       // Notify if account enters overdraft
                System.out.println("Note: Account is now in overdraft");
            }
            return true;       // Successful withdrawal
        } 

        else {
            System.out.println("Withdrawal failed: Exceeds available funds");
            return false;
        }
    }
    
    // Override interest calculation for current account
    @Override
    public void calculateInterest() {
        double balance = getBalance();         // Get current balance 
        
        if (balance > 0) {                  // if balance is +ve
            double interest = balance * 0.005;     // 0.5% annual interest
            System.out.println("Current account interest: $" + interest);
        } else if (balance < 0) {
            double charge = Math.abs(balance) * 0.02;      // 2% charge on overdraft amount
            System.out.println("Overdraft interest charge: $" + charge);
        }
    }
    
    // Apply monthly maintenance fee
    public void applyMonthlyFee() {
        double currentBalance = getBalance();        
        setInitialBalance(currentBalance - monthlyFee);          // Deduct monthly fee from balance
        System.out.println("Monthly fee applied: $" + monthlyFee);    
    }
    
    //Check current overdraft status
    public void checkOverdraft() {
        double balance = getBalance();
        
        if (balance < 0) {          // Show overdraft amount
            System.out.println("Account is in overdraft by: $" + Math.abs(balance));
        } else {                    // Show available overdraft limit
            System.out.println("Overdraft available: $" + overdraftLimit);
        }
    }
}