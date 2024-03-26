package com.d208.giggy.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.d208.giggy.R
import com.d208.giggy.base.BaseFragment
import com.d208.giggy.databinding.FragmentSignUpBinding
import com.d208.giggy.viewmodel.MainActivityViewModel
import com.d208.giggy.viewmodel.SignUpFragmentViewModel

import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [SignUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val TAG = "SignUpFragment giggy"
@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::bind, R.layout.fragment_sign_up) {

    private val signUpFragmentViewModel : SignUpFragmentViewModel by viewModels()
    private val mainActivityViewModel : MainActivityViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }
    fun init() {
        with(binding) {
            // 등록 후 홈 화면 이동
            fragmentSignUpButton.setOnClickListener {

                if(fragmentSignUpNickNameEditText.text.isNullOrEmpty()){
                    showSnackbar("닉네임을 입력해주세요")
                }
                else{
                    signUpFragmentViewModel.duplicateNickNameCheck(mainActivityViewModel.user, fragmentSignUpNickNameEditText.text.toString())
                }

            }
            fragmentSignUpTargetAmountSlider.addOnChangeListener { slider, value, fromUser ->
                // Slider의 값이 변경되면 EditText에 업데이트합니다.
                Log.d("Slider", "$value 만원")
                fragmentSignUpTargetAmountEditText.text = "${value.toInt()} 만원"
            }
            // 금액 설정

        }
        signUpFragmentViewModel.checkSuccess.observe(viewLifecycleOwner){
            if(it != null){
                if(it.duplicate == true){
                    showSnackbar("이미 사용 중인 닉네임 입니다.")
                }
                else{
                    mainActivityViewModel.user.nickname = binding.fragmentSignUpNickNameEditText.text.toString()
                    mainActivityViewModel.user.targetAmount = binding.fragmentSignUpTargetAmountSlider.value.toInt() * 10000
                    findNavController().navigate(R.id.action_SignUpFragment_to_SignUpNextFragment)
                }
            }
            else{
                showSnackbar("통신 오류가 발생했습니다.")
            }
        }
    }


}