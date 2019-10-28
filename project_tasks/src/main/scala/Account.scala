import exceptions._

class Account(val bank: Bank, initialBalance: Double) {

    class Balance(var amount: Double) {}

    val balance = new Balance(initialBalance)

    // TODO
    // for project task 1.2: implement functions
    // for project task 1.3: change return type and update function bodies
    
    //Task 1.2: Account functions
    //withdraw function: removes an amount of money from the account
    def withdraw(amount: Double): Either[Unit, String] = this.synchronized {
        if (balance.amount - amount >= 0.0 && amount > 0.0) {  //checks if withdraw amount is valid
            balance.amount -= amount 
            return Left() 
        }
        Right("Withdraw failed") 
    }

    //deposit function: inserts an amount of money to the account
    def deposit (amount: Double): Either[Unit, String] = this.synchronized  {
        if (amount > 0.0) { //checks if deposit amount is a valid number
            balance.amount += amount
            return Left()
        }
        Right("Deposit failed. Tried to deposit negative amount.")
    }

    //getBalanceAmount function: returns the amount of funds in the account.
    def getBalanceAmount: Double = this.synchronized{
        balance.amount
    }

    def transferTo(account: Account, amount: Double) = this.synchronized {
        bank addTransactionToQueue(this, account, amount)
    }

}




/* 
Example: 
val result = withdraw(5)
result match {
    case Right(string) => println(string)
    case Left(number) => println(number)
}
*/