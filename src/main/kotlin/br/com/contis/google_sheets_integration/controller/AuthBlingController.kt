package br.com.contis.google_sheets_integration.controller

import br.com.contis.google_sheets_integration.service.AuthBlinService
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth-bling")
class AuthBlingController(
    private val authBlingService: AuthBlinService,
    @Value("\${bling.api.client-id}") private val blingClientId: String,
    @Value("\${app.auth.state-token}") private val token: String
) {

    @GetMapping("/authorize-url")
    fun generateAuthorizationUrl(): ResponseEntity<Map<String, String>> {
        val authorizeUrl = "https://www.bling.com.br/Api/v3/oauth/authorize" +
                "?response_type=code" +
                "&client_id=$blingClientId" +
                "&state=$token"

        return ResponseEntity.ok(mapOf("authorize_url" to authorizeUrl))
    }

    @GetMapping("/redirect")
    suspend fun handleBlingRedirect(code: String, state: String): ResponseEntity<String> {
        if (state != token) {
            return ResponseEntity.status(403).body("Invalid state token")
        }

        return try {
            authBlingService.authenticateAndStoreToken(code)
            ResponseEntity.ok("Authentication successful and token stored.")
        } catch (e: Exception) {
            ResponseEntity.status(500).body("Error during authentication: ${e.message}")
        }
    }
}