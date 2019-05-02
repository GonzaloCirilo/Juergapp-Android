package pe.com.redcups.juergapp_android.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_view_product.view.*
import pe.com.redcups.core.model.Product
import pe.com.redcups.juergapp_android.R

class ProductAdapter(private val products: Array<Product>, context: Context): RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private var inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
        val view: View = inflater.inflate(R.layout.recycler_view_product, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
        holder.productImageView.setImageResource(R.mipmap.event_image_placeholder)
        holder.product_nameTextView.text = products[position].name.toString()
    }

    inner class ViewHolder(productView: View): RecyclerView.ViewHolder(productView){
        val productImageView: ImageView = productView.product_image
        var product_nameTextView: TextView = productView.product_name
    }
}