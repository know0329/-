package com.d208.giggy.view



import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.d208.domain.model.DomainPost
import com.d208.giggy.R
import com.d208.giggy.base.BaseFragment
import com.d208.giggy.databinding.FragmentCommunityHomeBinding
import com.d208.giggy.databinding.ItemPostBinding

import com.d208.giggy.viewmodel.CommunityHomeFragmentViewModel
import com.d208.giggy.viewmodel.MainActivityViewModel
import com.d208.presentation.adapter.PostAdapter
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CommunityHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val TAG = "CommunityHomeFragment giggy"
@AndroidEntryPoint
class CommunityHomeFragment : BaseFragment<FragmentCommunityHomeBinding>(FragmentCommunityHomeBinding::bind, R.layout.fragment_community_home) {
    // TODO: Rename and change types of parameters

    private lateinit var adapter : PostAdapter
    private val communityHomeFragmentViewModel : CommunityHomeFragmentViewModel by viewModels()
    private val mainActivityViewModel : MainActivityViewModel by activityViewModels()
    private var list = mutableListOf<DomainPost>()
    private var filterList = mutableListOf<DomainPost>()
    private var isFabOpen = false
    private var fab_open: Animation? = null
    private var fab_close:Animation? = null
    var foodChecked = false
    var trafficChecked = false
    var leisureChecked = false
    var shoppingChecked = false
    var fixedChecked = false
    var selfdevChecked = false
    var etcChecked = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivityViewModel.postUpdateData = null
        fab_open = AnimationUtils.loadAnimation(requireContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(requireContext(), R.anim.fab_close);
        adapter = PostAdapter(requireContext())
        init()
    }


