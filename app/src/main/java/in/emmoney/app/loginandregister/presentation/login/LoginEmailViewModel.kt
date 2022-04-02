package `in`.emmoney.app.loginandregister.presentation.login

import `in`.emmoney.app.R
import `in`.emmoney.app.loginandregister.data.AuthRepo
import `in`.emmoney.app.loginandregister.domain.models.LogInFailedState
import android.app.Application
import android.content.Context
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginEmailViewModel(application: Application) : AndroidViewModel(application) {

    private val context: Context = getApplication<Application>().applicationContext

    private val authRepo: AuthRepo = AuthRepo(context)

    val TAG = "auth"

    var email: String = ""
    var password: String = ""

    private val _emailError = MutableLiveData<String>()
    val emailError: LiveData<String>
        get() = _emailError

    private val _passwordError = MutableLiveData<String>()
    val passwordError: LiveData<String>
        get() = _passwordError

    private val progress = MutableLiveData(false)

    fun isValidInput() : Boolean{
        email = email.trim()
        return when {
            email.isEmpty() -> {
                Log.d(TAG, "email is empty")
                _emailError.value = context.getString(R.string.malformed_email_empty_error)
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                Log.d(TAG, "email pattern mismatched")
                _emailError.value = context.getString(R.string.malformed_email_error)
                false
            }
            password.length < 6 -> {
                Log.d(TAG, "password is empty")
                _passwordError.value = context.getString(R.string.malformed_password_error)
                false
            }
            else -> {
                Log.d(TAG, "isValidInput: returning true")
                true
            }
        }
    }

    fun login() {
        Log.d(TAG, "viewmodel.login called")
        authRepo.signInWithEmailAndPassword(email, password)
    }

    fun getUserLoggedIn(): LiveData<FirebaseUser> {
        return authRepo.getUserLoggedIn()
    }

    fun setProgress(show: Boolean) {
        progress.value = show
    }

    fun getProgress(): LiveData<Boolean> {
        return progress
    }

    fun getFailed(): LiveData<LogInFailedState> {
        return authRepo.getFailed()
    }
}