package com.yi.movieflix.app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform