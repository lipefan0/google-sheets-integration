package br.com.contis.google_sheets_integration.dto.token

data class RefreshTokenBlingDTO(
    val grant_type: String,
    val refresh_token: String
)
