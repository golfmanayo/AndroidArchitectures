package th.co.gof.androidarchitectures.mvp.CountriesPresenter

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import th.co.gof.androidarchitectures.model.CountriesService
import th.co.gof.androidarchitectures.model.Country

class CountriesPresenter(var view: View) {

    private var service: CountriesService = CountriesService()

    init {
        fetchCountries()
    }

    private fun fetchCountries() {
        service.getCountries()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<List<Country>>() {
                override fun onSuccess(value: List<Country>?) {
                    val countriesName = value?.map { it.countryName } ?: mutableListOf()
                    view.setValues(countriesName)
                }

                override fun onError(e: Throwable?) {
                    view.onError()
                }

            })

    }

    fun onRefresh() {
        fetchCountries()
    }

    interface View{
        fun setValues(countries : List<String>)
        fun onError()
    }
}