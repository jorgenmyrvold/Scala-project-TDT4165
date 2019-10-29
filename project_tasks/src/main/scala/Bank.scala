class Bank(val allowedAttempts: Integer = 3) {

    private var transactionsQueue: TransactionQueue = new TransactionQueue()
    private var processedTransactions: TransactionQueue = new TransactionQueue()

    // Task 2: Creating the bank
    def addTransactionToQueue(from: Account, to: Account, amount: Double): Unit = {
        val transaction = new Transaction(transactionsQueue,        // create a new transaction object
                                          processedTransactions,
                                          from,
                                          to,
                                          amount,
                                          allowedAttempts)
        transactionsQueue.push(transaction)                               // and put it in the queue
        val thrd = new Thread{override def run() = processTransactions}   // spawn a thread that calls processTransactions
        thrd.start
    }

    private def processTransactions: Unit = {
        val transaction = transactionsQueue.pop     
        val thrd = new Thread(transaction)
        thrd.start
        thrd.join
        if (transaction.status == TransactionStatus.PENDING){
            transactionsQueue.push(transaction)
            processTransactions
        }
        else {
            processedTransactions.push(transaction)
        }
    }
    
        // Function that pops a transaction from the queue
        // and spawns a thread to execute the transaction.
        // Finally do the appropriate thing, depending on whether
        // the transaction succeeded or not

    def addAccount(initialBalance: Double): Account = {
        new Account(this, initialBalance)
    }

    def getProcessedTransactionsAsList: List[Transaction] = {
        processedTransactions.iterator.toList
    }

}
