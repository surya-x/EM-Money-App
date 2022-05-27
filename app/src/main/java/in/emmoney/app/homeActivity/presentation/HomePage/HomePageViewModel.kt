package `in`.emmoney.app.homeActivity.presentation.homePage

import `in`.emmoney.app.homeActivity.data.SchemeRepo
import `in`.emmoney.app.homeActivity.data.SchemesRoomDatabase
import `in`.emmoney.app.homeActivity.domain.models.AllSchemesEntity
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomePageViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "HomePageViewModel"

    private val repository: SchemeRepo

    init {
        val dao = SchemesRoomDatabase.getDatabase(application).getAllSchemesDao()
        repository = SchemeRepo(dao)
    }

//    var allSchemes: LiveData< List<AllSchemesEntity> > = repository.getAllSchemes()

    private val _allSchemes = MutableLiveData<List<AllSchemesEntity>>()
    val allSchemes: LiveData< List<AllSchemesEntity> >
        get() = _allSchemes


    fun fetchAllSchemes() {
        // Checking if schemes are present in the local Database
        if(repository.getSchemesCountOfAllSchemes() > 0){
            Log.d(TAG, "Table isn't empty!!")
            _allSchemes.value = repository.getAllSchemes()
            Log.d(TAG, "-> size:${allSchemes.value?.size}")

        } else {
            Log.d(TAG, "Table IS empty!!")
            viewModelScope.launch(Dispatchers.IO) {
                val responseObject = repository.getAllSchemesFromNetwork()
                Log.d(TAG, "response got from network, size:${responseObject.size}")

                _allSchemes.postValue(responseObject)

                repository.insertListOfSchemesToAllSchemes(responseObject)
            }
        }
    }

    fun delete() = viewModelScope.launch(Dispatchers.IO) {  }

}