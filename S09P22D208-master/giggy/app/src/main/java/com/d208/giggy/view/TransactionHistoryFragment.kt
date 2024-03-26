package com.d208.giggy.view

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.d208.domain.model.DomainTransaction
import com.d208.domain.utils.StringFormatUtil
import com.d208.giggy.R
import com.d208.giggy.base.BaseFragment
import com.d208.giggy.databinding.FragmentTransactionHistoryBinding
import com.d208.giggy.databinding.ItemTransactionBinding
import com.d208.giggy.di.App
import com.d208.giggy.viewmodel.MainActivityViewModel
import com.d208.giggy.viewmodel.TransactionHistoryFragmentViewModel
import com.d208.presentation.adapter.TransactionAdapater
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener
import java.security.SecureRandom.getInstance
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import androidx.core.util.Pair
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.DateValidatorPointForward
import dagger.hilt.android.AndroidEntryPoint

import java.util.UUID

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TransactionHistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val TAG = "TransactionHistoryFragm giggy"
@AndroidEntryPoint
class TransactionHistoryFragment : BaseFragment<FragmentTransactionHistoryBinding>(
    FragmentTransactionHistoryBinding::bind, R.layout.fragment_transaction_history) {


    private lateinit var adapter : TransactionAdapater
    private val transactionHistoryFragmentViewModel : TransactionHistoryFragmentViewModel by viewModels()
    private val mainActivityViewModel : MainActivityViewModel by activityViewModels()
    private lateinit var startDate : String
    private var endDate = StringFormatUtil.dateToString(System.currentTimeMillis())


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = TransactionAdapater(requireContext())
        startDate = StringFormatUtil.dateToString(changeDate(mainActivityViewModel.user.registerDate!!))
        transactionHistoryFragmentViewModel.getRecentData()
        init()

    }

    fun changeDate(currentTime : Long) : Long{
        val calendar = Calendar.getInstance();
        calendar.timeInMillis = currentTime

        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        val newDate: Date = calendar.time

        // Date 객체를 다시 Long 값으로 변환
        val newTimestamp: Long = newDate.time
        return newTimestamp
    }

    fun spinnerInit(){
        with(binding){
            fragmentTransactionHistorySpinner.setItems("전체 내역", "식품 내역", "교통 내역", "여가 내역", "쇼핑 내역", "고정지출 내역",  "기타 내역")
            fragmentTransactionHistorySpinner.setOnItemSelectedListener { view, position, id, item ->
                view.text = item.toString()
                when(item){
                    "전체 내역"->{
                        adapter.submitList(transactionHistoryFragmentViewModel.transactionList.value)
                    }
                    "식품 내역" ->{
                        adapter.submitList(transactionHistoryFragmentViewModel.transactionList.value?.filter { it.category == "식품" })
                    }
                    "교통 내역" ->{
                        adapter.submitList(transactionHistoryFragmentViewModel.transactionList.value?.filter { it.category == "교통" })
                    }
                    "여가 내역" ->{
                        adapter.submitList(transactionHistoryFragmentViewModel.transactionList.value?.filter { it.category == "여가" })
                    }
                    "쇼핑 내역" ->{
                        adapter.submitList(transactionHistoryFragmentViewModel.transactionList.value?.filter { it.category == "쇼핑" })
                    }
                    "고정지출 내역"-> {
                        adapter.submitList(transactionHistoryFragmentViewModel.transactionList.value?.filter { it.category == "고정지출" })
                    }
                    "기타 내역" ->{
                        adapter.submitList(transactionHistoryFragmentViewModel.transactionList.value?.filter { it.category == "기타" })
                    }
                }
            }
            fragmentTransactionHistorySpinner.setOnNothingSelectedListener {

            }

        }
    }

    fun recyclerViewInit() = with(binding) {
        adapter.apply {
            itemClickListener = object : TransactionAdapater.ItemClickListener{
                override fun onClick(
                    binding: ItemTransactionBinding,
                    position: Int,
                    data: DomainTransaction
                ) {
                    mainActivityViewModel.selectedTransaction = data
                    findNavController().navigate(R.id.action_TransactionHistoryFragment_to_TransactionDetailFragment)
                }

            }
        }
        fragmentTransactionHistoryRecyclerView.adapter = adapter
        fragmentTransactionHistoryRecyclerView.layoutManager =LinearLayoutManager(requireContext())
    }

    fun init(){

        recyclerViewInit()
        spinnerInit()
        binding.fragmentTransactionHistoryBack.setOnClickListener {
            findNavController().navigateUp()
        }

        transactionHistoryFragmentViewModel.updateSuccess.observe(viewLifecycleOwner){
            transactionHistoryFragmentViewModel.getTransactionData(UUID.fromString(App.sharedPreferences.getString("id")), startDate, endDate)
        }


        (requireActivity() as MainActivity).showLoadingDialog(requireContext())
        transactionHistoryFragmentViewModel.transactionList.observe(viewLifecycleOwner){
            (requireActivity() as MainActivity).dismissLoadingDialog()
            if(it.isEmpty()){
                showSnackbar("불러올 거래내역이 없습니다.")
            }
            else{
                App.sharedPreferences.updateMoney(it[0].amount)
                adapter.submitList(it)
            }
        }

        // 날짜 변경
        binding.fragmentTransactionHistoryChangeDateButton.setOnClickListener {
            Log.d(TAG, "init: ${mainActivityViewModel.user.registerDate!!}")
           val dateRangePicker =  MaterialDatePicker.Builder.dateRangePicker()
               .setTitleText("거래 내역 구간 설정")
               .setCalendarConstraints(CalendarConstraints.Builder().setValidator(
                   DateValidatorPointBackward.now()
               ).build())
//               .setCalendarConstraints(CalendarConstraints.Builder().setValidator(
//                   DateValidatorPointForward.from(mainActivityViewModel.user.registerDate!!)
//               ).build())
               .build()
            dateRangePicker.show(childFragmentManager, "date_picker")
            dateRangePicker.addOnPositiveButtonClickListener { selection ->
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = selection?.first ?: 0
                startDate = SimpleDateFormat("yyyy-MM-dd").format(calendar.time).toString()
                Log.d("start", startDate)

                calendar.timeInMillis = selection?.second ?: 0
                endDate = SimpleDateFormat("yyyy-MM-dd").format(calendar.time).toString()
                Log.d("end", endDate)
                transactionHistoryFragmentViewModel.getTransactionData(
                    UUID.fromString(
                        App.sharedPreferences.getString(
                            "id"
                        )
                    ), startDate, endDate
                )
            }

        }
        transactionHistoryFragmentViewModel.exceptionHandler.observe(viewLifecycleOwner){
            when(it){
                0 -> {
                    showSnackbar("네트워크 오류")
                    findNavController().navigate(R.id.action_TransactionHistoryFragment_to_ErrorFragment)
                }
                401 ->
                {
                    showSnackbar("토큰 값이 만료되었습니다.")
                    findNavController().navigate(R.id.action_TransactionHistoryFragment_to_ErrorFragment)
                }
            }
        }
    }


}