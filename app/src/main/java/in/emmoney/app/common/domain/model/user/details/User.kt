package `in`.emmoney.app.common.domain.model.user.details

import `in`.emmoney.app.common.domain.model.user.Media
import `in`.emmoney.app.common.domain.model.user.VerificationDetails

// Entity User
data class User(
    val id: Long,
    val name: Name,
    val email: String,
    val phone: String,
    val media: Media,
    val details: Details,
    val VerificationDetails: VerificationDetails,
)
