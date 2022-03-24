package `in`.emmoney.app.loginandregister.presentation.register

import com.google.firebase.auth.FirebaseAuth

class RegisterViewModel {
    private var mAuth = FirebaseAuth.getInstance()
    var fullname: String = ""
    var phoneNumber: String = ""
    var email: String = ""
    var password: String = ""
}