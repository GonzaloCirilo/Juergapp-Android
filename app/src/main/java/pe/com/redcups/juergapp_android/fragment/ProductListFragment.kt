package pe.com.redcups.juergapp_android.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_product_category.*
import kotlinx.android.synthetic.main.fragment_product_list.*
import kotlinx.android.synthetic.main.recycler_view_product_list.*
import pe.com.redcups.core.model.Product
import pe.com.redcups.core.model.ProductCategory
import pe.com.redcups.core.network.JuergappAPI
import pe.com.redcups.juergapp_android.R
import pe.com.redcups.juergapp_android.adapter.ProductListAdapter
import java.util.concurrent.CountDownLatch


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ProductListFragment : Fragment() {

    private lateinit var productList: Array<Product>
    private lateinit var productListAdapter: ProductListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //get the Product Category ID
        val safeArgs: ProductListFragmentArgs by navArgs()

        val signal = CountDownLatch(1)

        // Get the Product List from repository
        // Make request

        var product_category_id = safeArgs.productCategoryId

        JuergappAPI.getResource(
            Array<Product>::class.java,
            {
                productList = it
                Log.d("error", "I GOT A PRODUCT")
                signal.countDown()
            },
            {
                Log.d("error", it.toString())
                signal.countDown()
            }
            //"/$product_category_id"
            )
        //espera a que retorno el request
        signal.await()

        if (productList.isNotEmpty()) {
            //recien actualiza
            productListAdapter = ProductListAdapter(productList, view.context)
            recycler_view_product_list.apply {
                adapter = productListAdapter
                layoutManager = LinearLayoutManager(view.context)
            }
        }
    }

}
