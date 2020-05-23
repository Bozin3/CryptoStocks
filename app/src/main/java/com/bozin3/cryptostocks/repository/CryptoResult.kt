package com.bozin3.cryptostocks.repository

data class Result(val status: Status, val message: String? = null) {

    enum class Status {
        SUCCESS,
        ERROR
    }

    companion object {
        fun success(): Result {
            return Result(Status.SUCCESS)
        }

        fun error(message: String): Result {
            return Result(Status.ERROR, message)
        }
    }
}