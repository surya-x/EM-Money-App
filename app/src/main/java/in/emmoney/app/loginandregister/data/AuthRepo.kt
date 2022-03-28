package `in`.emmoney.app.loginandregister.data

import `in`.emmoney.app.MainActivity
import `in`.emmoney.app.common.utils.Utils.toast
import `in`.emmoney.app.loginandregister.domain.models.LogInFailedState
import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class AuthRepo constructor(
    val context: Context
) {
    private val TAG = "register"

    private val auth = FirebaseAuth.getInstance()

    private val verificationId: MutableLiveData<String> = MutableLiveData()

    val credential: MutableLiveData<PhoneAuthCredential> = MutableLiveData()

    private val taskResult: MutableLiveData<Task<AuthResult>> = MutableLiveData()

    private val failedState: MutableLiveData<LogInFailedState> = MutableLiveData()


    fun sendOtp(activity: Activity, number: String) {
        Log.d(TAG, "AuthRepo.sendOtp called")

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()

        Log.d(TAG, "calling firebase verify method")
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        Log.d(TAG, "calling signInWithPhoneAuthCredentials")
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "signInWithCredential:success")
                    taskResult.value = task
                } else {
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException)
                        toast(context, "Invalid verification code!")
                    failedState.value = LogInFailedState.SignIn
                }
            }
    }

    fun setCredential(credential: PhoneAuthCredential) {
        signInWithPhoneAuthCredential(credential)
    }

    fun getVCode(): MutableLiveData<String> {
        return verificationId
    }

    fun setVCodeNull() {
        verificationId.value = null
    }

    fun clearOldAuth(){
        credential.value=null
        taskResult.value=null
    }

    fun getCredential(): LiveData<PhoneAuthCredential> {
        return credential
    }

    fun getTaskResult(): LiveData<Task<AuthResult>> {
        return taskResult
    }

    fun getFailed(): LiveData<LogInFailedState> {
        return failedState
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

        /* Firebase verifies the otp automatically */
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            Log.d(TAG, "callback: onVerificationCompleted: $credential")
            this@AuthRepo.credential.value = credential
            signInWithPhoneAuthCredential(credential)

//            Handler().postDelayed({
//                signInWithPhoneAuthCredential(credential)
//            }, 1000)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            failedState.value = LogInFailedState.Verification
            Log.e(TAG, "callback: onVerificationFailed: ${e.message}")
            when (e) {
                is FirebaseAuthInvalidCredentialsException ->
                    toast(context, "Invalid Request")
                else -> toast(context, e.message.toString())
            }
        }

        override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
            super.onCodeSent(verificationId, token)
            Log.d(TAG, "callback: onCodeSent: $verificationId")
            this@AuthRepo.verificationId.value = verificationId
//            toast(context, "Verification code sent successfully")
        }
    }
}