package com.d208.giggy.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.d208.giggy.R
import com.d208.giggy.base.BaseFragment
import com.d208.giggy.databinding.FragmentSignUpCompleteBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SignUpCompleteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignUpCompleteFragment : BaseFragment<FragmentSignUpCompleteBinding>(FragmentSignUpCompleteBinding::bind, R.layout.fragment_sign_up_complete) {
    // TODO: Rename and change types of parameters



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }
    fun init(){
        binding.fragmentSignUpCompleteButton.setOnClickListener {

            findNavController().navigate(R.id.action_SignUpCompleteFragment_to_LoginFragment)
        }

    }

}