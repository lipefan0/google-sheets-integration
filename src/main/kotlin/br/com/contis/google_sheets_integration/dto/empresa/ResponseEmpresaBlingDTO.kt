package br.com.contis.google_sheets_integration.dto.empresa

import java.time.LocalDate

data class ResponseEmpresaBlingDTO(
    val id: String,
    val nome: String,
    val cnpj: String,
    val email: String,
    val dataContrato: LocalDate,
)
