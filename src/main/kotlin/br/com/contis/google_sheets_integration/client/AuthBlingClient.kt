package br.com.contis.google_sheets_integration.client

import br.com.contis.google_sheets_integration.dto.empresa.ResponseDataEmpresaBlingDTO
import br.com.contis.google_sheets_integration.dto.empresa.ResponseEmpresaBlingDTO
import br.com.contis.google_sheets_integration.dto.token.ResponseTokenBlingDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@Component
class AuthBlingClient(
    private val webClientBuilder: WebClient.Builder,
    @Value("\${bling.api.url}") private val blingApiUrl: String,
    @Value("\${bling.api.client-id}") private val clientId: String,
    @Value("\${bling.api.client-secret}") private val clientSecret: String
) {

    private val webClient = webClientBuilder.baseUrl(blingApiUrl).build()

    private val credentials = java.util.Base64.getEncoder()
        .encodeToString("$clientId:$clientSecret".toByteArray())

    suspend fun generateToken(code: String): ResponseTokenBlingDTO {
        return webClient.post()
            .uri("/oauth/token")
            .header("Authorization", "Basic $credentials")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(
                BodyInserters
                    .fromFormData("grant_type", "authorization_code")
                    .with("code", code)
            )
            .retrieve()
            .awaitBody<ResponseTokenBlingDTO>()
    }

    suspend fun refreshToken(refreshToken: String): ResponseTokenBlingDTO {
        return webClient.post()
            .uri("/oauth/token")
            .header("Authorization", "Basic $credentials")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(
                BodyInserters
                    .fromFormData("grant_type", "refresh_token")
                    .with("refresh_token", refreshToken)
            )
            .retrieve()
            .awaitBody<ResponseTokenBlingDTO>()
    }

    suspend fun getEmpresaDetails(accessToken: String): ResponseDataEmpresaBlingDTO {
        return webClient.get()
            .uri("/empresas/me/dados-basicos")
            .header("Authorization", "Bearer $accessToken")
            .retrieve()
            .awaitBody<ResponseDataEmpresaBlingDTO>()
    }
}