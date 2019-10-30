import java.util.concurrent.Callable
import java.util.concurrent.atomic.AtomicInteger

object Main extends App{
    // TASK 1.a
    var arr = Array.emptyIntArray
    for (i <- 1 to 50){
        arr = arr :+ i
    }
    println("\nTask 1.a: List from 1 to 50")
    println("= " + arr.mkString(","))

    // TASK 1.b
    def array_sum(arr: Array[Int]) : Int = {
        var running_total : Int = 0
        for (i <- arr.indices){
            running_total += arr(i)
        }
        running_total
    }
    println("\nTask 1.b: Sum of array using for loop")
    println("= " + array_sum(arr))

    // TASK 1.c
    def array_sum_rec(arr: Array[Int]) : Int = {
        if (arr.length == 0) {
            0
        } else {
            arr(0) + array_sum_rec(arr.slice(1, arr.length))
        }

    }
    println("\nTask 1.c: Sum of array using recursion")
    println("= " + array_sum_rec(arr))

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
    println("\nTask 1.d: Computing the nth Fibonacci number, n=10")
    println("= " + fib_num(10))
    
    /*
    BigInt has no practical limit. It allocates as much memory as needed. 
    In theory the size is limited to Integer.MAX_VALUE bits, but the 
    available recoursses on the machine sets the limit in pracis. Int has 
    a limitation on 32 bit which can generate values from -2^-31 to (2^31)-1
     */
    
    //TASK 2.a
    def returnThread(body: =>Unit): Thread = {
        val t = new Thread {
            override def run() = body
        }
        t 
    }


    /* TASK 2.c

    There are at least two ways to make the function thread safe. Using synchronized or AtomicInteger. 
    In the implementation Atomic integer is used, but the function head using 
    synchronized is showed in the comment below. */

    //def increaseCounter(): Unit = this.synchronized {  
    def increaseCounter(): Unit = {
        counter.addAndGet(1)
    }
    
    private var counter: AtomicInteger = new AtomicInteger(123)

    
    //TASK 2.b
    println("\nTask 2.b: Printing counter")
    def printCounter() : Unit = {
        val thrd1 = returnThread(increaseCounter())
        val thrd2 = returnThread(increaseCounter())
        val thrd3 = returnThread({println("=> Counter: " + counter)})

        thrd1.start
        thrd2.start
        thrd3.start
    }

    printCounter()
   

    /* What we are seeing is that the functions gets called in different order, and this 
    results in different results when printing the value. This phenomenon is called 
    inconsistent retrieval. To prevent this in this example it will not help to use 
    syncronized or atomicity, but we have to use join to make sure the two threads that 
    increment the value is finished before the value is printed. 

    A situation where it can be problematic is in bank. If two different accounts 
    are trying to make a transaction to the same account simultaneously, they both 
    add a new value to the same initial value. This can lead to some of the money 
    getting lost in transaction because the account balance isn't updated in-between 
    the two transactions. And one of them is transferring to the account with a wrong 
    assumption of what the current account balance is because it hasn't been updated.
    This is also called "lost update". */


    /* TASK 2.d

    Deadlock is a situation where two computer programs sharing the same resource are 
    preventing each other from accessing the resource. This results in both programs 
    ceasing to function. To prevent deadlock we can use one of the four conditions:
    1. Mutual Exclusion - Resources shared such as read-only files do not lead to
        deadlocks but resources, such as printers and tape drives, requires exclusive 
        access by a single process.
    2. Hold and Wait - Processes must be prevented from holding one or more resources 
        while simultaneously waiting for one or more others.
    3. No Preemption - Preemption of process resource allocations can avoid the condition 
        of deadlocks, where ever possible.
    4. Circular Wait - Circular wait can be avoided if we number all resources, and require 
        that processes request resources only in strictly increasing or decreasing order.
    */

    //Deadlock example 
    def deadlock() = {
        lazy val A : Int = B
        lazy val B : Int = A 
        val Ta = new Thread {println(A)}
        Ta.start()
    }

    //To activate the deadlock, remove '//' under 
    //deadlock()
    
}
