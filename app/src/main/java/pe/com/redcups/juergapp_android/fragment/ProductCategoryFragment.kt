package pe.com.redcups.juergapp_android.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_product_category.*
import pe.com.redcups.core.model.ProductCategory
import pe.com.redcups.core.network.AppController
import pe.com.redcups.core.network.JuergappAPI
import pe.com.redcups.juergapp_android.R
import pe.com.redcups.juergapp_android.adapter.ProductCategoryAdapter
import com.android.volley.*
import com.android.volley.toolbox.Volley
import pe.com.redcups.core.network.VolleyConfig
import java.util.concurrent.CountDownLatch

/**
 * A simple [Fragment] subclass.
 *
 */
class ProductCategoryFragment : Fragment() {


    private lateinit var productCategories: Array<ProductCategory>
    private lateinit var productCategoryAdapter: ProductCategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_product_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Prepara el latch
        val signal =  CountDownLatch(1)

        //crea el App Controller
        AppController.getInstance()
        AppController.initRequestQueue(VolleyConfig.newVolleyRequestQueueForTest(view.context))

        // Make request
        JuergappAPI.getResource(
            Array<ProductCategory>::class.java,
            {
                productCategories = it
                signal.countDown()

            },
            {
                Log.d("error", it.toString())
                signal.countDown()
            })
        //espera a que retorno el request
        signal.await()

        //recien actualiza
        productCategoryAdapter = ProductCategoryAdapter(productCategories,view.context)
        recycler_view_product_category.apply{
            adapter = productCategoryAdapter
            layoutManager = LinearLayoutManager(view.context)
        }

        // adds click listener
        //event_add_button.setOnClickListener{
        //    // Este id dentro de Navigate es del que esta definido en navigation/nav_graph
        //    findNavController().navigate(R.id.event_add_dest, null, options)
        //}

    }

}
