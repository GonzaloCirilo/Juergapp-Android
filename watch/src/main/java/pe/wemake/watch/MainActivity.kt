package pe.wemake.watch

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.wear.ambient.AmbientModeSupport
import kotlinx.android.synthetic.main.activity_main.*
import pe.com.redcups.core.utilities.InjectorUtils
import pe.com.redcups.core.viewmodel.events.EventViewModel

class MainActivity : FragmentActivity(), AmbientModeSupport.AmbientCallbackProvider {


    override fun getAmbientCallback(): AmbientModeSupport.AmbientCallback = AmbientCallback()

    private lateinit var adapter: EventAdapter
    private lateinit var ambientController: AmbientModeSupport.AmbientController


    private val viewModel: EventViewModel by viewModels{
        InjectorUtils.provideEventViewModelFactory(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Enables Always-on
        ambientController = AmbientModeSupport.attach(this)

        adapter = EventAdapter(applicationContext)

        wearable_recycler_view.adapter = adapter
        wearable_recycler_view.layoutManager= LinearLayoutManager(applicationContext)

        viewModel.events.observe(this, Observer {
            adapter.setEvents(it)
        })
    }

}
