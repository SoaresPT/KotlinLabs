package lab4

import kotlin.math.abs
import kotlin.math.sign

class Fraction(numerator: Int, denominator: Int, private var sign: Int = 1) : Comparable<Fraction> {
    private val numerator: Int
    private val denominator: Int

    init {
        require(denominator != 0) { "Denominator can't be zero." }

        // Handle negative signs in the denominator and numerator
        var num = numerator
        var denom = denominator
        if (denom < 0) {
            num = -num
            denom = -denom
        }

        val gcd = gcd(abs(num), abs(denom))
        this.numerator = abs(num) / gcd
        this.denominator = abs(denom) / gcd
        this.sign *= num.sign

        // Edge case where the numerator is zero; make sure the fraction is positive
        if (this.numerator == 0) {
            this.sign = 1
        }
    }

    private fun gcd(a: Int, b: Int): Int {
        return if (b == 0) a else gcd(b, a % b)
    }

    override fun compareTo(other: Fraction): Int {
        val thisFraction = this.numerator * other.denominator * this.sign
        val otherFraction = other.numerator * this.denominator * other.sign
        return thisFraction.compareTo(otherFraction)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Fraction) return false
        return numerator == other.numerator && denominator == other.denominator && sign == other.sign
    }

    override fun hashCode(): Int {
        var result = numerator
        result = 31 * result + denominator
        result = 31 * result + sign
        return result
    }

    operator fun unaryMinus(): Fraction = Fraction(numerator, denominator, -sign)
    operator fun plus(other: Fraction): Fraction = this.add(other)
    operator fun times(other: Fraction): Fraction = this.mult(other)
    operator fun minus(other: Fraction): Fraction = this.add(-other)

    fun add(other: Fraction): Fraction {
        val commonDenominator = this.denominator * other.denominator
        val newNumerator = this.sign * this.numerator * other.denominator + other.sign * other.numerator * this.denominator
        return Fraction(newNumerator, commonDenominator)
    }

    fun mult(other: Fraction): Fraction {
        return Fraction(this.numerator * other.numerator, this.denominator * other.denominator, this.sign * other.sign)
    }

    fun div(other: Fraction): Fraction {
        return Fraction(this.numerator * other.denominator, this.denominator * other.numerator, this.sign * other.sign)
    }

    fun negate(): Fraction {
        return Fraction(this.numerator, this.denominator, -this.sign)
    }

    override fun toString(): String {
        val num = if (sign < 0) -numerator else numerator
        return "$num/$denominator"
    }
}

fun main() {
    val a = Fraction(1, 2, -1)
    println(a) // -1/2
    println(a.add(Fraction(1, 3))) // -1/6
    println(a.mult(Fraction(5, 2, -1))) // 5/4
    println(a.div(Fraction(2, 1))) // 1/4
    println(-Fraction(1, 6) + Fraction(1, 2)) // 1/3
    println(Fraction(2, 3) * Fraction(3, 2)) // 1/1
    println(Fraction(1, 2) > Fraction(2, 3)) // false
}