package com.d208.giggy.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.d208.giggy.R
import com.d208.giggy.adapter.GameRankAdapter
import com.d208.giggy.base.BaseFragment
import com.d208.giggy.databinding.FragmentGameBinding
import com.d208.giggy.utils.GameInteractionListener
import com.d208.giggy.viewmodel.GameFragmentViewModel
import com.unity3d.player.UnityPlayer
import com.unity3d.player.UnityPlayerActivity
import com.unity3d.player.UnityPlayerActivity.RESULT_OK
import dagger.hilt.android.AndroidEntryPoint


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

private const val TAG = "GameFragment giggy"

@AndroidEntryPoint
class GameFragment : BaseFragment<FragmentGameBinding>(FragmentGameBinding::bind, R.layout.fragment_game) {

    private lateinit var adapter : GameRankAdapter
    private val gameFragmentViewModel : GameFragmentViewModel by viewModels()
    private var gameInteractionListener: GameInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is GameInteractionListener) {
            gameInteractionListener = context
        } else {
            throw ClassCastException("$context must implement GameInteractionListener")
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: 뷰 시작")
        adapter = GameRankAdapter(requireContext())
        init()
    }

    override fun onResume() {
        super.onResume()
        init()
    }

    fun init() = with(binding){

        gameFragmentViewModel.getGameRank()
        gameFragmentViewModel.getMyGameRank()
        gameFragmentViewModel.gameRankList.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
        gameFragmentViewModel.myGameRank.observe(viewLifecycleOwner){
            binding.apply {
                fragmentGameCurrentMyRankTextView.text = "${it.rank} 위"
                fragmentGameCurrentLifeTextView.text = "${it.leftLife} 회"
                fragmentGameCurrentMyHighestScoreTextView.text = "${it.score} 점"

            }
        }

        fragmentGameRankTop10RecyclerView.adapter = adapter
        fragmentGameRankTop10RecyclerView.layoutManager = LinearLayoutManager(requireContext())

        gameStart.setOnClickListener {
            if(gameFragmentViewModel.myGameRank.value?.leftLife ?: 0 > 0){
                gameInteractionListener?.startGame()
            }
            else{
                showSnackbar("게임을 시작 할 수 없습니다.")
            }

        }
        fragmentGameBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }



}