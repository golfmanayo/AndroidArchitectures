package th.co.gof.androidarchitectures.mvc

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_mvc.*
import th.co.gof.androidarchitectures.R

class MVCActivity : AppCompatActivity() {

    private val listValue: MutableList<String> = mutableListOf()
    lateinit var adapter: ArrayAdapter<String>
    private lateinit var controller: CountriesController

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, MVCActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvc)
        title = "MVC Activity"

        controller = CountriesController(this)

        adapter = ArrayAdapter(this, R.layout.row_layout, R.id.listText, listValue)
        list.adapter = adapter
        list.setOnItemClickListener { adapterView, view, i, l ->
            Toast.makeText(this, "You Clicked ${listValue[i]}", Toast.LENGTH_LONG).show()
        }


    }

    fun setValues(values: List<String>) {
        listValue.clear()
        listValue.addAll(values)
        retryButton.visibility = View.GONE
        progress.visibility = View.GONE
        list.visibility = View.VISIBLE
        adapter.notifyDataSetChanged()
    }

    fun onRetry(view: View) {
        controller.onRefresh()
        retryButton.visibility = View.GONE
        progress.visibility = View.VISIBLE
        list.visibility = View.GONE
    }

    fun onError(){
        retryButton.visibility = View.VISIBLE
        progress.visibility = View.GONE
        list.visibility = View.GONE
    }

}
