package lab3

import java.util.*

class Lotto (val lottoRange: IntRange, val n: Int, val secretNumbers: List<Int>) {

    fun pickNDistinct(range: IntRange, n: Int): List<Int> { // returns a list with n distinct ints from range
        return range.shuffled().take(n)
    }

    fun numDistinct(list: List<Int>): Int{ // return the number of distinct ints in list
        return list.distinct().size
    }

    fun numCommon(list1: List<Int>, list2: List<Int>): Int { // return the number of ints in both list1 and list2
        return list1.intersect(list2.toSet()).size
    }

    fun isLegalLottoGuess(guess: List<Int>, range: IntRange = lottoRange, count: Int = n): Boolean { // is guess legal? (consists of n different ints from range)
        return guess.intersect(range).size == count
    }

    fun checkGuess(guess: List<Int>, secret: List<Int> = secretNumbers): Int { // if guess is legal return the number of ints in guess that also appear in secret, otherwise 0
        if (isLegalLottoGuess(guess)) {
            return numCommon(guess, secret)
        }
        return 0
    }
}

fun readNDistinct(low: Int, high: Int, n: Int): List<Int> {
    val list = mutableListOf<Int>()

    while (list.size < n) {
        print("Give $n numbers from $low to $high, separated by commas: ")

        val input = readlnOrNull()?.split(",")?.mapNotNull { it.trim().toIntOrNull() }

        if (input != null && input.size == n) {
            // Check if all numbers are within the range and distinct
            if (input.all { it in low..high } && input.distinct().size == n) {
                // Add valid input to the list
                list.clear() // Clear existing list before adding new valid input
                list.addAll(input)
            } else {
                println("Input numbers should be distinct and within the specified range.")
            }
        } else {
            println("Please enter exactly $n numbers.")
        }
    }

    return list
}

fun playLotto() {
    var more = "Y"

    while (more.lowercase(Locale.getDefault()) == "y") {
        val secretNumbers = mutableListOf<Int>()
        while (secretNumbers.size < 7) {
            val x = (1..40).random()
            if (!secretNumbers.contains(x)) {
                secretNumbers.add(x)
            }
        }
        val userGuess = readNDistinct(1, 40, 7)
        val correctGuesses = userGuess.intersect(secretNumbers.toSet())

        println("Lotto Numbers: ${secretNumbers.sorted()}, you got ${correctGuesses.size} correct.")
        // Part C
        val lotto = Lotto(1..40,7,secretNumbers) // construct the object, so we can call the findLotto function here
        println("Computer guess in ${findLotto(lotto).first} steps is ${findLotto(lotto).second}")
        do {
            print("More? (Y/N): ")
            more = readlnOrNull()?.lowercase(Locale.getDefault()) ?: "N".lowercase(Locale.getDefault())
        } while (more != "Y".lowercase(Locale.getDefault()) && more != "N".lowercase(Locale.getDefault()))
    }
}

fun findLotto(lotto: Lotto): Pair<Int, List<Int>> {
    var attempts = 0
    var guess: List<Int>

    val correctNumbers = mutableListOf<Int>()

    do {
        // Generate a new guess
        guess = lotto.pickNDistinct(lotto.lottoRange, lotto.n)
        attempts++
        //println("Guess: $guess")
        //println("Right numbers found: ${guess.intersect(lotto.secretNumbers.toSet())}")
        val intersectedNumbers = guess.intersect(lotto.secretNumbers.toSet()).toList()
        intersectedNumbers.forEach {
            if (!correctNumbers.contains(it)) {
                correctNumbers.add(it)
            }
        }

        //println("Correct lotto numbers : ${lotto.secretNumbers.sorted()}")

    } while (correctNumbers.size < lotto.n)

    return Pair(attempts, guess.sorted())
}

fun main() {
    // Part A - Class Showcase
    val b = Lotto(1..40, 7, listOf(3,4,6,7,10,15,35))
    println("5 numbers between 1-10: ${b.pickNDistinct(1..10, 5)}" )
    println("Number of distinct numbers in a list: ${b.numDistinct(listOf(1, 2, 3, 4, 5, 6, 7, 7, 7, 7, 7))}")
    println("Numbers in common between 2 lists ${b.numCommon(listOf(1, 2, 6), listOf(1, 2, 3, 4, 5, 6, 7, 7, 7, 7))}")
    println("Is Legal Lotto Guess?: ${b.isLegalLottoGuess(listOf(1,2,3,5,6,9,10,8))}") // fails because 8 total
    println("Is Legal Lotto Guess?: ${b.isLegalLottoGuess(listOf(1,2,3,5,6,9,10))}") // should pass since 7 distincts
    println("How many correct numbers using checkGuess function: ${b.checkGuess(listOf(1, 2, 3, 4, 5, 6, 7))}")

    println()
    // Part B/C
    playLotto()
}
