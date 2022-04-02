package `in`.emmoney.app.loginandregister.presentation.login

import `in`.emmoney.app.R
import `in`.emmoney.app.common.utils.Utils
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

class LoginPhoneViewModel(application: Application) : AndroidViewModel(application) {

    private val context: Context = getApplication<Application>().applicationContext

    private val authRepo: AuthRepo = AuthRepo(context)

    val TAG = "auth"

    var phoneNumber: String = ""
    var otpPhone: String = ""

    var phoneWithCountryCode = ""

    private val _phoneNumberError = MutableLiveData<String>()
    val phoneNumberError: LiveData<String>
        get() = _phoneNumberError

    private val _otpError = MutableLiveData<String>()
    val otpError: LiveData<String>
        get() = _otpError

    private val progress = MutableLiveData(false)

    val userProfileGot = MutableLiveData("")

    var verifyCode: String = ""


    fun isValidPhone(): Boolean {
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
                Utils.toastLong(context, "No Internet Connection!")
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
                Utils.toastLong(context, "No Internet Connection!")
                false
            }
            else -> {
                Log.d(TAG, "isValidOtp format, returning true")
                true
            }
        }
    }

    fun sendOtp(activity: Activity) {
        Log.d(TAG, "viewmodel.sendOtp called")

        phoneWithCountryCode = "+91$phoneNumber"

        authRepo.clearOldAuth()
//        this.activity = activity
        authRepo.sendOtp(activity, phoneWithCountryCode)
    }

    fun verifyOtp() {
        Log.d(TAG, "verifyOtp called -> verificationCode:$verifyCode")

        if(this.verifyCode.isNotEmpty()) {
            val credential = PhoneAuthProvider.getCredential(this.verifyCode, this.otpPhone)
            this.setCredential(credential)
        }
        else{
            Log.w(TAG, "verifyOtp: verifyCode is null")
            Toast.makeText(
                context,
                "Something went wrong!! Please try again after sometime",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setCredential(credential: PhoneAuthCredential) {
        setProgress(true)
        authRepo.setCredential(credential)
    }

    fun setProgress(show: Boolean) {
        progress.value = show
    }

    fun getProgress(): LiveData<Boolean> {
        return progress
    }

    fun getCredential(): LiveData<PhoneAuthCredential> {
        return authRepo.getCredential()
    }

    fun getTaskResult(): LiveData<Task<AuthResult>> {
        return authRepo.getTaskResult()
    }

    fun getFailed(): LiveData<LogInFailedState> {
        return authRepo.getFailed()
    }

    fun getVerificationId(): MutableLiveData<String> {
        return authRepo.getVCode()
    }

    fun setVCodeNull() {
        verifyCode = authRepo.getVCode().value!!
        authRepo.setVCodeNull()
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
                setProgress(false)
                progress.value = false
                if (data.exists()) {
                    // User with credential already created in Firestore DB
                    Log.d(TAG, "data.exists():true -> user already in firestore")

                    userProfileGot.value = user?.uid
                    Log.d(TAG, "data.exists():true -> user in present firestore")
                } else {
                    // TODO: mmove user to register page
                    Log.d(TAG, "data.exists():false -> user new to firestore")

                    Utils.toastLong(context, "USER ALREADY EXISTS!! Try Logging in")
                }
            }.addOnFailureListener { e ->
                Log.d(TAG, "noteRef.get():Failure: $e")
//                setVProgress(false)
                setProgress(false)
                progress.value = false
                Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
            }
    }

    fun clearAll() {
        userProfileGot.value = null
        authRepo.clearOldAuth()
    }
}