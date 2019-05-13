package com.vti.base.data.param.response.wrapper

open class Failure {

    class NetworkConnection : Failure()

    class ServerError : Failure()

    class Empty : Failure()

    class SessionTimeout : Failure()

    class APIError(var errorCode: String, var message: String) : Failure() {
        companion object {
            fun justMessage(message: String): APIError {
                return APIError(UNIQUE_ERROR_CODE, message)
            }
        }

    }

    abstract inner class FeatureFailure : Failure()

    companion object {
        private val UNIQUE_ERROR_CODE = "chiuchiu"
    }


}
