package day2

abstract class RockPaperScissorEngine {
    abstract fun scoreRound(code1: String, code2: String): RoundScore
}

class KnowResultEngine : RockPaperScissorEngine() {
    override fun scoreRound(code1: String, code2: String): RoundScore {
        val expectedResultForMe = RoundResult.fromCode(code2)
        val expectedResultForOpponent = expectedResultForMe.invert()

        val opponentChoice = RockPaperScissorChoice.fromCode(code1)
        val meChoice = opponentChoice.chooseToHaveRoundResult(expectedResultForOpponent)

        val opponentScore = opponentChoice.score()
        val meScore = meChoice.score()

        val opponentRoundResult = expectedResultForOpponent.score()
        val meRoundResult = expectedResultForMe.score()

        return RoundScore(opponentScore + opponentRoundResult, meScore + meRoundResult)
    }

    private fun RockPaperScissorChoice.chooseToHaveRoundResult(expectedResult: RoundResult): RockPaperScissorChoice {
        return when (expectedResult) {
            RoundResult.DRAW -> this

            RoundResult.WIN -> when (this) {
                RockPaperScissorChoice.ROCK -> RockPaperScissorChoice.SCISSOR
                RockPaperScissorChoice.PAPER -> RockPaperScissorChoice.ROCK
                RockPaperScissorChoice.SCISSOR -> RockPaperScissorChoice.PAPER
            }

            RoundResult.LOOSE -> when (this) {
                RockPaperScissorChoice.ROCK -> RockPaperScissorChoice.PAPER
                RockPaperScissorChoice.PAPER -> RockPaperScissorChoice.SCISSOR
                RockPaperScissorChoice.SCISSOR -> RockPaperScissorChoice.ROCK
            }
        }
    }

    private fun RoundResult.Companion.fromCode(code: String): RoundResult {
        return when (code) {
            "X" -> RoundResult.LOOSE
            "Y" -> RoundResult.DRAW
            "Z" -> RoundResult.WIN
            else -> throw IllegalArgumentException()
        }
    }

    private fun RoundResult.invert(): RoundResult {
        return when (this) {
            RoundResult.LOOSE -> RoundResult.WIN
            RoundResult.DRAW -> RoundResult.DRAW
            RoundResult.WIN -> RoundResult.LOOSE
        }
    }
}


class IAlwaysWinEngine : RockPaperScissorEngine() {
    override fun scoreRound(code1: String, code2: String): RoundScore {
        val opponentChoice = RockPaperScissorChoice.fromCode(code1)
        val meChoice = RockPaperScissorChoice.fromCode(code2)

        val opponentScore = opponentChoice.score()
        val meScore = meChoice.score()

        val opponentRoundResult = opponentChoice.battle(meChoice).score()
        val meRoundResult = meChoice.battle(opponentChoice).score()

        return RoundScore(opponentScore + opponentRoundResult, meScore + meRoundResult)
    }


    private fun RockPaperScissorChoice.battle(choice: RockPaperScissorChoice): RoundResult {
        return when {
            this == choice -> RoundResult.DRAW
            this == RockPaperScissorChoice.PAPER && choice == RockPaperScissorChoice.ROCK -> RoundResult.WIN
            this == RockPaperScissorChoice.ROCK && choice == RockPaperScissorChoice.SCISSOR -> RoundResult.WIN
            this == RockPaperScissorChoice.SCISSOR && choice == RockPaperScissorChoice.PAPER -> RoundResult.WIN
            else -> RoundResult.LOOSE
        }
    }
}

private fun RoundResult.score(): Long {
    return when (this) {
        RoundResult.WIN -> 6
        RoundResult.DRAW -> 3
        RoundResult.LOOSE -> 0
    }
}

private fun RockPaperScissorChoice.score(): Long {
    return when (this) {
        RockPaperScissorChoice.ROCK -> 1L
        RockPaperScissorChoice.PAPER -> 2L
        RockPaperScissorChoice.SCISSOR -> 3L
    }
}

private fun RockPaperScissorChoice.Companion.fromCode(code: String): RockPaperScissorChoice {
    return when (code) {
        "A", "X" -> RockPaperScissorChoice.ROCK
        "B", "Y" -> RockPaperScissorChoice.PAPER
        "C", "Z" -> RockPaperScissorChoice.SCISSOR
        else -> throw IllegalArgumentException()
    }
}


