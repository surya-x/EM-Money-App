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
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
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

//    private var mAuth = FirebaseAuth.getInstance()

    var phoneNumber: String = ""
    var otpPhone: String = ""
    val TAG = "register"


    private val _continueButtonEnabled = MutableLiveData<Boolean>().apply { value = true }
    val continueButtonEnabled: LiveData<Boolean>
        get() = _continueButtonEnabled

    private val _resendEnabled = MutableLiveData<Boolean>().apply { value = true }
    val resendEnabled: LiveData<Boolean>
        get() = _resendEnabled

    private val _phoneNumberError = MutableLiveData<String>()
    val phoneNumberError: LiveData<String>
        get() = _phoneNumberError

    private val _otpError = MutableLiveData<String>()
    val otpError: LiveData<String>
        get() = _otpError

    val userProfileGot = MutableLiveData("")

    private val progress = MutableLiveData(false)

    private val verifyProgress = MutableLiveData(false)

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
                toastLong(context,"No Internet Connection!")
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
                toastLong(context,"No Internet Connection!")
                false
            }
            else -> {
                Log.d(TAG, "isValidOtp, returning true")
                true
            }
        }
    }

    fun sendOtp(activity: Activity) {
        Log.d(TAG, "viewmodel.sendOtp called")

        val phoneWithCountryCode = "+91$phoneNumber"

        authRepo.clearOldAuth()
        this.activity = activity
        authRepo.sendOtp(activity, phoneWithCountryCode)
    }

    fun verifyOtp(){
        val credential = PhoneAuthProvider.getCredential(this.verifyCode, this.otpPhone)
        this.setCredential(credential)
    }

    fun setCredential(credential: PhoneAuthCredential) {
        setVProgress(true)
        authRepo.setCredential(credential)
    }

    fun checkOtp() {
        Log.d(TAG, "continue button clicked")
//        _otpRequestSent.value = false
        _continueButtonEnabled.value = false
    }

    fun setProgress(show: Boolean) {
        progress.value = show
    }

    fun getProgress(): LiveData<Boolean> {
        return progress
    }

    fun resendClicked() {
        _resendEnabled.value = false
//        if (canResend) {
//            setVProgress(true)
//            sendOtp(activity)
//        }
    }

    fun setVProgress(show: Boolean) {
        verifyProgress.value = show
    }

    fun getVProgress(): LiveData<Boolean> {
        return verifyProgress
    }

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

    fun fetchUser(taskId: Task<AuthResult>) {
        // TODO: temp log + update section
        Log.d(TAG, "credentials verified, fetch user called")

        val db = FirebaseFirestore.getInstance()
        val user = taskId.result?.user
        val noteRef = db.document("Users/" + user?.uid)
        noteRef.get()
            .addOnSuccessListener { data ->
                setVProgress(false)
                progress.value = false
                if (data.exists()) {  //already created user
                    //save profile in preference
                }
                userProfileGot.value = user?.uid
            }.addOnFailureListener { e ->
                setVProgress(false)
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