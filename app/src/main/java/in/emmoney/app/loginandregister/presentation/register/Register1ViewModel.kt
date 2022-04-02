package `in`.emmoney.app.loginandregister.presentation.register

import `in`.emmoney.app.R
import `in`.emmoney.app.common.utils.Utils
import `in`.emmoney.app.common.utils.Utils.toastLong
import `in`.emmoney.app.loginandregister.data.AuthRepo
import `in`.emmoney.app.loginandregister.domain.models.LogInFailedState
import android.app.Activity
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
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore


//class Register1ViewModel
//constructor(private val context: Context, private val authRepo: AuthRepo) :
//    ViewModel() {
class Register1ViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var activity: Activity

    private val context: Context = getApplication<Application>().applicationContext

    private val authRepo: AuthRepo = AuthRepo(context)

    val TAG = "auth"

    var phoneNumber: String = ""
    var otpPhone: String = ""

    var phoneWithCountryCode = ""
    var firstName: String = ""
    var lastName: String = ""
    var email: String = ""
    var password: String = ""


    // Error Live data's

    private val _phoneNumberError = MutableLiveData<String>()
    val phoneNumberError: LiveData<String>
        get() = _phoneNumberError

    private val _otpError = MutableLiveData<String>()
    val otpError: LiveData<String>
        get() = _otpError

    private val _firstNameError = MutableLiveData<String>()
    val firstNameError: LiveData<String>
        get() = _firstNameError

    private val _lastNameError = MutableLiveData<String>()
    val lastNameError: LiveData<String>
        get() = _lastNameError

    private val _emailError = MutableLiveData<String>()
    val emailError: LiveData<String>
        get() = _emailError

    private val _passwordError = MutableLiveData<String>()
    val passwordError: LiveData<String>
        get() = _passwordError


    val userProfileGot = MutableLiveData("")

    private val progress = MutableLiveData(false)

    var verifyCode: String = ""


    fun isValidPhone(): Boolean {
//        return if (!PhoneNumberUtils.isGlobalPhoneNumber("+91$phoneNumber")) {

        phoneNumber = phoneNumber.filter { !it.isWhitespace() }
        return when {
            phoneNumber.length != 10 || !android.util.Patterns.PHONE.matcher(phoneNumber)
                .matches() -> {
                Log.d(TAG, "Phone number pattern mismatched")
                _phoneNumberError.value = context.getString(R.string.malformed_phone_error)
                false
            }
            Utils.isNoInternet(context) -> {
                Log.d(TAG, "No Internet Connection!")
                toastLong(context, "No Internet Connection!")
                false
            }
            else -> {
                Log.d(TAG, "Phone number pattern matched, returning true")
                true
            }
        }
    }

    fun isValidOtp(): Boolean {
        otpPhone = otpPhone.filter { !it.isWhitespace() }
        return when {
            otpPhone.length != 6 -> {
                Log.d(TAG, "OTP length mismatched")
                _otpError.value = context.getString(R.string.malformed_otp_error)
                false
            }
            Utils.isNoInternet(context) -> {
                Log.d(TAG, "No Internet Connection!")
                toastLong(context, "No Internet Connection!")
                false
            }
            else -> {
                Log.d(TAG, "isValidOtp format, returning true")
                true
            }
        }
    }

    fun isValidEntries(): Boolean {
        firstName = firstName.trim()
        lastName = lastName.trim()
        email = email.trim()

        return when {
            firstName.isEmpty() -> {
                Log.d(TAG, "firstname is empty")
                _firstNameError.value = context.getString(R.string.malformed_fname_error)
                false
            }
            lastName.isEmpty() -> {
                Log.d(TAG, "lastname is empty")
                _lastNameError.value = context.getString(R.string.malformed_lname_error)
                false
            }
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
            Utils.isNoInternet(context) -> {
                Log.d(TAG, "No Internet Connection!")
                Utils.toastLong(context, "No Internet Connection!")
                false
            }
            else -> {
                Log.d(TAG, "isValidEntries, returning true")
                true
            }
        }
    }


    fun sendOtp(activity: Activity) {
        Log.d(TAG, "viewmodel.sendOtp called")

        phoneWithCountryCode = "+91$phoneNumber"

        authRepo.clearOldAuth()
        this.activity = activity
        authRepo.sendOtp(activity, phoneWithCountryCode)
    }

    fun verifyOtp() {
        val credential = PhoneAuthProvider.getCredential(this.verifyCode, this.otpPhone)
        this.setCredential(credential)
    }

    private fun setCredential(credential: PhoneAuthCredential) {
        setProgress(true)
        authRepo.setCredential(credential)
    }

    fun linkUserWithEmail() {
        email = email.trim()
        authRepo.linkUserWithEmail(email, password)
    }

    fun createUserInDb(user: FirebaseUser) {
        Log.d(TAG, "createUser called")

        val userid = user.uid

        var userPhoneNumber = ""
        if (user.phoneNumber != null)
            userPhoneNumber = user.phoneNumber!!
        Log.d(TAG, " ->  viewmodel phoneNumber is $userPhoneNumber")

        val items = HashMap<String, Any>()
        items["userID"] = userid
        items["firstName"] = firstName
        items["lastName"] = lastName
        items["email"] = email
        items["phoneNumber"] = userPhoneNumber
        items["active"] = true

        authRepo.saveUserToDatabase(user, items)
    }


    fun setProgress(show: Boolean) {
        progress.value = show
    }

    fun getProgress(): LiveData<Boolean> {
        return progress
    }

    fun resendClicked() {
//        _resendEnabled.value = false
//        if (canResend) {
//            // setVProgress(true)
//            setProgress(true)
//            sendOtp(activity)
//        }
    }

