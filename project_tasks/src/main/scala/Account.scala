import exceptions._

class Account(val bank: Bank, initialBalance: Double) {

    class Balance(var amount: Double) {}

    val balance = new Balance(initialBalance)

    // TODO
    // for project task 1.2: implement functions
    // for project task 1.3: change return type and update function bodies
    def withdraw(amount: Double): Unit = {
        if (balance - amount < 0) {
            println("Error: Funds insufficient")
        } else {
            (balance -= amount)
        }
    }

    def deposit (amount: Double): Unit = {
        balance += amount
    }


    def getBalanceAmount: Double = {
        balance
    }

    def transferTo(account: Account, amount: Double) = {
        bank addTransactionToQueue (this, account, amount)
    }

}
