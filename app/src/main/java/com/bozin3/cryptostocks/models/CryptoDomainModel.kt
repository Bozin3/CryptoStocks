package com.bozin3.cryptostocks.models

/**
 * Domain objects are plain Kotlin data classes that represent the things in our app. These are the
 * objects that should be displayed on screen, or manipulated by the app.
 */
data class CryptoDomainModel(
    var id: Long,
    var symbol: String,
    var name: String,
    var price: Float,
    var percentageChangeHour: Float,
    var percentageChangeDay: Float,
    var percentageChangeWeek: Float
)