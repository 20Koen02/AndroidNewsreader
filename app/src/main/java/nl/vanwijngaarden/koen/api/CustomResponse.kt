package nl.vanwijngaarden.koen.api

import retrofit2.Response


data class CustomResponse<T>(
    val status: Status,
    val data: Response<T>?,
    val exception: Exception?
) {
    enum class Status {
        Success,
        Failure
    }

    companion object {
        fun <T> success(data: Response<T>): CustomResponse<T> {
            return CustomResponse(
                status = Status.Success,
                data = data,
                exception = null
            )
        }

        fun <T> failure(exception: Exception): CustomResponse<T> {
            return CustomResponse(
                status = Status.Failure,
                data = null,
                exception = exception
            )
        }
    }


    val isSuccessful: Boolean
        get() = this.status != Status.Failure && this.data?.isSuccessful == true

    val body: T
        get() = this.data!!.body()!!
}