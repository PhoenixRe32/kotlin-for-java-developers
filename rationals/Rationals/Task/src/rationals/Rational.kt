package rationals

import java.math.BigInteger
import java.math.BigInteger.ONE
import java.math.BigInteger.ZERO


class Rational(private val numerator: BigInteger, private val denominator: BigInteger) :
    Comparable<Rational> {

    constructor(numerator: BigInteger) : this(numerator, ONE)

    companion object {
        fun sync(left: Rational, right: Rational): Pair<Rational, Rational> {
            val leftEquivalent = Rational(
                left.numerator * right.denominator,
                left.denominator * right.denominator
            )
            val rightEquivalent = Rational(
                right.numerator * left.denominator,
                right.denominator * left.denominator
            )

            return leftEquivalent to rightEquivalent
        }
    }

    private fun normalise(): Rational {
        val gcd = gcd(numerator.abs(), denominator.abs())
        return if (denominator < ZERO) {
            Rational(-numerator / gcd, -denominator / gcd)
        } else {
            Rational(numerator / gcd, denominator / gcd)
        }
    }

    private fun gcd(n1: BigInteger, n2: BigInteger): BigInteger {
        return if (n2 == ZERO) {
            n1
        } else gcd(n2, n1 % n2)
    }

    operator fun plus(right: Rational): Rational {
        if (denominator == right.denominator)
            return plusWithSameDenominator(right)

        val (leftEquivalent, rightEquivalent) = sync(this.normalise(), right.normalise())

        return leftEquivalent + rightEquivalent;
    }

    private fun plusWithSameDenominator(right: Rational): Rational {
        return Rational(
            numerator + right.numerator,
            denominator
        )
    }

    operator fun minus(right: Rational): Rational {
        if (denominator == right.denominator)
            return minusWithSameDenominator(right)

        val (leftEquivalent, rightEquivalent) = sync(this.normalise(), right.normalise())

        return leftEquivalent - rightEquivalent;
    }

    private fun minusWithSameDenominator(right: Rational): Rational {
        return Rational(
            numerator - right.numerator,
            denominator
        )
    }

    operator fun times(right: Rational): Rational {
        return Rational(
            numerator * right.numerator,
            denominator * right.denominator

        )
    }

    operator fun div(right: Rational): Rational {
        return this * right.reversed()
    }

    private fun reversed() = Rational(denominator, numerator)

    operator fun unaryMinus(): Rational = Rational(-numerator, denominator)

    override fun compareTo(other: Rational): Int {
        val (leftEquivalent, rightEquivalent) = sync(this.normalise(), other.normalise())
        return leftEquivalent.numerator.compareTo(rightEquivalent.numerator)
    }

    override fun toString(): String {
        val normalised = normalise()
        val (numerator, denominator) = normalised.numerator to normalised.denominator
        return when (denominator != ONE) {
            true -> "${numerator}/${denominator}"
            false -> "$numerator"
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Rational

        val normalised = normalise()
        val (numerator, denominator) = normalised.numerator to normalised.denominator
        val otherNormalised = other.normalise()
        val (otherNumerator, otherDenominator) = otherNormalised.numerator to otherNormalised.denominator

        if (numerator != otherNumerator) return false
        if (denominator != otherDenominator) return false

        return true
    }

    override fun hashCode(): Int {
        var result = numerator.hashCode()
        result = 31 * result + denominator.hashCode()
        return result
    }


}

infix fun Int.divBy(i: Int): Rational = Rational(this.toBigInteger(), i.toBigInteger())
infix fun Long.divBy(i: Long): Rational = Rational(this.toBigInteger(), i.toBigInteger())
infix fun BigInteger.divBy(i: BigInteger): Rational = Rational(this, i)

fun String.toRational(): Rational {
    if ('/' !in this)
        return Rational(BigInteger(this))

    val (numerator, denomanator) = this.split('/')
    return Rational(BigInteger(numerator), BigInteger(denomanator))
}

fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println(
        "912016490186296920119201192141970416029".toBigInteger() divBy
                "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2
    )
}
