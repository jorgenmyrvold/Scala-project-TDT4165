import exceptions._
import scala.collection.mutable.Queue


object TransactionStatus extends Enumeration {
    val SUCCESS, PENDING, FAILED = Value
}

class TransactionQueue {
    // Task 1.1: Add datastructure to contain the transactions

    //Datastructure to hold transctions
    var TransQueue = Queue.empty[Transaction]

    // Remove and return the first element from the queue
    def pop: Transaction = this.synchronized {
        TransQueue.dequeue
    }

    // Return whether the queue is empty
    def isEmpty: Boolean = {
        !TransQueue.nonEmpty 
    }

    // Add new element to the back of the queue
    def push(t: Transaction): Unit = this.synchronized {
        TransQueue.enqueue(t)
    }

    // Return the first element from the queue without removing it
    def peek: Transaction = {
        TransQueue.head
    }

    // Return an iterator to allow you to iterate over the queue
    def iterator: Iterator[Transaction] = {
        TransQueue.iterator

    }
}

class Transaction(val transactionsQueue: TransactionQueue,
                  val processedTransactions: TransactionQueue,
                  val from: Account,
                  val to: Account,
                  val amount: Double,
                  val allowedAttempts: Int) extends Runnable {

    var status: TransactionStatus.Value = TransactionStatus.PENDING
    var attempt = 0

    override def run: Unit = {

        // doTransaction performes the transaction. Transfers money and handles 
        // errors if they occur. doTransaction is syncronized which means that
        // no other function can edit the transaction while it is beeing processed

        def doTransaction() = this.synchronized {
            if (this.attempt < this.allowedAttempts) {
                val resultWithdraw = this.from.withdraw(amount)
                resultWithdraw match {
                    case Right(error) => {this.attempt += 1}        // If withdraw fails, increment attempts
                    case Left(success) => {this.to.deposit(amount)  // If withdraw succeeds so will deposit. The condition for deposit to succeed is checked for in withdraw.
                                           this.status = TransactionStatus.SUCCESS}
                }
            }
            else {                                      // When a transaction has tries more than allowed attempts
                this.status = TransactionStatus.FAILED  // it's status is set to FAILED.
            }
        }

        // While the transaction is not finished it tries again to process it. 
        // It tries as long as it has tries less then allowedAttempts
        if (status == TransactionStatus.PENDING) { 
            doTransaction
            Thread.sleep(50)
        }
    }
}
