package nl.vanwijngaarden.koen.api.responses


import com.squareup.moshi.Json

data class LoginUserResponse(
    @Json(name = "AuthToken")
    val authToken: String
)