package th.co.gof.androidarchitectures

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import th.co.gof.androidarchitectures.mvc.MVCActivity
import th.co.gof.androidarchitectures.mvp.MVPActivity
import th.co.gof.androidarchitectures.mvvm.MVVMActivity

class ArchitecturesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_architectures)

    }


    fun onMVC(view: View){
        startActivity(MVCActivity.getIntent(this))
    }

    fun onMVP(view: View){
        startActivity(MVPActivity.getIntent(this))
    }

    fun onMVVM(view: View){
        startActivity(MVVMActivity.getIntent(this))
    }
}
