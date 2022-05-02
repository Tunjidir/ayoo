package com.tunjidir.ayoo.boundary

import com.tunjidir.ayoo.control.Moderator
import com.tunjidir.ayoo.control.Sower
import com.tunjidir.ayoo.entity.Game
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController

/**
 * REST API interface
 *
 * @author olatunji
 */
@RestController
class GameResource(val moderator: Moderator, val sower: Sower) {

    @PostMapping("/game")
    fun startGame(pitStones: Int) = moderator.createGame(pitStones)

    @GetMapping
    fun sow(@PathVariable("gameId") gameId: String, @PathVariable("pitId") pitId: Int) {
        val game = moderator.loadGame(gameId)
        sower.sow(pitId, game!!)
    }

    @PutMapping("/update/{gameId}")
    fun updateGame(@PathVariable("gameId") gameId: String, game: Game) = moderator.updateGame(gameId, game)

}