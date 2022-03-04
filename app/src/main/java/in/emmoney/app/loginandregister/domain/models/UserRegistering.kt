package `in`.emmoney.app.loginandregister.domain.models

import `in`.emmoney.app.common.domain.model.user.details.Name

data class UserRegistering(
    val email: String,
    val password: String,
    val phone: String,
    val name: Name,
)
