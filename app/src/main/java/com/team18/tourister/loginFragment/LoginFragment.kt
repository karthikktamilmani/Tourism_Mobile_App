package com.team18.tourister.loginFragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.team18.tourister.API.EMAIL_EXTRA

import com.team18.tourister.R
import com.team18.tourister.databinding.FragmentLoginBinding
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        binding.loginVm = ViewModelProvider(this).get(LoginViewModel::class.java).also { view ->
            view.moveForward.observe(viewLifecycleOwner, Observer {
                if(it) {
                    findNavController().navigate(R.id.action_loginFragment_to_otpFragment, bundleOf(
                        EMAIL_EXTRA to view.email), null, null)
                }else {
                    Toast.makeText(context,"Invalid Credentials", Toast.LENGTH_LONG).show()
                }
            })
        }

        binding.lifecycleOwner = this

        goToRegister.setOnClickListener { findNavController().navigate(R.id.action_loginFragment_to_registerFragment) }
        return binding.root
    }


}
