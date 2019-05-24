package pe.com.redcups.juergapp_android.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_view_product_category.view.*
import pe.com.redcups.core.model.ProductCategory
import pe.com.redcups.juergapp_android.R
import pe.com.redcups.juergapp_android.fragment.ProductCategoryFragmentDirections
import pe.com.redcups.juergapp_android.options

class ProductCategoryAdapter(context: Context): RecyclerView.Adapter<ProductCategoryAdapter.ViewHolder>() {

    private var productCategories: List<ProductCategory> = emptyList()
    private var inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = inflater.inflate(R.layout.recycler_view_product_category, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = productCategories.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder){
            var product_category = productCategories[position]
            product_categoriesImageView.setImageResource(R.mipmap.event_image_placeholder)
            product_categories_nameTextView.text = product_category.name

            itemView.setOnClickListener{
                // Le pasas el argumento del producto por Safe Args
                //https://developer.android.com/guide/navigation/navigation-pass-data
                val action = ProductCategoryFragmentDirections.getProductListAction(product_category.id.toString(), product_category.name )
                itemView.findNavController().navigate(action, options)
            }
            //}
        }
    }

    inner class ViewHolder(productCategoryView: View): RecyclerView.ViewHolder(productCategoryView){
        val product_categoriesImageView: ImageView = productCategoryView.product_category_image
        var product_categories_nameTextView: TextView = productCategoryView.product_category_name


    }
    fun setProductCategories(productCategories: List<ProductCategory>){
        this.productCategories = productCategories
        notifyDataSetChanged()
    }
}
