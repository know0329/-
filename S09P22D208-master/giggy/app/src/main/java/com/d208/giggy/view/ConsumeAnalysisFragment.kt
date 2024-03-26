package com.d208.giggy.view

import android.graphics.Color
import android.os.Bundle
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
import com.d208.giggy.databinding.FragmentConsumeAnalysisBinding
import com.d208.giggy.di.App

import com.d208.giggy.viewmodel.ConsumeAnalysisFragmentViewModel
import com.d208.giggy.viewmodel.MainActivityViewModel

import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.ColorTemplate.COLORFUL_COLORS
import dagger.hilt.android.AndroidEntryPoint
import java.util.Arrays
import java.util.LinkedList
import java.util.UUID

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ConsumeAnalysisFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class ConsumeAnalysisFragment : BaseFragment<FragmentConsumeAnalysisBinding>(
    FragmentConsumeAnalysisBinding::bind, R.layout.fragment_consume_analysis) {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val consumeAnalysisFragmentViewModel : ConsumeAnalysisFragmentViewModel by viewModels()
    private val mainActivityViewModel : MainActivityViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun chartInit(){
        with(binding){

            fragmentConsumeAnalysisChart.setUsePercentValues(true)

            // data Set
            val entries = ArrayList<PieEntry>()
            entries.add(PieEntry(0f, "쇼핑"))
            entries.add(PieEntry(0f, "여가"))
            entries.add(PieEntry(0f, "교통"))
            entries.add(PieEntry(0f, "식비"))
            entries.add(PieEntry(0f, "고정지출"))
            entries.add(PieEntry(0f, "기타"))

            // add a lot of colors
            val colorsItems = ArrayList<Int>()
            for (c in ColorTemplate.VORDIPLOM_COLORS) colorsItems.add(c)
            for (c in ColorTemplate.JOYFUL_COLORS) colorsItems.add(c)
            for (c in COLORFUL_COLORS) colorsItems.add(c)
            for (c in ColorTemplate.LIBERTY_COLORS) colorsItems.add(c)
            for (c in ColorTemplate.PASTEL_COLORS) colorsItems.add(c)
            for (c in ColorTemplate.MATERIAL_COLORS) colorsItems.add(c)
            colorsItems.add(ColorTemplate.getHoloBlue())

            val pieDataSet = PieDataSet(entries, "")


            pieDataSet.apply {
                colors = colorsItems
                valueTextColor = Color.BLACK
                valueTextSize = 32f
            }

            val pieData = PieData(pieDataSet)
            fragmentConsumeAnalysisChart.apply {
                data = pieData
                description.isEnabled = false
                legend.isEnabled = false
                centerText = "소비 패턴"
                setCenterTextSize(32f)
                setEntryLabelColor(Color.BLACK)
                animateY(1400, Easing.EaseInOutQuad)
                animate()
            }
        }
    }



    fun init(){
        consumeAnalysisFragmentViewModel.searchMonths()
        (requireActivity() as MainActivity).showLoadingDialog(requireContext())
        chartInit()
        with(binding){
            fragmentConsumeAnalysisBackButton.setOnClickListener {
                findNavController().navigateUp()
            }
            niceSpinner.setItems("없음")
            niceSpinner.setOnItemSelectedListener { view, position, id, item ->
                consumeAnalysisFragmentViewModel.getAnalysis(UUID.fromString(App.sharedPreferences.getString("id")), item.toString())
                (requireActivity() as MainActivity).showLoadingDialog(requireContext())
            }
        }


        consumeAnalysisFragmentViewModel.exceptionHandler.observe(viewLifecycleOwner){
            when(it){
                0 -> {
                    showSnackbar("네트워크 오류")
                    findNavController().navigate(R.id.action_ConsumeAnalysisFragment_to_ErrorFragment)
                }
                401 ->{
                    showSnackbar("토큰 값이 만료되었습니다.")
                    findNavController().navigate(R.id.action_ConsumeAnalysisFragment_to_ErrorFragment)
                }
            }

        }
        consumeAnalysisFragmentViewModel.monthList.observe(viewLifecycleOwner){
            (requireActivity() as MainActivity).dismissLoadingDialog()
            binding.niceSpinner.setItems(it)
            consumeAnalysisFragmentViewModel.getRecentData()
            (requireActivity() as MainActivity).showLoadingDialog(requireContext())

        }
        consumeAnalysisFragmentViewModel.updateSuccess.observe(viewLifecycleOwner){
            (requireActivity() as MainActivity).dismissLoadingDialog()
            if(!consumeAnalysisFragmentViewModel.monthList.value.isNullOrEmpty()){
                consumeAnalysisFragmentViewModel.getAnalysis(UUID.fromString(App.sharedPreferences.getString("id")), consumeAnalysisFragmentViewModel.monthList.value!![binding.niceSpinner.selectedIndex])
                (requireActivity() as MainActivity).showLoadingDialog(requireContext())
            }

        }
        consumeAnalysisFragmentViewModel.analysisList.observe(viewLifecycleOwner){
            var sum = 0f
            for(data in it){
                sum += data.price
            }
            binding.fragmentConsumeAnalysisAmountTextView.text = StringFormatUtil.moneyToWon(sum.toInt())
            val entries = ArrayList<PieEntry>()

            for(data in it){
                if(data.price > 0){
                    entries.add(PieEntry(data.price/sum *100f, "${data.categoryName}"))
                    when(data.categoryName){
                        "식품" -> {
                            binding.fragmentConsumeAnalysisFoodAmountTextView.text = StringFormatUtil.moneyToWon(data.price)
                            binding.fragmentConsumeAnalysisFoodAmountPercentTextView.text = "${data.price/sum * 100f} %"
                        }
                        "교통" -> {
                            binding.fragmentConsumeAnalysisTrafficAmountTextView.text = StringFormatUtil.moneyToWon(data.price)
                            binding.fragmentConsumeAnalysisTrafficAmountPercentTextView.text = "${data.price/sum * 100f} %"
                        }
                        "여가" -> {
                            binding.fragmentConsumeAnalysisLeisureAmountTextView.text = StringFormatUtil.moneyToWon(data.price)
                            binding.fragmentConsumeAnalysisLeisureAmountPercentTextView.text = "${data.price/sum * 100f} %"
                        }
                        "쇼핑" -> {
                            binding.fragmentConsumeAnalysisShoppingAmountTextView.text = StringFormatUtil.moneyToWon(data.price)
                            binding.fragmentConsumeAnalysisShoppingAmountPercentTextView.text = "${data.price/sum * 100f} %"

                        }
                        "고정지출" -> {
                            binding.fragmentConsumeAnalysisFixedAmountTextView.text = StringFormatUtil.moneyToWon(data.price)
                            binding.fragmentConsumeAnalysisFixedAmountPercentTextView.text = "${data.price/sum * 100f} %"
                        }
                        "기타" -> {
                            binding.fragmentConsumeAnalysisEtcAmountTextView.text = StringFormatUtil.moneyToWon(data.price)
                            binding.fragmentConsumeAnalysisEtcAmountPercentTextView.text = "${data.price/sum * 100f} %"
                        }
                    }
                }
                else{
                    when(data.categoryName){
                        "식품" -> {
                            binding.fragmentConsumeAnalysisFoodAmountTextView.text = StringFormatUtil.moneyToWon(data.price)
                            binding.fragmentConsumeAnalysisFoodAmountPercentTextView.text = "0%"
                        }
                        "교통" -> {
                            binding.fragmentConsumeAnalysisTrafficAmountTextView.text = StringFormatUtil.moneyToWon(data.price)
                            binding.fragmentConsumeAnalysisTrafficAmountPercentTextView.text = "0%"
                        }
                        "여가" -> {
                            binding.fragmentConsumeAnalysisLeisureAmountTextView.text = StringFormatUtil.moneyToWon(data.price)
                            binding.fragmentConsumeAnalysisLeisureAmountPercentTextView.text = "0%"
                        }
                        "쇼핑" -> {
                            binding.fragmentConsumeAnalysisShoppingAmountTextView.text = StringFormatUtil.moneyToWon(data.price)
                            binding.fragmentConsumeAnalysisShoppingAmountPercentTextView.text = "0%"

                        }
                        "고정지출" -> {
                            binding.fragmentConsumeAnalysisFixedAmountTextView.text = StringFormatUtil.moneyToWon(data.price)
                            binding.fragmentConsumeAnalysisFixedAmountPercentTextView.text = "0%"
                        }
                        "기타" -> {
                            binding.fragmentConsumeAnalysisEtcAmountTextView.text = StringFormatUtil.moneyToWon(data.price)
                            binding.fragmentConsumeAnalysisEtcAmountPercentTextView.text = "0%"
                        }
                    }
                }

            }
            val colorsItems = ArrayList<Int>()
            for (c in ColorTemplate.VORDIPLOM_COLORS) colorsItems.add(c)
            for (c in ColorTemplate.JOYFUL_COLORS) colorsItems.add(c)
            for (c in COLORFUL_COLORS) colorsItems.add(c)
            for (c in ColorTemplate.LIBERTY_COLORS) colorsItems.add(c)
            for (c in ColorTemplate.PASTEL_COLORS) colorsItems.add(c)
            for (c in ColorTemplate.MATERIAL_COLORS) colorsItems.add(c)
            colorsItems.add(ColorTemplate.getHoloBlue())
            val pieDataSet = PieDataSet(entries, "")


            pieDataSet.apply {
                colors = colorsItems
                valueTextColor = Color.BLACK
                valueTextSize = 12f
            }
            val pieData = PieData(pieDataSet)
            binding.fragmentConsumeAnalysisChart.apply {
                data = pieData
                description.isEnabled = false
                legend.isEnabled = false
                centerText = "소비 패턴"
                setCenterTextSize(32f)
                setEntryLabelColor(Color.BLACK)
                animateY(1400, Easing.EaseInOutQuad)
                animate()
            }
            (requireActivity() as MainActivity).dismissLoadingDialog()
        }


    }


}