package `in`.emmoney.app.loginandregister.data

import `in`.emmoney.app.common.utils.Utils.toast
import `in`.emmoney.app.common.utils.Utils.toastLong
import `in`.emmoney.app.loginandregister.domain.models.LogInFailedState
import `in`.emmoney.app.loginandregister.domain.models.UserModel
import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.TimeUnit

class AuthRepo constructor(
    val context: Context
) {
    private val TAG = "register"

    private val auth = FirebaseAuth.getInstance()

    private val database = FirebaseFirestore.getInstance()

    private val verificationId: MutableLiveData<String> = MutableLiveData()

    val credential: MutableLiveData<PhoneAuthCredential> = MutableLiveData()

    private val taskResult: MutableLiveData<Task<AuthResult>> = MutableLiveData()

    private val failedState: MutableLiveData<LogInFailedState> = MutableLiveData()

    private val emailAuthTaskResult: MutableLiveData<Task<AuthResult>> = MutableLiveData()

    private val isUserRegistered: MutableLiveData<Boolean> = MutableLiveData(false)


    fun sendOtp(activity: Activity, number: String) {
        Log.d(TAG, "AuthRepo.sendOtp called, calling firebase verify method")

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    /* To create and mark entry in firebase auth database with the phone number*/
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    taskResult.value = task
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException)
                        toastLong(context, "Invalid OTP, Please Try Again!")
                    failedState.value = LogInFailedState.SignIn
                }
            }
    }

    fun linkUserWithEmail (email: String, password: String) {
        val emailCredential = EmailAuthProvider.getCredential(email, password)

        auth.currentUser!!.linkWithCredential(emailCredential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "linkWithCredential:success")
                    Log.d(TAG, "user registered with email:$email")

                    val user = task.result?.user
                    Log.d(TAG, "user: ${user!!.uid}")

                    emailAuthTaskResult.value = task
                } else {
                    Log.w(TAG, "linkWithCredential:failure", task.exception)
                    toastLong(context, "Unable to Register, please try again after sometime")
                }
            }
    }

    fun saveUserToDatabase(user: FirebaseUser, items: HashMap<String, Any>) {
        database.collection("users").document(user.uid).set(items)
            .addOnSuccessListener {
                val userModel = UserModel()
                userModel.userID = user.uid
                userModel.email = items["email"].toString()
                userModel.firstName = items["firstName"].toString()
                userModel.lastName = items["lastName"].toString()
                userModel.phoneNumber = items["phoneNumber"].toString()
                userModel.active = true
//                MyApplication.currentUser = userModel

                Log.d(TAG, "saveUserToDatabase:success")

                isUserRegistered.value = true
            }.addOnFailureListener {
                Log.d(TAG, "saveUserToDatabase:failure", it)
                toastLong(context, "Unable to Register, please try again after sometime")
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

    fun getEmailAuthTaskResult(): LiveData<Task<AuthResult>> {
        return emailAuthTaskResult
    }

    fun isUserRegistered(): LiveData<Boolean> {
        return isUserRegistered
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
        }
    }
}