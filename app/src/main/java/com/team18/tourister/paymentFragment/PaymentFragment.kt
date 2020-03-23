package com.team18.tourister.paymentFragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.team18.tourister.API.TO_ADDRESS

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
//        val name = arguments?.getString(TO_ADDRESS,"Vadodara")
            val name = "Vadodara"
        binding.paymentVm = ViewModelProvider(this).get(PaymentViewModel::class.java).also { view ->

            view.price.observe(viewLifecycleOwner, Observer { price ->
                binding.total.setText("Total:" + price + "$")
            })

            view.cardNumber.observe(viewLifecycleOwner, Observer { number ->
                cardInput.setText(number)
                emailInput.setText(view.email.value)
            })

            view.expiry.observe(viewLifecycleOwner, Observer { expiry ->
                val parts = expiry.split('/')
                binding.monthInput.setText(parts[0])
                binding.yearInput.setText(parts[1])
            })




            view.getPlaceName(name)

            view.ticketBooked.observe(viewLifecycleOwner, Observer {
                if (!it) {
                    Toast.makeText(context,"There was a problem, please try again later", Toast.LENGTH_LONG).show()
                }else {
                    findNavController().navigate(R.id.action_paymentFragment_to_successFragment)
                }
            })
        }

        binding.lifecycleOwner = this
        // Inflate the layout for this fragment
        return binding.root
    }


}
