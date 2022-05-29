package `in`.emmoney.app.homeActivity.presentation.homePage

import `in`.emmoney.app.homeActivity.data.SchemeRepo
import `in`.emmoney.app.homeActivity.data.SchemesRoomDatabase
import `in`.emmoney.app.homeActivity.domain.models.AllSchemesEntity
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class AllSchemeViewModel(application: Application) : AndroidViewModel(application){

    private val TAG = "AllSchemeViewModel"

    private val repository: SchemeRepo

    init {
        val dao = SchemesRoomDatabase.getDatabase(application).getAllSchemesDao()
        repository = SchemeRepo(dao)
    }

    private val _allSchemes = MutableLiveData<List<AllSchemesEntity>>()
    val allSchemes: LiveData<List<AllSchemesEntity>>
        get() = _allSchemes


    fun fetchAllSchemes() {
        _allSchemes.value = repository.getAllSchemes()
        Log.d(TAG, "-> size:${allSchemes.value?.size}")
    }

}