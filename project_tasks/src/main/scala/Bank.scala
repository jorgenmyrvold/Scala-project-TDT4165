class Bank(val allowedAttempts: Integer = 3) {

    private var transactionsQueue: TransactionQueue = new TransactionQueue()
    private var processedTransactions: TransactionQueue = new TransactionQueue()


    // Create a Transaction object and adds it to the transaction queue.
    // Spawns a thread to process the transaction and starts it.

    def addTransactionToQueue(from: Account, to: Account, amount: Double): Unit = {
        val transaction = new Transaction(transactionsQueue,
                                          processedTransactions,
                                          from,
                                          to,
                                          amount,
                                          allowedAttempts)
        transactionsQueue.push(transaction)
        val thrd = new Thread{override def run() = processTransactions}  // spawn a thread that calls processTransactions
        thrd.start
    }


    // Processes the first transaction in the TransactionQueue. Tries to complete
    // the transaction by spawning a thread with transaction which does the transaction.

    private def processTransactions: Unit = {
        val transaction = transactionsQueue.pop     
        val thrd = new Thread(transaction)    // Spawns a thread calling run from transaction which contains doTransaction.
        thrd.start
        thrd.join
        if (transaction.status == TransactionStatus.PENDING){  // If the transaction is not finished
            transactionsQueue.push(transaction)                // it is pushed back onto the queue
            processTransactions                                // and processed again.
        }
        else {                                          // If it is finished
            processedTransactions.push(transaction)     // it is pushed onto processed transaction.
        }
    }

    // Creates a new account in this bank with initial balance
    def addAccount(initialBalance: Double): Account = {
        new Account(this, initialBalance)
    }

    def getProcessedTransactionsAsList: List[Transaction] = {
        processedTransactions.iterator.toList
    }

}
