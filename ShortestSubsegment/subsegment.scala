def trimWords(lines: List[String]) = lines.flatMap { line =>
   "[a-zA-Z]+".r.findAllIn(line).map(_.toLowerCase)
}

def permute(arr: Array[Int], num: Int, max: Int, pos: Int) : Array[Int] = {
   val quotient = num / max
   val remainder = num % max

   arr(pos) = remainder
   if (quotient < max) { arr(pos+1) = quotient }
   if (quotient >= max) { permute(arr, quotient, max, pos+1) }
   return arr
}

//-------------------------------
// subsegment will take an input.txt -> matrix of indexes of occurances
// matrix -> permutate all combos to -> results -> filter to -> answers
// Written by: Kyle Dinh @ https://github.com/kyledinh/kodex/tree/master/coding-problems
// scala 2.10.1
//-------------------------------

val file = io.Source.fromFile("input.txt","utf-8")
val lines = file.getLines.toList
val depth = lines(0).toInt
val keywordArr = lines.slice(1, depth+1)
val segmentArr = trimWords(lines(depth +1).split(" ").toList)
val breath = keywordArr.map(t => segmentArr.count(_ == t)).max
val matrix = Array.fill(depth, breath)(-1)

println("Keywords: " + keywordArr.mkString(" "))
println("depth " + depth + ", breath " + breath)
println("segmentArr length: " + segmentArr.length)

for { x <- 0 to depth-1 } {
   var y = 0;

   // fill the array with -1 as initial value
   // for { k <- 0 to breath-1 } { matrix(x)(k) = -1 }

   for { i <- 0 to segmentArr.length-1 } {
      if (segmentArr(i) == keywordArr(x)) {
         println("Found @ " + i + " " + segmentArr(i) + " for " + keywordArr(x))
         matrix(x)(y) = i
         y = y+1
      }
   }
}

for { x <- 0 to depth-1 } {
   println(x + " row of matrix " + matrix(x).deep.mkString(" "))
}

/*
matrix(0) 0, 4, 9
      (1) 2, 6, 11
      (2) 3, 8, 13
      (3) 7, 12

combos:
0,2,3,7
4,2,3,7
9,2,3,7
...
*/

// Now, permute through the matrix for each combonation; a num from each row(depth)
val permutationArr = Array.fill(depth)(0)
var results = List[Array[Int]]()

for { x <- 0 to math.pow(breath, depth).toInt -1 } {
   var arr = Array.fill(depth)(-1)
   val combo = permute(permutationArr, x, breath, 0)

   for { i <- 0 to arr.length-1 } {
      arr(i) = matrix(i)(combo(i))
   }
   val distance = arr.max - arr.min
   arr = arr :+ distance
   println(arr.mkString(" "))

   // reject combos with a -1 from results; incomplete combos
   if (!arr.contains(-1)) { results = results :+ arr }
}

println("Number of possible combos: " + results.length)

if (results.length == 0) {
   println("NO SUBSEGMENT FOUND")
} else {
   val answers = results.sortWith(_.last < _.last)
   val min_num = answers.head.last
   def printList(arr: Array[Int]): Unit = { println(arr.mkString(" "))}
   println("Your answer(s) for shortest substring with the length of : " + min_num)
   answers.filter(_.last == min_num).foreach(printList)
}
