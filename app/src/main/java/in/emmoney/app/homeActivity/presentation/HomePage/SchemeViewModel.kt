package `in`.emmoney.app.homeActivity.presentation.homePage

import `in`.emmoney.app.homeActivity.data.SchemeRepo
import `in`.emmoney.app.homeActivity.data.SchemesRoomDatabase
import android.app.Application
import androidx.lifecycle.AndroidViewModel

class SchemeViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "SchemeViewModel"

    private val repository: SchemeRepo

    init {
        val dao = SchemesRoomDatabase.getDatabase(application).getAllSchemesDao()
        repository = SchemeRepo(dao)
    }



}