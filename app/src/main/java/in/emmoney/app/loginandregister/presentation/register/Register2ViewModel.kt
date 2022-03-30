package `in`.emmoney.app.loginandregister.presentation.register

import `in`.emmoney.app.R
import `in`.emmoney.app.common.utils.Utils
import `in`.emmoney.app.loginandregister.data.AuthRepo
import android.app.Application
import android.content.Context
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential

class Register2ViewModel(application: Application) : AndroidViewModel(application) {

//    private val context: Context = getApplication<Application>().applicationContext
//
//    private val authRepo: AuthRepo = AuthRepo(context)
//
//    val TAG = "register"
//
//    var firstName: String = ""
//    var lastName: String = ""
//    var email: String = ""
//    var password: String = ""

//    private val progress = MutableLiveData(false)

//    private val _firstNameError = MutableLiveData<String>()
//    val firstNameError: LiveData<String>
//        get() = _firstNameError
//
//    private val _lastNameError = MutableLiveData<String>()
//    val lastNameError: LiveData<String>
//        get() = _lastNameError
//
//    private val _emailError = MutableLiveData<String>()
//    val emailError: LiveData<String>
//        get() = _emailError
//
//    private val _passwordError = MutableLiveData<String>()
//    val passwordError: LiveData<String>
//        get() = _passwordError

//    fun isValidEntries(): Boolean {
//        return when {
//            firstName.isEmpty() -> {
//                Log.d(TAG, "firstname is empty")
//                _firstNameError.value = context.getString(R.string.malformed_fname_error)
//                false
//            }
//            lastName.isEmpty() -> {
//                Log.d(TAG, "lastname is empty")
//                _lastNameError.value = context.getString(R.string.malformed_lname_error)
//                false
//            }
//            email.isEmpty() -> {
//                Log.d(TAG, "email is empty")
//                _emailError.value = context.getString(R.string.malformed_email_empty_error)
//                false
//            }
//            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
//                Log.d(TAG, "email pattern mismatched is empty")
//                _emailError.value = context.getString(R.string.malformed_email_error)
//                false
//            }
//            password.length < 6 -> {
//                Log.d(TAG, "password is empty")
//                _passwordError.value = context.getString(R.string.malformed_password_error)
//                false
//            }
//            Utils.isNoInternet(context) -> {
//                Log.d(TAG, "No Internet Connection!")
//                Utils.toastLong(context, "No Internet Connection!")
//                false
//            }
//            else -> {
//                Log.d(TAG, "isValidEntries, returning true")
//                true
//            }
//        }
//    }

//    fun registerUser() {
//        email = email.trim()
//        authRepo.registerUserInAuth(email, password)
//    }
//
//    fun createUser(user: FirebaseUser) {
//        Log.d(TAG, "createUser called")
//
//        val userid = user.uid
//        val items = HashMap<String, Any>()
//        items["email"] = email
//        items["firstName"] = firstName
//        items["lastName"] = lastName
//        items["phoneNumber"] = phoneNumber
//        items["userID"] = userid
//        items["profilePictureURL"] = ""
//        items["active"] = true
//        saveUserToDatabase(auth.currentUser!!, items)
//    }
//
//    fun setProgress(show: Boolean) {
//        progress.value = show
//    }
//
//    fun getProgress(): LiveData<Boolean> {
//        return progress
//    }




}