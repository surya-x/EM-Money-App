package `in`.emmoney.app.homeActivity.presentation.homePage

import `in`.emmoney.app.homeActivity.data.SchemeRepo
import `in`.emmoney.app.homeActivity.data.SchemesRoomDatabase
import `in`.emmoney.app.homeActivity.domain.models.SchemeDetailed
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SchemeViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "SchemeViewModel"

    private var currentScheme: SchemeDetailed? = null

    private val _schemeName = MutableLiveData<String?>()
    val schemeName: LiveData<String?>
        get() = _schemeName

    private val _latestNAV = MutableLiveData<String?>()
    val latestNAV: LiveData<String?>
        get() = _latestNAV

    private val _latestNAVDate = MutableLiveData<String?>()
    val latestNAVDate: LiveData<String?>
        get() = _latestNAVDate

    private val _fundType = MutableLiveData<String?>()
    val fundType: LiveData<String?>
        get() = _fundType

    private val _fundCategory = MutableLiveData<String?>()
    val fundCategory: LiveData<String?>
        get() = _fundCategory

    private val _fundHouse = MutableLiveData<String?>()
    val fundHouse: LiveData<String?>
        get() = _fundHouse



    private val repository: SchemeRepo

    init {
        val dao = SchemesRoomDatabase.getDatabase(application).getAllSchemesDao()
        repository = SchemeRepo(dao)
    }

    fun updateDataToViews(schemeID: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(TAG, "Request called for -> getOneSchemeFromNetwork")
            currentScheme = repository.getOneSchemeFromNetwork(schemeID).body()

            Log.d(TAG, "Response received :${currentScheme?.meta.toString()}")

            _schemeName.postValue(currentScheme?.meta?.schemeName)
            _latestNAV.postValue(currentScheme?.data?.get(0)?.nav)
            _latestNAVDate.postValue(currentScheme?.data?.get(0)?.date)
            _fundType.postValue(currentScheme?.meta?.schemeType)
            _fundCategory.postValue(currentScheme?.meta?.schemeCategory)
            _fundHouse.postValue(currentScheme?.meta?.fundHouse)

        }


    }


}