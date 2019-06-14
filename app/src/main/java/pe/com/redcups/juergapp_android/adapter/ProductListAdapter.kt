package pe.com.redcups.juergapp_android.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_view_product_list.view.*
import pe.com.redcups.core.model.Product
import pe.com.redcups.core.utilities.BitmapUtils
import pe.com.redcups.juergapp_android.R
import pe.com.redcups.juergapp_android.fragment.ProductListFragmentDirections
import pe.com.redcups.juergapp_android.options

class ProductListAdapter(context: Context): RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    private var products: List<Product> = emptyList()
    private var inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListAdapter.ViewHolder {
        val view: View = inflater.inflate(R.layout.recycler_view_product_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ProductListAdapter.ViewHolder, position: Int) {
        var product = products[position]

        with(holder){

            if (product.picture_data != null){
                productImageView.setImageBitmap(BitmapUtils.stringToBitmap(product.picture_data!!))
            }
            productNameTextView.text = product.name
            itemView.setOnClickListener{
                // aca le pasas el argumento del evento por Safe Args
                //https://developer.android.com/guide/navigation/navigation-pass-data
                val action = ProductListFragmentDirections.getProductAction(product.id.toString(), product.name)
                holder.itemView.findNavController().navigate(action, options)
            }
        }
    }
    inner class ViewHolder(productListView: View): RecyclerView.ViewHolder(productListView){
        val productImageView: ImageView = productListView.product_list_image
        var productNameTextView: TextView = productListView.product_list_name
    }

    fun setProducts(products: List<Product>){
        this.products = products
        notifyDataSetChanged()
    }

}