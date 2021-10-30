package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    /*fun rec(evaluation: Evaluation, secret: String, guess: String, i: Int): Evaluation {
        if (i == 4)
            return evaluation

        if (secret[i] == guess[i]) {
            rec(
                Evaluation(evaluation.rightPosition + 1, evaluation.wrongPosition),
                secret.substring(0, i) + ' ' + secret.substring(i + 1),
                guess,
                i + 1
            )
        } else {
            rec(
                Evaluation(evaluation.rightPosition + 1, evaluation.wrongPosition),
                secret.substring(0, i) + ' ' + secret.substring(i + 1),
                guess,
                i + 1
            )
        }
    }*/
//    val r = guess.withIndex().count { secret[it.index] == it.value }
//    val secret2 = guess.withIndex().map { if (secret[it.index] == it.value) ' ' else it.value }.joinToString(separator = "")
    var updatedSeret = secret
    var updatedGuess = guess
    var evaluation = Evaluation(0, 0)
    for (i in 0 until 4) {
        if (updatedSeret[i] == updatedGuess[i]) {
            evaluation = Evaluation(evaluation.rightPosition + 1, evaluation.wrongPosition)
            updatedSeret = updatedSeret.substring(0, i) + ' ' + updatedSeret.substring(i + 1)
            updatedGuess = updatedGuess.substring(0, i) + '_' + updatedGuess.substring(i + 1)
        }
    }
    for (i in 0 until 4) {
        val indexOf = updatedSeret.indexOf(updatedGuess[i])
        if (indexOf != -1) {
            evaluation = Evaluation(evaluation.rightPosition, evaluation.wrongPosition + 1)
            updatedSeret = updatedSeret.substring(0, indexOf) + ' ' + updatedSeret.substring(indexOf + 1)
            updatedGuess = updatedGuess.substring(0, i) + '_' + updatedGuess.substring(i + 1)
        }
    }

    return evaluation
}
