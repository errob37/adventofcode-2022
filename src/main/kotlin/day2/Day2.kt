package day2

import ResourceFileReader.Companion.readAocFile

class Day2 {
    companion object {
        fun problem1() = getMyScore(IAlwaysWinEngine())
        fun problem2() = getMyScore(KnowResultEngine())
        private fun getMyScore(engine: RockPaperScissorEngine) =
            getRockPaperScissorGames().map { it.play(engine) }.sumOf { it.me }

        private fun getRockPaperScissorGames(): List<RockPaperScissorGame> {
            return readAocFile(2)
                .split("\n")
                .filter { it.isNotBlank() }
                .map {
                    val rawGame = it.split(" ")
                    RockPaperScissorGame(rawGame[0], rawGame[1])
                }
        }
    }
}

class RoundScore(val opponent: Long, val me: Long)
enum class RoundResult {
    WIN, DRAW, LOOSE;

    companion object
}

enum class RockPaperScissorChoice {
    ROCK, PAPER, SCISSOR;

    companion object
}

class RockPaperScissorGame(private val opponent: String, private val me: String) {
    fun play(engine: RockPaperScissorEngine): RoundScore = engine.scoreRound(opponent, me)
}
