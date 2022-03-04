package `in`.emmoney.app.common.domain.model.user

data class Media(val profilePhoto: Photo) {
    data class Photo(
        val link: String
    )
}
