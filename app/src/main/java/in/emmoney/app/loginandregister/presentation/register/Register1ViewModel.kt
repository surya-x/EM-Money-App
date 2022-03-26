package `in`.emmoney.app.loginandregister.presentation.register

import `in`.emmoney.app.R
import android.app.Activity
import android.app.Application
import android.telephony.PhoneNumberUtils
import android.util.Log
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthProvider
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class Register1ViewModel(private val myApplication: Application) : AndroidViewModel(myApplication){

    private var mAuth = FirebaseAuth.getInstance()

    var phoneNumber: String = ""
    var otpPhone: String = ""
    val TAG = "register"

    private val _otpRequestSent = MutableLiveData<Boolean>().apply { value = false }
    val otpRequestSent: LiveData<Boolean>
        get() = _otpRequestSent

    private val _otpStatus = MutableLiveData<String>()
    val otpStatus: LiveData<String>
        get() = _otpStatus

    private val _sendOtpButtonEnabled = MutableLiveData<Boolean>().apply { value = true }
    val sendOtpButtonEnabled: LiveData<Boolean>
        get() = _sendOtpButtonEnabled

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

    fun onSendOtpClicked() {
        Log.d(TAG, "send otp called")

        if (!PhoneNumberUtils.isGlobalPhoneNumber("+91$phoneNumber")) {
            Log.d(TAG, "Phone number pattern mismatched")
            _phoneNumberError.value = myApplication.getString(R.string.malformed_phone_error)
        }
        else {
            Log.d(TAG, "Phone number pattern matched, sending OTP")

            _sendOtpButtonEnabled.value = false
            // not here in callback -- temp TODO
            _otpRequestSent.value = true
            _otpStatus.value = R.string.otp_sent.toString()

            sendOtp()
        }
    }

    private fun sendOtp() {
        val phoneWithCountryCode = "+91$phoneNumber"
//        val options = PhoneAuthOptions.newBuilder(mAuth)
//            .setPhoneNumber(phoneWithCountryCode)       // Phone number to verify
//            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
//            .setActivity()                 // Activity (for callback binding)
//            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
//            .build()


//        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun checkOtp() {
        Log.d(TAG, "continue button clicked ${otpRequestSent.value}")
//        _otpRequestSent.value = false
        _continueButtonEnabled.value = false

    }

    fun resendOtp(){
        _resendEnabled.value = false
    }
}