package th.co.gof.androidarchitectures.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CountriesService {

    companion object {
        const val BASE_UEL = "https://restcountries.eu/rest/v2/"
    }

    var api: CountriesApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_UEL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        api = retrofit.create(CountriesApi::class.java)
    }

    fun getCountries(): Single<List<Country>> {
        return api.getCountries()
    }

}