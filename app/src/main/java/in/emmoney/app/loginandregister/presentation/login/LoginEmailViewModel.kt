package `in`.emmoney.app.loginandregister.presentation.login

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth

class LoginEmailViewModel(application: Application) : AndroidViewModel(application) {
    private var mAuth = FirebaseAuth.getInstance()

//    val email: LiveData<String>
    var email: String = ""
        set(value) {
            field = value
//            validateInput()
        }

    var password: String = ""
        set(value) {
            field = value
//            validateInput()
        }

    fun login() {
        Toast.makeText(getApplication(), "login clicked $email & $password", Toast.LENGTH_SHORT).show()
//        onStartLoading()
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
//                getFirebaseUserData()
                Log.d("LOGIN", "SUCCESSFUL with $email & $password")
            } else {
//                onFinishLoading()
//                _errorString.value = it.exception?.message
                Log.d("LOGIN", "UNSUCCESSFUL with $email & $password")
            }
        }
    }

    private fun validateInput() {
//        _buttonEnabled.value = !(username.isEmpty() || password.isEmpty())
    }
}