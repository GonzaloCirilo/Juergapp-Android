package pe.com.redcups.juergapp_android.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_view_order_detail.view.*
import pe.com.redcups.core.model.tx.OrderDetailTX
import pe.com.redcups.juergapp_android.R

class OrderDetailsAdapter(context: Context): RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder>() {

    private var orderDetails: List<OrderDetailTX> = emptyList()
    private val inflater =  LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = inflater.inflate(R.layout.recycler_view_order_detail, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = orderDetails.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            productNameTextView.text = orderDetails[position].productId.toString()
            quantityTextView.text = orderDetails[position].quantity.toString()
            priceTextView.text = orderDetails[position].price.toString()
        }
    }

    fun setOrderDetails(_orderDetails: List<OrderDetailTX>){
        orderDetails = _orderDetails
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var productNameTextView: TextView = itemView.product_name
        var quantityTextView: TextView = itemView.quantity
        var priceTextView: TextView = itemView.unit_price
    }
}