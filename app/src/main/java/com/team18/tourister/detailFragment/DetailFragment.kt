package com.team18.tourister.detailFragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.team18.tourister.API.PLACE_NAME
import com.team18.tourister.API.PLACE_TYPE

import com.team18.tourister.R
import com.team18.tourister.databinding.FragmentDetailFragmentBinding
import kotlinx.android.synthetic.main.fragment_detail_fragment.*

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDetailFragmentBinding.inflate(inflater,container,false)
        binding.detailVM = ViewModelProvider(this).get(DetailViewModel::class.java).also { view ->
            view.isLoaded.observe(viewLifecycleOwner, Observer {
                if (it) {
                    view.setParam(arguments?.getString(PLACE_NAME,"") + "/" + arguments?.getString(
                        PLACE_TYPE,""))
                }
            })

            view.isLoggedIn.observe(this, Observer {
                if (it) {
                    findNavController().navigate(R.id.action_detailFragmet_to_paymentFragment)
                }else {
                    findNavController().navigate(R.id.action_detailFragmet_to_loginFragment)
                }
            })
        }

        binding.lifecycleOwner = this
        // Inflate the layout for this fragment
        return binding.root
    }



}
