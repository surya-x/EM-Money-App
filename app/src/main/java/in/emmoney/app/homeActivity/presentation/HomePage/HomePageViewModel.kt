package `in`.emmoney.app.homeActivity.presentation.homePage

import `in`.emmoney.app.homeActivity.data.SchemeRepo
import `in`.emmoney.app.homeActivity.data.SchemesRoomDatabase
import `in`.emmoney.app.homeActivity.domain.models.AllSchemesEntity
import android.app.Application
import android.util.Log
import android.widget.Toast
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

    private val _searchBarEnabled = MutableLiveData<Boolean>()
    val searchBarEnabled: LiveData<Boolean>
        get() = _searchBarEnabled

    private val progress = MutableLiveData(false)


    fun fetchLimitedSchemes() {
        // Checking if schemes are present in the local Database
        if(repository.getSchemesCountOfAllSchemes() > 0){
            Log.d(TAG, "Found in database")
            _allSchemes.value = repository.getAllSchemesLimitRand()
            Log.d(TAG, "-> size:${allSchemes.value?.size}")

        } else {
            Log.d(TAG, "NOT found in database, requesting API")

            // Making a network call in case not present in local DB
            viewModelScope.launch(Dispatchers.IO) {
                val responseObject = repository.getAllSchemesFromNetwork()
                Log.d(TAG, "response got from network, size:${responseObject.size}")

                if (responseObject.size > 20)
                    _allSchemes.postValue(responseObject.subList(0, 20))

                repository.insertListOfSchemesToAllSchemes(responseObject)
            }
        }
    }

    fun setSearchBarEnabled(boolean: Boolean){
        _searchBarEnabled.value = boolean
    }

    fun getSearchResults(text: String) {
        Log.d(TAG, "getResults called:")
//        _allSchemes.value = repository.searchSchemeName(text)
        val results = repository.searchSchemeNameLike(text)
//        if(results.isEmpty()){
//            Toast.makeText()
//        }
        _allSchemes.value = results

    }

    fun setProgress(show: Boolean) {
        progress.value = show
    }

    fun getProgress(): LiveData<Boolean> {
        return progress
    }


}