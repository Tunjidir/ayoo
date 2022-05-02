package com.tunjidir.ayoo.control

import com.tunjidir.ayoo.entity.*
import com.tunjidir.ayoo.entity.GameConstants.LEFT_PIT_HOUSE_ID
import com.tunjidir.ayoo.entity.GameConstants.RIGHT_PIT_HOUSE_ID
import com.tunjidir.ayoo.entity.GameConstants.TOTAL_PITS
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.IntStream

/**
 *
 * @author olatunji
 */

@Service
class Sower {

    fun sow(requestedPitId: Int, game: Game): Game {

        if (requestedPitId == RIGHT_PIT_HOUSE_ID || requestedPitId == LEFT_PIT_HOUSE_ID) return game

        if (requestedPitId < RIGHT_PIT_HOUSE_ID) game.playerTurn = PlayerTurn.PLAYER_ONE else game.playerTurn =
            PlayerTurn.PLAYER_TWO

        if (game.playerTurn === PlayerTurn.PLAYER_ONE && requestedPitId > RIGHT_PIT_HOUSE_ID ||
            game.playerTurn === PlayerTurn.PLAYER_TWO && requestedPitId < RIGHT_PIT_HOUSE_ID
        ) return game

        val selectedPit = game.getPit(requestedPitId)

        if (selectedPit.empty()) {
            return game
        }

        selectedPit.stones = 0
        game.currentPitIndex = requestedPitId

        IntStream.range(0, selectedPit.stones - 1)
            .forEach { sowRight(game, false) }
        sowRight(game, true)

        val currentPitIndex = game.currentPitIndex
        if (currentPitIndex != RIGHT_PIT_HOUSE_ID && currentPitIndex != LEFT_PIT_HOUSE_ID) game.playerTurn?.nextTurn()

        return game
    }

    fun validatePitIndex() {
        TODO()
    }

    fun sowRight(game: Game, lastStone: Boolean) {
        var currentPitIndex: Int = game.currentPitIndex!! % TOTAL_PITS + 1
        val playerTurn = game.playerTurn

        if (currentPitIndex == RIGHT_PIT_HOUSE_ID &&
            playerTurn === PlayerTurn.PLAYER_TWO ||
            currentPitIndex == LEFT_PIT_HOUSE_ID &&
            playerTurn === PlayerTurn.PLAYER_ONE
        ) {
            currentPitIndex = currentPitIndex % TOTAL_PITS + 1
        }

        game.currentPitIndex = currentPitIndex
        val targetPit = game.getPit(currentPitIndex)

        if (!lastStone ||
            currentPitIndex == RIGHT_PIT_HOUSE_ID ||
            currentPitIndex == LEFT_PIT_HOUSE_ID) targetPit.sow()

        val oppositePit = game.getPit(TOTAL_PITS - currentPitIndex)

        if (targetPit.empty() && !oppositePit.empty()) {
            val oppositeStones = oppositePit.stones
            oppositePit.clear()
            val pitHouseIndex = if (currentPitIndex < RIGHT_PIT_HOUSE_ID) RIGHT_PIT_HOUSE_ID else LEFT_PIT_HOUSE_ID
            val pitHouse = game.getPit(pitHouseIndex)
            pitHouse.addStones(oppositeStones + 1)
        }

        targetPit.sow()
    }
}

@Service
class Moderator {

    /**
     * Internal game holder
     */
    val custodian = mutableMapOf<String, Game>()

    /**
     * create a new game instance
     */
    fun createGame(pitStones: Int): String {
        val gameId = UUID.randomUUID().toString()
        custodian[gameId] = Game(gameId, pitStones)
        return gameId
    }

    /**
     * retrieve a particular game instance
     */
    fun loadGame(gameId: String) = custodian[gameId]

    /**
     * update an existing game if not existing
     */
    fun updateGame(gameId: String, updatedGame: Game) = custodian.getOrPut(gameId) {
        updatedGame
    }.gameId

}