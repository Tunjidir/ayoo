package com.tunjidir.ayoo.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.tunjidir.ayoo.entity.GameConstants.FIFTH_PIT_PLAYER_A
import com.tunjidir.ayoo.entity.GameConstants.FIFTH_PIT_PLAYER_B
import com.tunjidir.ayoo.entity.GameConstants.FIRST_PIT_PLAYER_A
import com.tunjidir.ayoo.entity.GameConstants.FIRST_PIT_PLAYER_B
import com.tunjidir.ayoo.entity.GameConstants.FOURTH_PIT_PLAYER_A
import com.tunjidir.ayoo.entity.GameConstants.FOURTH_PIT_PLAYER_B
import com.tunjidir.ayoo.entity.GameConstants.LEFT_PIT_HOUSE_ID
import com.tunjidir.ayoo.entity.GameConstants.RIGHT_PIT_HOUSE_ID
import com.tunjidir.ayoo.entity.GameConstants.SECOND_PIT_PLAYER_A
import com.tunjidir.ayoo.entity.GameConstants.SECOND_PIT_PLAYER_B
import com.tunjidir.ayoo.entity.GameConstants.SIXTH_PIT_PLAYER_A
import com.tunjidir.ayoo.entity.GameConstants.SIXTH_PIT_PLAYER_B
import com.tunjidir.ayoo.entity.GameConstants.THIRD_PIT_PLAYER_A
import com.tunjidir.ayoo.entity.GameConstants.THIRD_PIT_PLAYER_B

data class Game(
    val gameId: String,
    val pitStones: Int,
    val pits: MutableList<Pit> = mutableListOf(),
    var playerTurn: PlayerTurn? = null,
    @JsonIgnore
    var currentPitIndex: Int? = null
) {

    init {
        val initPits = listOf(
            Pit(FIRST_PIT_PLAYER_A, pitStones),
            Pit(SECOND_PIT_PLAYER_A, pitStones),
            Pit(THIRD_PIT_PLAYER_A, pitStones),
            Pit(FOURTH_PIT_PLAYER_A, pitStones),
            Pit(FIFTH_PIT_PLAYER_A, pitStones),
            Pit(SIXTH_PIT_PLAYER_A, pitStones),
            Pit(RIGHT_PIT_HOUSE_ID, 0),
            Pit(FIRST_PIT_PLAYER_B, pitStones),
            Pit(SECOND_PIT_PLAYER_B, pitStones),
            Pit(THIRD_PIT_PLAYER_B, pitStones),
            Pit(FOURTH_PIT_PLAYER_B, pitStones),
            Pit(FIFTH_PIT_PLAYER_B, pitStones),
            Pit(SIXTH_PIT_PLAYER_B, pitStones),
            Pit(LEFT_PIT_HOUSE_ID, 0)
        )
        pits.addAll(initPits)
    }

    fun getPit(pitIndex: Int) = pits[pitIndex - 1]
}

class Pit(val pitNumber: Int, initialStones: Int) : House(initialStones)

enum class PlayerTurn {
    PLAYER_ONE, PLAYER_TWO;
}

abstract class House(var stones: Int) {

    /**
     * adds one extra stone to a pit via the process of sowing
     */
    fun sow() = stones++

    /**
     * add stones into the pit
     */
    fun addStones(stones: Int) = stones.plus(stones)

    fun clear() {
        stones = 0
    }

}

fun Pit.empty() = stones == 0

fun PlayerTurn.nextTurn() =
    if (this.equals(PlayerTurn.PLAYER_ONE)) PlayerTurn.PLAYER_ONE else PlayerTurn.PLAYER_TWO

object GameConstants {
    const val LEFT_PIT_HOUSE_ID = 14
    const val TOTAL_PITS = 14
    const val RIGHT_PIT_HOUSE_ID = 7

    const val FIRST_PIT_PLAYER_A = 1
    const val SECOND_PIT_PLAYER_A = 2
    const val THIRD_PIT_PLAYER_A = 3
    const val FOURTH_PIT_PLAYER_A = 4
    const val FIFTH_PIT_PLAYER_A = 5
    const val SIXTH_PIT_PLAYER_A = 6

    const val FIRST_PIT_PLAYER_B = 8
    const val SECOND_PIT_PLAYER_B = 9
    const val THIRD_PIT_PLAYER_B = 10
    const val FOURTH_PIT_PLAYER_B = 11
    const val FIFTH_PIT_PLAYER_B = 12
    const val SIXTH_PIT_PLAYER_B = 13
}