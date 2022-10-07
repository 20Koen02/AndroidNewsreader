package nl.vanwijngaarden.koen.api.responses


import com.squareup.moshi.Json

data class RegisterUserResponse(
    @Json(name = "Message")
    val message: String,
    @Json(name = "Success")
    val success: Boolean
)