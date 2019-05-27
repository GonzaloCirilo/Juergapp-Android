package pe.com.redcups.juergapp_android.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_product_category.*
import pe.com.redcups.core.utilities.InjectorUtils
import pe.com.redcups.juergapp_android.R
import pe.com.redcups.juergapp_android.adapter.ProductCategoryAdapter
import pe.com.redcups.core.viewmodel.products.ProductCategoryViewModel

/**
 * A simple [Fragment] subclass.
 *
 */
class ProductCategoryFragment : Fragment() {


    private lateinit var adapter: ProductCategoryAdapter
    private val viewModel: ProductCategoryViewModel by viewModels {
        InjectorUtils.provideProductCategoryViewModelFactory(requireContext())
    }

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
        recycler_view_product_category.layoutManager = GridLayoutManager(view.context, 3, RecyclerView.VERTICAL,false)


        viewModel.allProductCategories.observe(this, Observer { productCategories ->
            adapter.setProductCategories(productCategories)
            adapter.notifyDataSetChanged()
        })

    }

}
