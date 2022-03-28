package `in`.emmoney.app.common.utils

import `in`.emmoney.app.common.presentation.CustomProgressView
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast

object Utils {
    fun toast(context: Context, msg: String){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun toastLong(context: Context,msg: String){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    private fun isNetConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            (capabilities != null &&
                    ((capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)))
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            (activeNetworkInfo != null && activeNetworkInfo.isConnected)
        }
    }

    fun isNoInternet(context: Context) = !isNetConnected(context)

    fun CustomProgressView.toggle(show: Boolean){
        if (show)
            this.show()
        else
            this.dismiss();
    }

    fun CustomProgressView.dismissIfShowing(){
        if (this.isShowing)
            this.dismiss()
    }

}