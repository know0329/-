package com.d208.giggy.view

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.d208.domain.utils.StringFormatUtil
import com.d208.giggy.R
import com.d208.giggy.base.BaseFragment
import com.d208.giggy.databinding.FragmentHomeBinding

import com.d208.giggy.di.App
import com.d208.giggy.viewmodel.HomeFragmentViewModel
import com.d208.giggy.viewmodel.MainActivityViewModel

import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID

private const val TAG = "HomeFragment giggy"
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home) {

    private val homeFragmentViewModel : HomeFragmentViewModel by viewModels()
    private val mainActivityViewModel : MainActivityViewModel by activityViewModels()
    private var amountPercent = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        animate()
        init()
    }

    fun animate() {

        with(binding){
            YoYo.with(Techniques.ZoomIn)
                .duration(1000)
                .repeat(0)
                .playOn(fragmentHomeConsumptionAnalysisCardView)

            YoYo.with(Techniques.ZoomIn)
                .duration(1000)
                .repeat(0)
                .playOn(fragmentHomeCommunityCardView)

            YoYo.with(Techniques.ZoomIn)
                .duration(1000)
                .repeat(0)
                .playOn(fragmentHomeMiniGameCardView)

            YoYo.with(Techniques.ZoomIn)
                .duration(1000)
                .repeat(0)
                .playOn(fragmentHomeSettingCardView)

            YoYo.with(Techniques.BounceIn)
                .duration(1500)
                .repeat(0)
                .playOn(cardView)


        }



    }

    fun init(){
        with(binding){

            homeFragmentViewModel.getUserData(UUID.fromString(App.sharedPreferences.getString("id")))
            //애니메이션

            homeFragmentViewModel.user.observe(viewLifecycleOwner){
                Log.d(TAG, "init: $it")
                if(it != null){
                    mainActivityViewModel.user = it
                    fragmentHomeNickNameTextView.text = "${it.nickname} 님"
                    fragmentHomeTargetAmountTextView.text = StringFormatUtil.moneyToWon(it.targetAmount)
                    fragmentHomeCurrentAmountTextView.text = StringFormatUtil.moneyToWon(it.currentAmount)
                    amountPercent = (100f - (it.currentAmount.toFloat() / it.targetAmount) * 100f).toInt()
                    fragmentHomeBankMoneyTextView.text =StringFormatUtil.moneyToWon(App.sharedPreferences.getInt("money"))
                    ObjectAnimator.ofInt(fragmenthomeProgressBar, "progress", amountPercent.toInt())
                        .setDuration(500)
                        .start()
                }
                else{
                    showSnackbar("불러올 정보가 없습니다")

                }

            }


            // 소비 패턴 분석 화면
            fragmentHomeConsumptionAnalysisCardView.setOnClickListener {
                findNavController().navigate(R.id.action_HomeFragment_to_ConsumeAnalysisFragment)
            }
            // 미니 게임 화면
            fragmentHomeMiniGameCardView.setOnClickListener {
                findNavController().navigate(R.id.action_HomeFragment_to_GameFragment)
            }
            // 커뮤니티 화면
            fragmentHomeCommunityCardView.setOnClickListener {
                findNavController().navigate(R.id.action_HomeFragment_to_CommunityHomeFragment)
            }
            // 설정 화면
            fragmentHomeSettingCardView.setOnClickListener {
                findNavController().navigate(R.id.action_HomeFragment_to_SettingFragment)
            }
            // 내 거래내역 조회
            fragmentHomeMyAccountCardView.setOnClickListener {
                findNavController().navigate(R.id.action_HomeFragment_to_TransactionHistoryFragment)
            }
            //거지 랭킹 조회
            fragmentHomeRankButton.setOnClickListener {
                findNavController().navigate(R.id.action_HomeFragment_to_RankFragment)
            }

            ObjectAnimator.ofInt(fragmenthomeProgressBar, "progress", amountPercent.toInt())
                .setDuration(500)
                .start()
        }
        homeFragmentViewModel.exceptionHandler.observe(viewLifecycleOwner){
            when(it){
                0 -> {
                    showSnackbar("네트워크 오류")
                    findNavController().navigate(R.id.action_HomeFragment_to_ErrorFragment)
                }
                401 ->{
                    showSnackbar("토큰 값이 만료되었습니다.")
                    findNavController().navigate(R.id.action_HomeFragment_to_ErrorFragment)
                }
            }
        }

    }

}