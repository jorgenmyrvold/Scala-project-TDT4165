class Bank(val allowedAttempts: Integer = 3) {

    private var transactionsQueue: TransactionQueue = new TransactionQueue()
    private var processedTransactions: TransactionQueue = new TransactionQueue()

    def addTransactionToQueue(from: Account, to: Account, amount: Double): Unit = this.synchronized {
        val transaction = new Transaction(transactionsQueue,        // create a new transaction object
                                          processedTransactions,
                                          from,
                                          to,
                                          amount,
                                          allowedAttempts)
        transactionsQueue.push(transaction)                         // and put it in the queue
        val thrd = new Thread{processTransactions}                  // spawn a thread that calls processTransactions
        thrd.start
    }

    private def processTransactions: Unit = this.synchronized {
        val transaction = transactionsQueue.pop                     
        val thrd = new Thread{
            transaction.run
            if (transaction.status == TransactionStatus.PENDING){
                transactionsQueue.push(transaction)
                processTransactions
            }
            else {
                processedTransactions.push(transaction)
            }
        }
        thrd.start
    }
        // TOO
        // project task 2
        // Function that pops a transaction from the queue
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
