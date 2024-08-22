package lab2

import kotlin.math.abs

class FractionMutable(numerator: Int, denominator: Int, private var sign: Int = 1) {
    private var numerator: Int = if (numerator < 0) -numerator else numerator
    private var denominator: Int = if (denominator < 0) -denominator else denominator

    init {
        if (numerator < 0) this.sign = -this.sign
        if (denominator < 0) this.sign = -this.sign
        reduce()
    }

    fun negate() {
        this.sign = -this.sign
    }

    fun add(other: FractionMutable) {
        val commonDenominator = this.denominator * other.denominator
        val adjustedNumerator1 = this.numerator * other.denominator * this.sign
        val adjustedNumerator2 = other.numerator * this.denominator * other.sign
        val resultNumerator = adjustedNumerator1 + adjustedNumerator2
        this.numerator = abs(resultNumerator)
        this.denominator = commonDenominator
        this.sign = if (resultNumerator >= 0) 1 else -1
        reduce()
    }

    fun mult(other: FractionMutable) {
        this.numerator *= other.numerator
        this.denominator *= other.denominator
        this.sign *= other.sign
        reduce()
    }

    fun div(other: FractionMutable) {
        this.numerator *= other.denominator
        this.denominator *= other.numerator
        this.sign *= other.sign
        reduce()
    }

    fun intPart(): Int {
        return (numerator * sign) / denominator
    }

    private fun reduce() {
        val gcd = gcd(numerator, denominator)
        numerator /= gcd
        denominator /= gcd
    }

    private fun gcd(a: Int, b: Int): Int {
        if (b == 0) return a
        return gcd(b, a % b)
    }

    override fun toString(): String {
        return if (sign == -1) "-$numerator/$denominator" else "$numerator/$denominator"
    }
}

// Main function to demonstrate functionality
fun main() {
    val a = FractionMutable(1,2,-1)
    a.add(FractionMutable(1,3))
    println(a)
    a.mult(FractionMutable(5,2, -1))
    println(a)
    a.div(FractionMutable(2,1))
    println(a)
}