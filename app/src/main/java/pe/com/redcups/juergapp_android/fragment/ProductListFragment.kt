package pe.com.redcups.juergapp_android.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_product_list.*
import pe.com.redcups.core.utilities.InjectorUtils
import pe.com.redcups.core.viewmodel.products.ProductViewModel
import pe.com.redcups.juergapp_android.R
import pe.com.redcups.juergapp_android.adapter.ProductListAdapter

class ProductListFragment : Fragment() {

    private lateinit var adapter: ProductListAdapter
    private val safeArgs: ProductListFragmentArgs by navArgs()
    private val viewModel: ProductViewModel by viewModels{
        InjectorUtils.provideProductViewModelFactory(requireContext(), safeArgs.productCategoryId)
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

        //set adapter
        adapter = ProductListAdapter(view.context)

        viewModel.allProducts.observe(this, Observer { products ->
            adapter.setProducts(products)
        })

        recycler_view_product_list.adapter = adapter
        recycler_view_product_list.layoutManager = LinearLayoutManager(view.context)
    }

}
