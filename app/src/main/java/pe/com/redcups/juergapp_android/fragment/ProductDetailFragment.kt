package pe.com.redcups.juergapp_android.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_product.*
import pe.com.redcups.core.utilities.InjectorUtils
import pe.com.redcups.core.viewmodel.products.ProductDetailViewModel
import pe.com.redcups.juergapp_android.R

class ProductDetailFragment : Fragment() {

    private val safeArgs: ProductDetailFragmentArgs by navArgs()

    private val viewModel: ProductDetailViewModel by viewModels{
        InjectorUtils.provideProductDetailViewModelFactory(requireContext(), safeArgs.productId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // bind product to view
        viewModel.product.observe(this, Observer {
            it?.also { product ->
                product_name.text = product.name
                product_description.text =  product.description
                product_alcohol_percentage.text =  product.alcohol_percentage.toString()
                product_image.setImageResource(R.mipmap.event_image_placeholder)
            }
        })


    }


}
