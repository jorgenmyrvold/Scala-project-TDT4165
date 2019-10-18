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


    // TASK 2.a


}