package com.prueba.tinderswipe.network.repository

import android.util.Log
import com.prueba.tinderswipe.network.model.Characters
import com.prueba.tinderswipe.network.model.DragonBallCharacter
import com.prueba.tinderswipe.network.service.DragonBallService
import okio.IOException

class DragonBallRepository(private val dragonBallService: DragonBallService) {
    suspend fun getCharacter(): DragonBallCharacter? {
        return try {
            dragonBallService.getCharacter().also { characters ->
                Log.d("DragonBallRepository", characters.toString())
            }
        } catch (e: IOException) {
            Log.e("DragonBallRepository", "Error fetching characters", e)
            null
        }
    }
    suspend fun getCharacters(): Characters? {
        return try {
            dragonBallService.getCharacters()
        } catch (e: IOException) {
            Log.e("DragonBallRepository", "Error fetching characters", e)
            null
        }
    }
}