//    fun setVProgress(show: Boolean) {
//        verifyProgress.value = show
//    }
//
//    fun getVProgress(): LiveData<Boolean> {
//        return verifyProgress
//    }

    fun getCredential(): LiveData<PhoneAuthCredential> {
        return authRepo.getCredential()
    }

    fun setVCodeNull() {
        verifyCode = authRepo.getVCode().value!!
        authRepo.setVCodeNull()
    }

    fun getVerificationId(): MutableLiveData<String> {
        return authRepo.getVCode()
    }

    fun getTaskResult(): LiveData<Task<AuthResult>> {
        return authRepo.getTaskResult()
    }

    fun getFailed(): LiveData<LogInFailedState> {
        return authRepo.getFailed()
    }

    fun getEmailAuthTaskResult(): LiveData<Task<AuthResult>> {
        return authRepo.getEmailAuthTaskResult()
    }

    fun isUserRegistered(): LiveData<Boolean> {
        return authRepo.isUserRegistered()
    }

    fun fetchUser(taskId: Task<AuthResult>) {
        // TODO: temp log + update section
        Log.d(TAG, "credentials verified, fetch user called")
        val db = FirebaseFirestore.getInstance()
        val user = taskId.result?.user
        val noteRef = db.document("users/" + user?.uid)

        noteRef.get()
            .addOnSuccessListener { data ->
                Log.d(TAG, "noteRef.get():Success")
//                setVProgress(false)
                setProgress(false)
                progress.value = false
                if (data.exists()) {
                    // User with credential already created in Firestore DB
                    // TODO: move user to login page
                    Log.d(TAG, "data.exists():true -> user already in firestore")
                    toastLong(context, "USER ALREADY EXISTS!! Try Logging in")
                } else {
                    Log.d(TAG, "data.exists():false -> user new to firestore")
                    userProfileGot.value = user?.uid
                }
            }.addOnFailureListener { e ->
                Log.d(TAG, "noteRef.get():Failure: $e")
//                setVProgress(false)
                setProgress(false)
                progress.value = false
                Toast.makeText(
                    context,
                    e.message.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    fun clearAll() {
        userProfileGot.value = null
        authRepo.clearOldAuth()
    }
}