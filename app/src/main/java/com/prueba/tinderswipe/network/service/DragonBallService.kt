package com.prueba.tinderswipe.network.service

import android.util.Log
import com.prueba.tinderswipe.network.model.Characters
import com.prueba.tinderswipe.network.model.DragonBallCharacter
import com.prueba.tinderswipe.utils.ApiLinks
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class DragonBallService(private val client: HttpClient) {
    suspend fun getCharacter(): DragonBallCharacter? {
        return try {
            client
                .get(urlString = ApiLinks.Character.apiLink)
                .body()
        } catch (e: Exception) {
            Log.e("DragonBallService", "Error fetching characters", e)
            null
        }
    }

    suspend fun getCharacters(): Characters? {
        return try {
            client
                .get(urlString = ApiLinks.Characters.apiLink)
                .body<Characters?>().also {
                    Log.d("Characters", it.toString())
                }
        } catch (e: Exception) {
            Log.e("DragonBallService", "Error fetching characters", e)
            null
        }
    }
}