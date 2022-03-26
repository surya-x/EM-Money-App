package `in`.emmoney.app.loginandregister.presentation.register

import `in`.emmoney.app.R
import android.app.Application
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth

class Register1ViewModel(private val myApplication: Application) : AndroidViewModel(myApplication) {

    private var mAuth = FirebaseAuth.getInstance()

    var phoneNumber: String = ""
    var otpPhone: String = ""

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

    fun sendOtp() {
        Log.d("Register", "send otp button clicked ${otpRequestSent.value}")

        if (!Patterns.PHONE.matcher(phoneNumber).matches()) {
            Log.d("Register", "phone number pattern mismatched")
            _phoneNumberError.value = myApplication.getString(R.string.malformed_phone_error)
        }
        else {
            Log.d("Register", "request check pass")
            _sendOtpButtonEnabled.value = false

            // not here in callback -- temp TODO
            _otpRequestSent.value = true
            _otpStatus.value = R.string.otp_sent.toString()

            Log.d("Register", "request check pass -- end ${otpRequestSent.value}")
        }
        // _otprequestsent = true
        // otpstatus = otp sent
    }

    fun checkOtp() {
        Log.d("Register", "continue button clicked ${otpRequestSent.value}")
//        _otpRequestSent.value = false
        _continueButtonEnabled.value = false

    }

    fun resendOtp(){
        _resendEnabled.value = false
    }
}