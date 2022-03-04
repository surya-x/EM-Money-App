package `in`.emmoney.app.common.domain.model.user

data class VerificationDetails (
    val isAadharVerified: Boolean,
    val isEmailVerified: Boolean,
    val isPhoneVerified: Boolean,
)
