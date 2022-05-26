package `in`.emmoney.app.homeActivity.presentation

import `in`.emmoney.app.homeActivity.data.EMMoneyClient
import `in`.emmoney.app.homeActivity.data.SchemeRepo
import `in`.emmoney.app.homeActivity.data.SchemesRoomDatabase
import `in`.emmoney.app.homeActivity.domain.AllSchemesEntity
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomePageViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "HomePageViewModel"

    private val repository: SchemeRepo

    init {
        val dao = SchemesRoomDatabase.getDatabase(application).getAllSchemesDao()
        val remoteDAO = EMMoneyClient.remoteDAO
        repository = SchemeRepo(dao, remoteDAO)
    }

    var allSchemes: LiveData< List<AllSchemesEntity> > = repository.allSchemes

//    fun createRepoInstance(application: Application) = viewModelScope.launch(Dispatchers.IO) {
//        val dao = SchemesRoomDatabase.getDatabase(application).getAllSchemesDao()
//        val remoteDAO = EMMoneyClient.remoteDAO
//        repository = SchemeRepo(dao, remoteDAO)
//    }

    fun fetchAllSchemes() {
        // Checking if schemes are present in the local Database
        if(repository.isTableEmptyDB() > 0){
            Log.d(TAG, "Table isn't empty!!")
            allSchemes = repository.allSchemes

        } else {
            Log.d(TAG, "Table IS empty!!")

        }
    }

    fun delete() = viewModelScope.launch(Dispatchers.IO) {  }

}