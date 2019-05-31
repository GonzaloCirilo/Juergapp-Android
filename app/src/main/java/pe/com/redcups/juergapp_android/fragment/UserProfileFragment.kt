package pe.com.redcups.juergapp_android.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import pe.com.redcups.juergapp_android.R
import pe.com.redcups.core.viewmodel.UserProfileViewModel
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_profile.*
import pe.com.redcups.core.network.TokenManager
import pe.com.redcups.juergapp_android.ui.login.LoginActivity


class UserProfileFragment : Fragment() {
    private var viewModel: UserProfileViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProviders.of(this).get(UserProfileViewModel::class.java)
        logout_button.setOnClickListener {
            TokenManager.getInstance().clear()
            activity?.startActivity(Intent(context, LoginActivity::class.java))
            activity?.finish()
        }

    }
}

