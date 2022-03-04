package `in`.emmoney.app.common.domain.model.user.details

// Value objects details
data class Details(
    val age: Int,
    val gender: Gender,
    val address: String,    // TO be edited
    val country: Country,
)
