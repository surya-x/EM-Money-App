package `in`.emmoney.app.loginandregister.domain
import `in`.emmoney.app.loginandregister.domain.models.UserRegistering
import android.app.Activity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterUser {
    private lateinit var auth: FirebaseAuth

    private fun createAccount(activity: Activity, userRegistering: UserRegistering) : FirebaseAuth {
        val email = userRegistering.email
        val password = userRegistering.password

        Log.d(TAG, "createAccount: $email")

//        showProgressBar()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
//                    Toast.makeText(context, "Authentication failed.",
//                        Toast.LENGTH_SHORT).show()
//                    updateUI(null)
                }
//                hideProgressBar()
            }
        return auth
    }

    private fun validateForm(): Boolean {
        val valid = true

//        val email = binding.fieldEmail.text.toString()
//        if (TextUtils.isEmpty(email)) {
//            binding.fieldEmail.error = "Required."
//            valid = false
//        } else {
//            binding.fieldEmail.error = null
//        }

//        val password = binding.fieldPassword.text.toString()
//        if (TextUtils.isEmpty(password)) {
//            binding.fieldPassword.error = "Required."
//            valid = false
//        } else {
//            binding.fieldPassword.error = null
//        }

        return valid
    }

    companion object {
        private const val TAG = "EmailPassword"
        private const val RC_MULTI_FACTOR = 9005
    }
}