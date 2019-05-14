package pe.com.redcups.juergapp_android.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_product_category.*
import pe.com.redcups.core.model.ProductCategory
import pe.com.redcups.core.network.AppController
import pe.com.redcups.core.network.JuergappAPI
import pe.com.redcups.juergapp_android.R
import pe.com.redcups.juergapp_android.adapter.ProductCategoryAdapter
import pe.com.redcups.core.network.VolleyConfig
import pe.com.redcups.core.viewmodel.ProductCategoryViewModel
import java.util.concurrent.CountDownLatch

/**
 * A simple [Fragment] subclass.
 *
 */
class ProductCategoryFragment : Fragment() {


    private lateinit var adapter: ProductCategoryAdapter
    private lateinit var viewModel: ProductCategoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_product_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ProductCategoryAdapter(view.context)

        recycler_view_product_category.adapter = adapter
        recycler_view_product_category.layoutManager = LinearLayoutManager(view.context)

        // Get a new or existing ViewModel from the ViewModelProvider.
        viewModel = ViewModelProviders.of(this).get(ProductCategoryViewModel::class.java)

        viewModel.allProductCategories.observe(this, Observer { productCategories ->
            adapter.setProductCategories(productCategories)
            adapter.notifyDataSetChanged()
        })

        // Make request
        // TODO: Get from viewmodel
        //JuergappAPI.getInstance(view.context).getResource(Array<ProductCategory>::class.java)
    }

}
