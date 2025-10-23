package br.com.contis.google_sheets_integration.service

import br.com.contis.google_sheets_integration.client.AuthBlingClient
import br.com.contis.google_sheets_integration.model.TokenBling
import br.com.contis.google_sheets_integration.repository.TokenBlingRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AuthBlinService(
    private val authBlingClient: AuthBlingClient,
    private val tokenBlingRepository: TokenBlingRepository
) {

    suspend fun authenticateAndStoreToken(code: String) {
        val tokenResponse = authBlingClient.generateToken(code)
        val empresaDetails = authBlingClient.getEmpresaDetails(tokenResponse.accessToken)

        val tokenBling = TokenBling(
            accessToken = tokenResponse.accessToken,
            refreshToken = tokenResponse.refreshToken,
            expiresIn = tokenResponse.expiresIn,
            companyId = empresaDetails.id,
            companyName = empresaDetails.nome,
            createdAt = java.time.LocalDateTime.now(),
            updatedAt = java.time.LocalDateTime.now()
        )

        tokenBlingRepository.save(tokenBling)
    }

    suspend fun refreshToken() {
       val tokenNeedRefresh = tokenBlingRepository.findTokenBlingByExpiresInBefore(LocalDateTime.now().minusMinutes(60))
        if (tokenNeedRefresh != null) {
            val tokenResponse = authBlingClient.refreshToken(tokenNeedRefresh.refreshToken)
            val updatedTokenBling = tokenNeedRefresh.copy(
                accessToken = tokenResponse.accessToken,
                refreshToken = tokenResponse.refreshToken,
                expiresIn = tokenResponse.expiresIn,
                updatedAt = LocalDateTime.now()
            )
            tokenBlingRepository.save(updatedTokenBling)
        } else {
            throw Exception("No token found that needs refresh")
        }
    }
}