
class Account(val bank: Bank, initialBalance: Double) {

    class Balance(var amount: Double) {}

    val balance = new Balance(initialBalance)

    // Task 1.2: Account functions
    // withdraw function: removes an amount of money from the account
    // Returns nothing if success or a string if it fails
    def withdraw(amount: Double): Either[Unit, String] = this.synchronized {
        if (balance.amount - amount >= 0.0 && amount > 0.0) {  //checks if withdraw amount is valid
            balance.amount -= amount 
            return Left() 
        }
        Right("Withdraw failed") 
    }

    // deposit function: inserts an amount of money to the account
    // Returns nothing if success or a string if it fails
    def deposit (amount: Double): Either[Unit, String] = this.synchronized  {
        if (amount > 0.0) { //checks if deposit amount is a valid number
            balance.amount += amount
            return Left()
        }
        Right("Deposit failed. Tried to deposit negative amount.")
    }

    //getBalanceAmount function: returns the amount of funds in the account.
    def getBalanceAmount: Double = {
        balance.amount
    }

    // Transfer money from this account to another.
    def transferTo(account: Account, amount: Double) = this.synchronized {
        bank addTransactionToQueue(this, account, amount)
    }

}
