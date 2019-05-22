package pe.com.redcups.juergapp_android.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
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
import pe.com.redcups.core.utilities.InjectorUtils
import pe.com.redcups.core.viewmodel.ProductViewModel
import pe.com.redcups.juergapp_android.R
import pe.com.redcups.juergapp_android.adapter.ProductListAdapter
import java.util.concurrent.CountDownLatch

class ProductListFragment : Fragment() {
    private lateinit var adapter: ProductListAdapter
    private val viewModel: ProductViewModel by viewModels{
        InjectorUtils.provideProductViewModelFactory(requireContext())
    }

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

        var product_category_id = safeArgs.productCategoryId

        //set adapter
        adapter = ProductListAdapter(view.context)


        viewModel.allProducts.observe(this, Observer { products ->
            adapter.setProducts(products)

        })
        // Get the Product List from repository
        //JuergappAPI.getResource( Array<Product>::class.java, //"/$product_category_id" )

        recycler_view_product_list.apply {
            adapter = adapter
            layoutManager = LinearLayoutManager(view.context)
        }


    }

}
