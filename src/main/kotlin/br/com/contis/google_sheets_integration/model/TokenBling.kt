package br.com.contis.google_sheets_integration.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "token_bling")
data class TokenBling(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "access_token")
    val accessToken: String,
    @Column(name = "refresh_token")
    val refreshToken: String,
    @Column(name = "expires_in")
    val expiresIn: LocalDateTime,
    @Column(name = "company_id")
    val companyId: String,
    @Column(name = "company_name")
    val companyName: String,
    @Column(name = "created_at")
    val createdAt: LocalDateTime,
    @Column(name = "updated_at")
    val updatedAt: LocalDateTime
)
