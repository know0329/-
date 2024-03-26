package com.d208.giggy.view

import android.app.Dialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.d208.domain.utils.StringFormatUtil
import com.d208.giggy.R
import com.d208.giggy.base.BaseFragment
import com.d208.giggy.databinding.DialogTargetAmountChangeBottomSheetBinding
import com.d208.giggy.databinding.FragmentSettingBinding
import com.d208.giggy.di.App
import com.d208.giggy.viewmodel.MainActivityViewModel
import com.d208.giggy.viewmodel.SettingFragmentViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import java.util.Date

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(FragmentSettingBinding::bind, R.layout.fragment_setting) {
    var nextTargetAmount = 100000
    private val mainActivityViewModel : MainActivityViewModel by activityViewModels()
    private val settingFragmentViewModel : SettingFragmentViewModel by viewModels()
    private val dialog: Dialog by lazy {
        BottomSheetDialog(requireContext()).apply {
            setContentView(R.layout.dialog_target_amount_change_bottom_sheet)
        }
    }
    private val dialogBinding: DialogTargetAmountChangeBottomSheetBinding by lazy {
        DialogTargetAmountChangeBottomSheetBinding.bind(dialog.findViewById(R.id.dialog_target_amount_change_bottom_sheet))
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }
    fun init(){

        with(binding) {
            fragmentSettingLogout.setOnClickListener {
                App.sharedPreferences.removeUser()
                findNavController().navigate(R.id.action_SettingFragment_to_LoginFragment)
            }
            fragmentTargetAmountChangeLayout.setOnClickListener {
                showDialog()

            }
        }
        settingFragmentViewModel.updateSuccess.observe(viewLifecycleOwner){
            if(it){
                showSnackbar("목표 금액이 변경되었습니다.")
                mainActivityViewModel.user.targetAmount = nextTargetAmount
            }
            else{
                showSnackbar("목표 금액 변경에 실패했습니다.")
            }
        }


    }

    fun showDialog(){
        val date = Date(System.currentTimeMillis())
        val calendar = Calendar.getInstance()
        calendar.time = date
        val week = calendar.get(Calendar.DAY_OF_WEEK)

        Log.d("요일", "$week ")
        with(dialogBinding){

            when(week){
                2 -> {
                    val backgroudColor = Color.parseColor("#FFB800")
                    dialogTargetAmountChangeUpdateButton.backgroundTintList = ColorStateList.valueOf(backgroudColor)
                }
                else -> {
                    val backgroudColor = Color.parseColor("#C6C6C6")
                    dialogTargetAmountChangeUpdateButton.backgroundTintList = ColorStateList.valueOf(backgroudColor)
                }
            }
            dialogTargetAmountChangeCurrentAmountTextView.text = StringFormatUtil.moneyToWon(mainActivityViewModel.user.targetAmount)
            dialogTargetAmountChangeUpdateButton.setOnClickListener {
                if(week == 2){
                    settingFragmentViewModel.updateTargetAmount(nextTargetAmount)
                    dialog.dismiss()
                }
                else{
                    showSnackbar("월요일에만 목표 금액을 변경할 수 있습니다.")
                }
            }
            dialogTargetAmountChangeCancelButton.setOnClickListener {
                dialog.dismiss()
            }
            dialogTargetAmountChangeTargetAmountSlider.addOnChangeListener { slider, value, fromUser ->
                nextTargetAmount = value.toInt()*10000
                Log.d("Slider", "$value 만원")
                dialogTargetAmountChangeNextAmountTextView.text = StringFormatUtil.moneyToWon(value.toInt()*10000)
            }
            dialog.show()
        }
    }

}