package com.team18.tourister.paymentFragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.team18.tourister.R
import com.team18.tourister.databinding.FragmentPaymentBinding
import kotlinx.android.synthetic.main.fragment_payment.*

/**
 * A simple [Fragment] subclass.
 */
class PaymentFragment : Fragment() {

    private lateinit var binding: FragmentPaymentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPaymentBinding.inflate(inflater,container,false)
        binding.paymentVm = ViewModelProvider(this).get(PaymentViewModel::class.java).also { view ->
            view.cardNumber.observe(viewLifecycleOwner, Observer { number ->
                cardInput.setText(number)
                emailInput.setText(view.email.value)
            })
        }

        binding.lifecycleOwner = this
        // Inflate the layout for this fragment
        return binding.root
    }


}
