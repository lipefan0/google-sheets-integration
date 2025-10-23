package br.com.contis.google_sheets_integration.dto.token

import com.fasterxml.jackson.annotation.JsonProperty

data class ResponseTokenBlingDTO(
    @JsonProperty("access_token")
    val accessToken: String,
    @JsonProperty("refresh_token")
    val refreshToken: String,
    @JsonProperty("expires_in")
    val expiresIn: Long,
)