    fun init() {
        spinnerInit()
        recyclerViewInit()
        tabInit()

        with(binding){

            //fab

            fragmentCommunityHomeMainFAB.setOnClickListener {
                toggleFab()
            }

            fragmentCommunityHomeFAB.setOnClickListener {
                findNavController().navigate(R.id.action_CommunityHomeFragment_to_CommunityPostRegisterFragment)
            }
            fragmentCommunityHomeSearchFAB.setOnClickListener {
                if(fragmentCommunityHomeSearchBar.visibility == View.VISIBLE)
                    fragmentCommunityHomeSearchBar.visibility = View.GONE
                else
                    fragmentCommunityHomeSearchBar.visibility = View.VISIBLE
            }


            fragmentCommunityHomeFoodChip.setOnClickListener {
                foodChecked = !foodChecked
                categoryFilterList(foodChecked, "FOOD")
            }
            fragmentCommunityHomeTrafficChip.setOnClickListener {
                trafficChecked = !trafficChecked
                categoryFilterList(trafficChecked, "TRAFFIC")
            }
            fragmentCommunityHomeLeisureChip.setOnClickListener {
                leisureChecked = !leisureChecked
                categoryFilterList(leisureChecked, "LEISURE")
            }
            fragmentCommunityHomeShoppingChip.setOnClickListener {
                shoppingChecked = !shoppingChecked
                categoryFilterList(shoppingChecked, "SHOPPING")
            }
            fragmentCommunityHomeFixedChip.setOnClickListener {
                fixedChecked = !fixedChecked
                categoryFilterList(fixedChecked, "FIXED")
            }
            fragmentCommunityHomeSelfdevChip.setOnClickListener {
                selfdevChecked = !selfdevChecked
                categoryFilterList(selfdevChecked, "SELFDEV")
            }
            fragmentCommunityHomeEtcChip.setOnClickListener {
                etcChecked = !etcChecked
                categoryFilterList(etcChecked, "ETC")
            }
            // search bar
            fragmentCommunityHomeSearchBar.searchEditText.addTextChangedListener(searchWatcher)


        }

        communityHomeFragmentViewModel.getPosts()
        communityHomeFragmentViewModel.postList.observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                adapter.submitList(it)
                list = it
            }
            else{
                adapter.submitList(it)
                showSnackbar("불러올 게시글이 없습니다.")
            }
        }

    }

    fun recyclerViewInit() = with(binding) {

        fragmentCommunityHomeRecyclerView.adapter = adapter
        fragmentCommunityHomeRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter.apply {
            itemClickListener = object : PostAdapter.ItemClickListener{

                override fun onClick(binding: ItemPostBinding, position: Int, data: DomainPost) {
                    mainActivityViewModel.seletedPostId = data.id
                    findNavController().navigate(R.id.action_CommunityHomeFragment_to_CommunityPostDetailFragment)
                }

            }
            itemHeartListener = object : PostAdapter.ItemClickListener{
                override fun onClick(binding: ItemPostBinding, position: Int, data: DomainPost) {
                   communityHomeFragmentViewModel.pushLike(data.id)
                }

            }
        }
    }

    fun tabInit() = with(binding){
        fragmentCommunityHomeTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {

                // Handle tab select
                if (tab != null) {
                    when(tab.text.toString()){
                        "자유" -> {
                            communityHomeFragmentViewModel.getPostsByPostType("FREE")
                        }

                        "꿀팁" -> {
                            communityHomeFragmentViewModel.getPostsByPostType("TIP")
                        }

                        "자랑" -> {
                            communityHomeFragmentViewModel.getPostsByPostType("BOAST")
                        }

                        else -> {
                           communityHomeFragmentViewModel.getPosts()
                        }
                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselect
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Handle tab unselect
            }
        })

    }
    fun spinnerInit() {
        with(binding){
            fragmentCommunityHomeSpinner.setItems("최신 순", "조회 순", "오래된 순", "좋아요 순")
            fragmentCommunityHomeSpinner.setOnItemSelectedListener { view, position, id, item ->
                view.text = item.toString()
                if(filterList.isNotEmpty())
                    adapter.submitList(orderList(filterList, item.toString()))
                else
                    adapter.submitList(orderList(list, item.toString()))

            }
            fragmentCommunityHomeSpinner.setOnNothingSelectedListener {

            }


        }

    }


    fun newPostTypeList(oldlist : MutableList<DomainPost>, postType : String) : MutableList<DomainPost>{

        if(postType == "전체"){
            return list
        }
        else{
            return oldlist.filter {
                when(postType){
                    "자유" -> {
                        it.postType == "FREE"
                    }
                    "꿀팁" -> {
                        it.postType == "TIP"
                    }
                    "자랑" -> {
                        it.postType == "BOAST"
                    }

                    else -> {
                        it.postType=="FREE" ||it.postType=="TIP" || it.postType =="BOAST"
                    }
                }

            } as MutableList<DomainPost>
        }


    }

    fun orderList(oldlist : MutableList<DomainPost>, orderType : String) : List<DomainPost>{


        when(orderType) {
            "좋아요 순" -> {
                return oldlist.sortedByDescending { it.likeCount }
            }
            "조회 순" -> {

                return oldlist.sortedByDescending { it.viewCount }
            }
            "오래된 순"-> {

                return oldlist.sortedBy { it.createdAt }
            }
            "최신 순" -> {

                return oldlist.sortedByDescending { it.createdAt }
            }
        }
        return oldlist
    }
    fun categoryFilterList(flag : Boolean, category : String){
        var newList = mutableListOf<DomainPost>()
        if(flag){
            filterList.addAll(list.filter { it.category == category })
            newList.addAll(filterList)
            adapter.submitList(newList)
        }
        else{
            filterList.removeAll(filterList.filter { it.category == category })
            newList.addAll(filterList)
            if(newList.isEmpty()){
                if(list.isNotEmpty()){
                    adapter.submitList(list)
                }
                else{
                    adapter.submitList(mutableListOf())
                }

            }
            else{
                adapter.submitList(newList)
            }
        }
    }

    private val searchWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // 텍스트가 변경되기 전에 호출됩니다.
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            PostSearch(s.toString())
        }

        override fun afterTextChanged(s: Editable?) {

        }
    }
    private fun PostSearch(title: String) {
        var newList = mutableListOf<DomainPost>()
        if(filterList.isEmpty())
            newList = list.filter { it -> it.title.contains(title) } as MutableList<DomainPost>
        else
            newList = filterList.filter { it -> it.title.contains(title) } as MutableList<DomainPost>
        adapter.submitList(newList)
    }
    private fun toggleFab() {
        if (isFabOpen) {
            binding.fragmentCommunityHomeMainFAB.setImageResource(R.drawable.ic_post_fab)
            binding.fragmentCommunityHomeSearchFAB.startAnimation(fab_close)
            binding.fragmentCommunityHomeFAB.startAnimation(fab_close)
            binding.fragmentCommunityHomeSearchFAB.setClickable(false)
            binding.fragmentCommunityHomeFAB.setClickable(false)
            binding.fragmentCommunityHomeSearchBar.searchEditText.setText("")
            binding.fragmentCommunityHomeSearchBar.visibility = View.GONE
            isFabOpen = false
        } else {
            binding.fragmentCommunityHomeMainFAB.setImageResource(R.drawable.ic_close)
            binding.fragmentCommunityHomeSearchFAB.startAnimation(fab_open)
            binding.fragmentCommunityHomeFAB.startAnimation(fab_open)
            binding.fragmentCommunityHomeSearchFAB.setClickable(true)
            binding.fragmentCommunityHomeFAB.setClickable(true)
            isFabOpen = true
        }
    }



}