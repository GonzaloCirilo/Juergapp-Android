package pe.com.redcups.juergapp_android.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_view_product_category.view.*
import pe.com.redcups.core.model.ProductCategory
import pe.com.redcups.juergapp_android.R

class ProductCategoryAdapter(private val product_categories: Array<ProductCategory>, context: Context): RecyclerView.Adapter<ProductCategoryAdapter.ViewHolder>() {

    private var inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductCategoryAdapter.ViewHolder {
        val view: View = inflater.inflate(R.layout.recycler_view_product_category, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = product_categories.size

    override fun onBindViewHolder(holder: ProductCategoryAdapter.ViewHolder, position: Int) {
        holder.product_categoriesImageView.setImageResource(R.mipmap.event_image_placeholder)
        holder.product_categories_nameTextView.text = product_categories[position].name.toString()
    }

    inner class ViewHolder(productCategoryView: View): RecyclerView.ViewHolder(productCategoryView){
        val product_categoriesImageView: ImageView = productCategoryView.product_category_image
        var product_categories_nameTextView: TextView = productCategoryView.product_category_name
    }
}
