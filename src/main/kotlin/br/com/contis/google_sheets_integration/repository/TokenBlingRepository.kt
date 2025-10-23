package br.com.contis.google_sheets_integration.repository

import br.com.contis.google_sheets_integration.model.TokenBling
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface TokenBlingRepository: JpaRepository<TokenBling, Long> {
    suspend fun findTokenBlingByExpiresInBefore(dateTime: LocalDateTime): TokenBling?
}