import java.util.concurrent.Callable

object Main extends App{
    // TASK 1.a
    var arr = Array.emptyIntArray
    for (i <- 0 to 50){
        arr = arr :+ i
    }
    //println(arr.mkString(","))

    // TASK 1.b
    def array_sum(arr: Array[Int]) : Int = {
        var running_total : Int = 0
        for (i <- arr.indices){
            running_total += arr(i)
        }
        running_total
    }
    println(array_sum(arr))

    // TASK 1.c
    def array_sum_rec(arr: Array[Int]) : Int = {
        if (arr.length == 0) {
            0
        } else {
            arr(0) + array_sum_rec(arr.slice(1, arr.length))
        }

    }
    // println(array_sum_rec(arr))

    // TASK 1.d
    def fib_num(n: Int) : BigInt = {
        if (n == 2) {
            1
        }
        else if (n == 1){
            0
        }
        else{
            fib_num(n-1) + fib_num(n-2)
        }
    }
    println(fib_num(10))
    /*
    BigInt has no limit practical limit. It allocates as much memory as needed. In theory the size is limited to
    Integer.MAX_VALUE bits, but the available recoursses on the machine sets the limit in pracis.
    Int has a limitation on 32 bit which can generate values from -2^-31 to (2^31)-1
     */
    
//Task 2a
  def returnThread(body: =>Unit): Thread = {
    val t = new Thread {
    override def run() = body
    }
      t 
  }

//def increaseCounter(): Unit = this.synchronized {
def increaseCounter(): Unit = {
  counter.addAndGet(1)
}


  private var counter: AtomicInteger = new AtomicInteger(123)

  val thrd1 = returnThread(increaseCounter())
  val thrd2 = returnThread(increaseCounter())
  val thrd3 = returnThread({println("Counter: " + counter)})

  thrd1.start
  thrd2.start
  thrd3.start

//This phenomenon is called...

/*
A situation where it can be problematic is in bank. 
If two different accounts are trying to make a transaction to the same account simultaneously,
they both add a new value to the same initial value. 
This can lead to some of the money getting lost in transaction because the account balance isn't updated in-between the two transactions. 
And one of them is transferring to the account with a wrong assumption of what the current account balance is because it hasn't been updated.
This is also called "lost update".
*/

//Deadlock is...
//To prevent deadlock we can...
/*lazy val A : Int = B
lazy val B : Int = A 
val Ta = new Thread {println(A)}
Ta.start()
*/



}
