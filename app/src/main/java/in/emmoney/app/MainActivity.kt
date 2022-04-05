package `in`.emmoney.app

import `in`.emmoney.app.databinding.ActivityMainBinding
import `in`.emmoney.app.homeActivity.presentation.HomeActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private val TAG = "auth"
    private lateinit var binding: ActivityMainBinding

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val FLAG_LOGGED_IN = intent.getBooleanExtra("FLAG_LOGGED_IN", true)

        Log.d(TAG, "checking if user already signed in: FLAG_LOGGED_IN=$FLAG_LOGGED_IN")

        // Only when firebase.current user is not null (&&) intent extra is true
        if(FLAG_LOGGED_IN && auth.currentUser != null){
            Log.d(TAG, "auth.currentUser not null, starting home activity")
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main) as ActivityMainBinding



    }

}