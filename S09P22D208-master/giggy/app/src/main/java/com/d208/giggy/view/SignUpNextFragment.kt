package com.d208.giggy.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.d208.giggy.R
import com.d208.giggy.base.BaseFragment
import com.d208.giggy.databinding.FragmentSignUpNextBinding
import com.d208.giggy.di.App
import com.d208.giggy.viewmodel.MainActivityViewModel
import com.d208.giggy.viewmodel.SignUpNextFragmentViewModel
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import dagger.hilt.android.AndroidEntryPoint


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SignUpNextFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val TAG = "SignUpNextFragment giggy"
@AndroidEntryPoint
class SignUpNextFragment : BaseFragment<FragmentSignUpNextBinding>(FragmentSignUpNextBinding::bind, R.layout.fragment_sign_up_next) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var countDownTimer: CountDownTimer? = null
    private val signUpNextFragmentViewModel : SignUpNextFragmentViewModel by viewModels()
    private val mainActivityViewModel : MainActivityViewModel by activityViewModels()
    private val REQ_PERMISSION_PUSH = 0;
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.TIRAMISU && PackageManager.PERMISSION_DENIED == ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS)){
            // 푸쉬 권한 없음
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.POST_NOTIFICATIONS), REQ_PERMISSION_PUSH)
        }
        init()
    }

    fun init(){
        with(binding){
            fragmentSignUpNextCheckButton.setOnClickListener {
                Log.d(TAG, "init: ${mainActivityViewModel.user.fcmToken}")
                if(!fragmentSignUpNextAccountEditText.text.toString().isNullOrEmpty()){
                    startTimer()
                    fragmentSignUpNextTimerTextView.visibility  = View.VISIBLE
                    signUpNextFragmentViewModel.accountAuth(binding.fragmentSignUpNextAccountEditText.text.toString(), mainActivityViewModel.fcmToken,  mainActivityViewModel.user.birthday!!)
                }
            }
            fragmentSignUpNextCompleteButton.setOnClickListener {

                if(fragmentSignUpNextTimerTextView.text == "00:00"){
                    showSnackbar("시간이 초과되었습니다. 다시 인증 요청을 해주세요")
                }
                else if(fragmentSignUpNextAccountCheckEditText.text.isNullOrEmpty()){
                    showSnackbar("인증 문자를 입력해주세요")
                }
                else{
                    Log.d(TAG, "init: ${signUpNextFragmentViewModel.password}")
                    if(fragmentSignUpNextAccountCheckEditText.text.toString() == signUpNextFragmentViewModel.password){
                        mainActivityViewModel.user.fcmToken = mainActivityViewModel.fcmToken
                        signUpNextFragmentViewModel.signUp(mainActivityViewModel.user, mainActivityViewModel.accessToken, mainActivityViewModel.refreshToken, fragmentSignUpNextAccountEditText.text.toString())

                    }
                    else{
                        showSnackbar("인증 문자가 일치하지 않습니다.")
                    }

                }

            }
        }
        signUpNextFragmentViewModel.accountAuthSuccess.observe(viewLifecycleOwner){
            if(it){
                App.sharedPreferences.addAccount(binding.fragmentSignUpNextAccountEditText.text.toString())
            }
            else{
                showSnackbar("일치하는 계좌가 없습니다.")
            }
        }
        signUpNextFragmentViewModel.signUpSuccess.observe(viewLifecycleOwner){
            if(it){

                findNavController().navigate(R.id.action_SignUpNextFragment_to_SignUpCompleteFragment)
            }
            else{
                showSnackbar("오류 발생")
            }
        }

    }

    private fun startTimer() {
        countDownTimer?.cancel()

        countDownTimer = object : CountDownTimer(180000, 1000) { // 3분을 밀리초로 표현
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / 60000
                val seconds = (millisUntilFinished % 60000) / 1000
                binding.fragmentSignUpNextTimerTextView.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                binding.fragmentSignUpNextTimerTextView.text = "00:00"
            }
        }
        countDownTimer?.start()
    }

    private fun stopTimer() {
        countDownTimer?.cancel()
        binding.fragmentSignUpNextTimerTextView.text = "3:00"
    }

    override fun onStop() {
        super.onStop()
        stopTimer()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTimer()
    }

}