package br.com.contis.google_sheets_integration.dto.token

data class AuthTokenBlingDTO(
    val grant_type: String,
    val code: String
)
