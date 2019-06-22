package pe.com.redcups.juergapp_android.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_cart.*
import pe.com.redcups.core.model.tx.OrderDetailTX
import pe.com.redcups.core.utilities.InjectorUtils
import pe.com.redcups.core.viewmodel.orders.CartViewModel

import pe.com.redcups.juergapp_android.R
import pe.com.redcups.juergapp_android.adapter.OrderDetailsAdapter

class CartFragment : Fragment() {

    val viewModel: CartViewModel by viewModels {
        InjectorUtils.provideCartViewModelFactory(requireContext())
    }

    private lateinit var orderDetailsAdapter: OrderDetailsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title.setOnClickListener {
            val orderDetail = OrderDetailTX(
                productId = null,
                price = 3.0f,
                quantity = 5,
                supplierId = null,
                orderId = 0
            )
            viewModel.persist(orderDetail)
        }

        orderDetailsAdapter = OrderDetailsAdapter(view.context)

        cart_content_recycler_view.adapter = orderDetailsAdapter
        cart_content_recycler_view.layoutManager = LinearLayoutManager(view.context)
        cart_content_recycler_view.addItemDecoration(DividerItemDecoration(context, 1))
        viewModel.cartContent.observe(this, Observer {order ->
            if(order==null){
                viewModel.initCart()
            }
            order?.also {
                orderDetailsAdapter.setOrderDetails(it.orderDetails)
                orderDetailsAdapter.notifyDataSetChanged()
            }

        })
        confirm_purchase_button.setOnClickListener {
            viewModel.order()
        }
    }
}
