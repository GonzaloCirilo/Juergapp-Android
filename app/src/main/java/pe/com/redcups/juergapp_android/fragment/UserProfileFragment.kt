package pe.com.redcups.juergapp_android.fragment

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import pe.com.redcups.juergapp_android.R
import pe.com.redcups.core.viewmodel.UserProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile.*
import pe.com.redcups.core.network.TokenManager
import pe.com.redcups.core.utilities.InjectorUtils
import pe.com.redcups.core.viewmodel.orders.OrderViewModel
import pe.com.redcups.juergapp_android.adapter.OrderAdapter
import pe.com.redcups.juergapp_android.ui.login.LoginActivity

class UserProfileFragment : Fragment() {
    private var viewModel: UserProfileViewModel? = null

    private val ordersViewModel: OrderViewModel by viewModels {
        InjectorUtils.provideOrderViewModelFactory(requireContext())
    }

    private lateinit var orderAdapter: OrderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logout_button.setOnClickListener {
            TokenManager.getInstance().clear()
            activity?.startActivity(Intent(context, LoginActivity::class.java))
            activity?.finish()
        }
        settings_button.setOnClickListener {
            // right now it launches language settings
            val languageIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(languageIntent)
        }
        orderAdapter = OrderAdapter(view.context)

        ordersViewModel.allOrders.observe(this, Observer {
            orderAdapter.setOrders(it)
        })

        order_recycler_view.apply {
            adapter = orderAdapter
            layoutManager = LinearLayoutManager(view.context)
            addItemDecoration(DividerItemDecoration(context, 1))
        }
    }
}

