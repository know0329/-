package com.d208.giggy.view

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.d208.domain.utils.StringFormatUtil
import com.d208.giggy.R
import com.d208.giggy.adapter.BeggerRankAdapter
import com.d208.giggy.base.BaseFragment
import com.d208.giggy.databinding.FragmentRankBinding
import com.d208.giggy.viewmodel.MainActivityViewModel
import com.d208.giggy.viewmodel.RankFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class RankFragment : BaseFragment<FragmentRankBinding>(FragmentRankBinding::bind, R.layout.fragment_rank) {

    private val rankFragmentViewModel : RankFragmentViewModel by viewModels()
    private val mainActivityViewModel : MainActivityViewModel by activityViewModels()
    private lateinit var adapter : BeggerRankAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = BeggerRankAdapter()
        init()
    }
    fun init() {

        rankFragmentViewModel.getBeggerRank()
        rankFragmentViewModel.getMyBeggerRank()
        rankFragmentViewModel.beggerRankList.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
        rankFragmentViewModel.myBeggerRank.observe(viewLifecycleOwner){
            binding.apply {
                fragmentRankCurrentMyRankTextView.text = "${it.rank} 위"
                fragmentRankCurrentAmountTextView.text = StringFormatUtil.moneyToWon(mainActivityViewModel.user.currentAmount)
                fragmentRankTargetAmountTextView.text = StringFormatUtil.moneyToWon(mainActivityViewModel.user.targetAmount)
                val amountPercent = (100f - (mainActivityViewModel.user.currentAmount.toFloat() / mainActivityViewModel.user.targetAmount) * 100f).toInt()
                ObjectAnimator.ofInt(fragmentRankProgressBar, "progress", (it.ratio*100).toInt())
                    .setDuration(500)
                    .start()
            }
        }
        with(binding){
            fragmentRankRankTop10RecyclerView.adapter = adapter
            fragmentRankRankTop10RecyclerView.layoutManager = LinearLayoutManager(requireContext())

            fragmentRankBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }
}