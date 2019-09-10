package th.co.gof.androidarchitectures.mvp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_mvp.*
import th.co.gof.androidarchitectures.R
import th.co.gof.androidarchitectures.mvp.CountriesPresenter.CountriesPresenter

class MVPActivity : AppCompatActivity(), CountriesPresenter.View {

    private val listValue: MutableList<String> = mutableListOf()
    lateinit var adapter: ArrayAdapter<String>
    private lateinit var presenter: CountriesPresenter

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, MVPActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvp)
        title = "MVP Activity"


        presenter = CountriesPresenter(this)

        adapter = ArrayAdapter(this, R.layout.row_layout, R.id.listText, listValue)
        list.adapter = adapter
        list.setOnItemClickListener { adapterView, view, i, l ->
            Toast.makeText(this, "You Clicked ${listValue[i]}", Toast.LENGTH_LONG).show()
        }
    }

    override fun setValues(countries: List<String>) {
        listValue.clear()
        listValue.addAll(countries)
        retryButton.visibility = View.GONE
        progress.visibility = View.GONE
        list.visibility = View.VISIBLE
        adapter.notifyDataSetChanged()
    }

    override fun onError() {
        retryButton.visibility = View.VISIBLE
        progress.visibility = View.GONE
        list.visibility = View.GONE
    }

    fun onRetry(view: View) {
        presenter.onRefresh()
        retryButton.visibility = View.GONE
        progress.visibility = View.VISIBLE
        list.visibility = View.GONE
    }
}
