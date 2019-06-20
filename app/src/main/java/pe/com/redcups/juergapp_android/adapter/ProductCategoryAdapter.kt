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
import android.util.Log
import pe.com.redcups.core.utilities.BitmapUtils


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

            val productCategory = productCategories[position]
            //productCategoriesImageView.setImageResource(R.mipmap.event_image_placeholder)
            Log.d("Ggot here", " I got after product categories")

            if (productCategory.pictureData != null){
                productCategoriesImageView.setImageBitmap(BitmapUtils.stringToBitmap(productCategory.pictureData!!))
            }

            with(productCategoriesNameTextView){
                this.text = productCategory.name
                //this.isSelected = true
            }

            itemView.setOnClickListener{
                // Le pasas el argumento del producto por Safe Args
                //https://developer.android.com/guide/navigation/navigation-pass-data
                val action = ProductCategoryFragmentDirections.getProductListAction(productCategory.id.toString(), productCategory.name )
                itemView.findNavController().navigate(action, options)
            }
            //}
        }
    }

    inner class ViewHolder(productCategoryView: View): RecyclerView.ViewHolder(productCategoryView){
        val productCategoriesImageView: ImageView = productCategoryView.product_category_image
        var productCategoriesNameTextView: TextView = productCategoryView.product_category_name


    }
    fun setProductCategories(productCategories: List<ProductCategory>){
        this.productCategories = productCategories
        notifyDataSetChanged()
    }
}
