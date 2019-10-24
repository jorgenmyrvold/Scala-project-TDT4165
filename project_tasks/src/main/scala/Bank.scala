package runtime
// import transaction.Transaction
// import transaction.TransactionQueue
// import transactionStatus.TransactionStatus

class Bank(val allowedAttempts: Integer = 3) {

    private val transactionsQueue: TransactionQueue = new TransactionQueue()
    private val processedTransactions: TransactionQueue = new TransactionQueue()

    def addTransactionToQueue(from: Account, to: Account, amount: Double): Unit = this.synchronized {
        val transaction = new Transaction(transactionsQueue,        // create a new transaction object
                                      processedTransactions,
                                      from,
                                      to,
                                      amount,
                                      allowedAttemps)
        transactionsQueue.push(transaction)                     // and put it in the queue
        val thrd = new Thread{processTransactions}                  // spawn a thread that calls processTransactions
    }

    private def processTransactions: Unit = this.synchronized {
        val transaction = transactionsQueue.pop                     // Function that pops a transaction from the queue
        val thrd = new Thread{
            if (transaction.status == TransactionStatus.SUCCESS || transaction.status == TransactionStatus.FAILED) {
                processTransactions.push(transaction)
            }
        }
    }
        // TOO
        // project task 2
        // and spawns a thread to execute the transaction.
        // Finally do the appropriate thing, depending on whether
        // the transaction succeeded or not

    def addAccount(initialBalance: Double): Account = this.synchronized {
        new Account(this, initialBalance)
    }

    def getProcessedTransactionsAsList: List[Transaction] = this.synchronized {
        processedTransactions.iterator.toList
    }

}
