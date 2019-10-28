import exceptions._

class Account(val bank: Bank, initialBalance: Double) {

    class Balance(var amount: Double) {}

    val balance = new Balance(initialBalance)

    // TODO
    // for project task 1.2: implement functions
    // for project task 1.3: change return type and update function bodies
    def withdraw(amount: Double): Either[Unit, String] = this.synchronized {
        if (balance.amount - amount >= 0.0 && amount > 0.0) {
            balance.amount -= amount
            return Left()
        }
        Right("Withdraw failed")
    }

    def deposit (amount: Double): Either[Unit, String] = this.synchronized  {
        if (amount > 0.0) {
            balance.amount += amount
            return Left()
        }
        Right("Deposit failed. Tried to deposit negative amount.")
    }

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