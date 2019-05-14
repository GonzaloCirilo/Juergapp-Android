package pe.com.redcups.juergapp_android.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_product.*
import pe.com.redcups.core.model.Product
import pe.com.redcups.core.network.AppController
import pe.com.redcups.core.network.JuergappAPI
import pe.com.redcups.core.network.VolleyConfig
import pe.com.redcups.juergapp_android.R
import java.util.concurrent.CountDownLatch


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ProductFragment : Fragment() {

    lateinit var product: Product

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val safeArgs: ProductFragmentArgs by navArgs()
        val productId = safeArgs.productId

        // Make request

        // JuergappAPI.getResource(Product::class.java, "/$productId")

        if (product.equals(null)) return

        // bind product to view

        product_name.text = product.name
        product_description.text =  product.description
        product_alcohol_percentage.text =  product.alcohol_percentage.toString()
        product_image.setImageResource(R.mipmap.event_image_placeholder)


    }


}