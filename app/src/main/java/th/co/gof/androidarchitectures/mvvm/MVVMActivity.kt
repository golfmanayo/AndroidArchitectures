package th.co.gof.androidarchitectures.mvvm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_mvvm.*
import th.co.gof.androidarchitectures.R

class MVVMActivity : AppCompatActivity() {

    private val listValue: MutableList<String> = mutableListOf()
    lateinit var adapter: ArrayAdapter<String>
    private lateinit var viewModel: CountriesViewModel

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, MVVMActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvvm)
        title = "MVVM Activity"

        viewModel = ViewModelProviders.of(this).get(CountriesViewModel::class.java)

        adapter = ArrayAdapter(this, R.layout.row_layout, R.id.listText, listValue)
        list.adapter = adapter
        list.setOnItemClickListener { adapterView, view, i, l ->
            Toast.makeText(this, "You Clicked ${listValue[i]}", Toast.LENGTH_LONG).show()
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.getCountries().observe(this, Observer { countries ->
            if (countries.isNotEmpty()){
                listValue.clear()
                listValue.addAll(countries)
                list.visibility = View.VISIBLE
                adapter.notifyDataSetChanged()
            }else{
                list.visibility = View.GONE
            }
        })

        viewModel.getCountryError().observe(this,Observer {isError->
            progress.visibility = View.GONE
            if (isError){
                retryButton.visibility = View.VISIBLE
            }else{
                retryButton.visibility = View.GONE
            }

        })
    }

    fun onRetry(view: View) {
        viewModel.onRefresh()
        retryButton.visibility = View.GONE
        progress.visibility = View.VISIBLE
        list.visibility = View.GONE
    }

}
