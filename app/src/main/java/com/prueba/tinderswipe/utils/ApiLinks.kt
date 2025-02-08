package com.prueba.tinderswipe.utils

sealed class ApiLinks(val apiLink: String) {
    data object Character : ApiLinks("https://dragonball-api.com/api/characters/1")
    data object Characters : ApiLinks("https://dragonball-api.com/api/characters?page=0&limit=30")
}