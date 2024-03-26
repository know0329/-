package com.d208.giggy.view

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
import com.d208.giggy.databinding.FragmentTransactionDetailBinding
import com.d208.giggy.viewmodel.MainActivityViewModel
import com.d208.giggy.viewmodel.TransactionDetailFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "TransactionDetailFragme giggy"
// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TransactionDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class TransactionDetailFragment : BaseFragment<FragmentTransactionDetailBinding>(FragmentTransactionDetailBinding::bind, R.layout.fragment_transaction_detail) {

    private val transactionDetailFragmentViewModel : TransactionDetailFragmentViewModel by viewModels()
    private val mainActivityViewModel : MainActivityViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }
    fun init() = with(binding){
        fragmentTransactionDetailCategoryChooseButton.setOnClickListener {
            val categoryChooseDialog = CategoryChooseDialog(requireContext(), mainActivityViewModel.selectedTransaction!!.category, this@TransactionDetailFragment)
            categoryChooseDialog.show()
        }
        fragmentTransactionDetailBack.setOnClickListener {
            findNavController().navigateUp()
        }
        if(mainActivityViewModel.selectedTransaction!!.transactionType == "출금"){
            fragmentTransactionDetailAmountTextView.text = " - ${StringFormatUtil.moneyToWon(mainActivityViewModel.selectedTransaction!!.withdraw)}"
        }
        else{
            fragmentTransactionDetailAmountTextView.text = " + ${StringFormatUtil.moneyToWon(mainActivityViewModel.selectedTransaction!!.deposit)}"
        }

        fragmentTransactionDetailCategoryTextView.text = mainActivityViewModel.selectedTransaction!!.category
        fragmentTransactionDetailContentTextView.text = mainActivityViewModel.selectedTransaction!!.content
        fragmentTransactionDetailTransactionDateTextView.text = StringFormatUtil.dateTimeToString(mainActivityViewModel.selectedTransaction!!.transactionDate)


    }

    fun updateCategory(category : String){
        Log.d(TAG, "updateCategory: $category")
        mainActivityViewModel.selectedTransaction!!.category = category
        transactionDetailFragmentViewModel.updateCategory(mainActivityViewModel.selectedTransaction!!)
        transactionDetailFragmentViewModel.updateSuccess.observe(viewLifecycleOwner){
            if(it){
                binding.fragmentTransactionDetailCategoryTextView.text = category
            }
        }
    }

}