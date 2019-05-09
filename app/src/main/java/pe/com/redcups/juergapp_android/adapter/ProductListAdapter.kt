package pe.com.redcups.juergapp_android.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_view_product_list.view.*
import pe.com.redcups.core.model.Product
import pe.com.redcups.juergapp_android.R
import pe.com.redcups.juergapp_android.fragment.ProductListFragmentDirections

class ProductListAdapter(private val products: Array<Product>, context: Context): RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    private var inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListAdapter.ViewHolder {
        val view: View = inflater.inflate(R.layout.recycler_view_product_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ProductListAdapter.ViewHolder, position: Int) {

        val options = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }

        with(holder){
            productImageView.setImageResource(R.mipmap.event_image_placeholder)
            productNameTextView.text = products[position].name.toString()
            itemView.setOnClickListener{
                // aca le pasas el argumento del evento por Safe Args
                //https://developer.android.com/guide/navigation/navigation-pass-data
                val action = ProductListFragmentDirections.getProductAction(products[position].id, products[position].name)
                holder.itemView.findNavController().navigate(action, options)
            }
        }
    }
    inner class ViewHolder(productListView: View): RecyclerView.ViewHolder(productListView){
        val productImageView: ImageView = productListView.product_list_image
        var productNameTextView: TextView = productListView.product_list_name
    }
}