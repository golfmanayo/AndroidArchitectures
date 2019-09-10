package th.co.gof.androidarchitectures.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import th.co.gof.androidarchitectures.model.CountriesService
import th.co.gof.androidarchitectures.model.Country

class CountriesViewModel : ViewModel() {

    private var service: CountriesService = CountriesService()
    private val countries = MutableLiveData<List<String>>()
    private val countryError = MutableLiveData<Boolean>()

    init {
        fetchCountries()
    }

    fun getCountries(): LiveData<List<String>> {
        return countries
    }

    fun getCountryError(): LiveData<Boolean> {
        return countryError
    }

    private fun fetchCountries() {
        service.getCountries()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<List<Country>>() {
                override fun onSuccess(value: List<Country>?) {
                    val countriesName = value?.map { it.countryName } ?: mutableListOf()
                    countries.value = countriesName
                    countryError.value = false
                }

                override fun onError(e: Throwable?) {
                    countryError.value = true
                }

            })

    }

    fun onRefresh() {
        fetchCountries()
    }
}