package pe.com.redcups.juergapp_android.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_order.*
import pe.com.redcups.juergapp_android.R
import pe.com.redcups.juergapp_android.adapter.OrderAdapter
import pe.com.redcups.core.model.Order

class OrderFragment : Fragment() {

    private var orders = ArrayList<Order>()
    private lateinit var orderAdapter: OrderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        for (i in 1..5){
            orders.add(Order("Order $i", i))
        }
        return inflater.inflate(R.layout.fragment_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        orderAdapter = OrderAdapter(orders, view.context)
        recycler_view_order.apply {
            adapter = orderAdapter
            layoutManager = LinearLayoutManager(view.context )
        }
    }
}
