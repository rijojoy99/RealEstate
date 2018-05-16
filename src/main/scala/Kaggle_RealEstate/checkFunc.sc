val fibs: Stream[Int] = 0 #:: fibs.scanLeft(1)(_ + _)
fib(20)
val fibOutput = fibs take 20 toList
val x = true

fibOutput.foreach(println)
val y = true
val loop = loop
val TorF = or(false,loop)

def fib(n: Int): Int = {
  def fibIter(i: Int, a: Int, b: Int): Int =
    if (i == n) a else {
      println(b); fibIter(i + 1, b, a + b)
    }

  fibIter(0, 0, 1)
}

def or(x: Boolean, y: => Boolean) = if ( x == y) x else if(y) true else x
