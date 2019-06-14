package pe.com.redcups.juergapp_android.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_view_order.view.*
import pe.com.redcups.juergapp_android.R
import pe.com.redcups.core.model.Order

class OrderAdapter(context: Context): RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    private var orders: List<Order> = emptyList();

    private var inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderAdapter.ViewHolder {
        val view: View = inflater.inflate(R.layout.recycler_view_order, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = orders.size

    override fun onBindViewHolder(holder: OrderAdapter.ViewHolder, position: Int) {
        holder.orderImageView.setImageResource(R.mipmap.event_image_placeholder)
        holder.orderTextView.text = orders[position].totalPrice.toString()
    }

    inner class ViewHolder(orderView: View): RecyclerView.ViewHolder(orderView){
        val orderImageView: ImageView = orderView.order_image
        var orderTextView: TextView = orderView.order_name
    }
    // updates Orders Array
    fun setOrders(orders: List<Order>){
        this.orders = orders
        this.notifyDataSetChanged()
    }
}