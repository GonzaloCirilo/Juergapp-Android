package pe.wemake.watch

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.activity_next_event.*

class NextEventActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next_event)

        next_event_image.setOnClickListener {
            Toast.makeText(this.applicationContext, "Tapped!", Toast.LENGTH_SHORT).show()
        }
        next_event_name.setOnClickListener {
            Toast.makeText(this.applicationContext, "Tapped!", Toast.LENGTH_SHORT).show()
        }
        next_event_address.setOnClickListener {
            Toast.makeText(this.applicationContext, "Tapped!", Toast.LENGTH_SHORT).show()
        }
    }
}
