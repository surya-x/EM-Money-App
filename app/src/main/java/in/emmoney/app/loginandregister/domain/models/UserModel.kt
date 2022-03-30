package `in`.emmoney.app.loginandregister.domain.models

import `in`.emmoney.app.common.domain.model.user.details.Name

data class UserModel(
    var userID: String = "",
    var email: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var phoneNumber: String = "",
    var active: Boolean = false
)
