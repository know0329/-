package com.d208.giggy.view

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.PopupMenu
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.d208.domain.model.DomainComment
import com.d208.domain.model.DomainPost
import com.d208.domain.utils.StringFormatUtil
import com.d208.giggy.R
import com.d208.giggy.adapter.CommentAdapter
import com.d208.giggy.base.BaseFragment
import com.d208.giggy.databinding.FragmentCommunityPostDetailBinding
import com.d208.giggy.databinding.ItemCommentBinding

import com.d208.giggy.di.App
import com.d208.giggy.viewmodel.CommunityPostDetailFragmentViewModel
import com.d208.giggy.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.w3c.dom.Comment
import java.util.UUID


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [CommunityPostDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val TAG = "CommunityPostDetailFrag giggy"
@AndroidEntryPoint
class CommunityPostDetailFragment : BaseFragment<FragmentCommunityPostDetailBinding>(FragmentCommunityPostDetailBinding::bind, R.layout.fragment_community_post_detail) {

    private val communityPostDetailFragmentViewModel : CommunityPostDetailFragmentViewModel by viewModels()
    private val mainActivityViewModel : MainActivityViewModel by activityViewModels()
    private lateinit var adapter : CommentAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter =  CommentAdapter(requireContext(), UUID.fromString(App.sharedPreferences.getString("id")))
        init()
    }

    fun init() = with(binding){
        communityPostDetailFragmentViewModel.getOnePost(mainActivityViewModel.seletedPostId)
        communityPostDetailFragmentViewModel.getComments(mainActivityViewModel.seletedPostId)
        (requireActivity() as MainActivity).showLoadingDialog(requireContext())
        // 게시글 정보 불러오기
        communityPostDetailFragmentViewModel.post.observe(viewLifecycleOwner){
            (requireActivity() as MainActivity).dismissLoadingDialog()
            if(it != null){
                try{
                    Glide.with(requireContext())
                        .load(it.postPicture)
                        .into(fragmentCommunityPostDetailImageView)
                }catch (e : Exception){
                    Log.d(TAG, "init: ${e.message}")
                    Glide.with(requireContext())
                        .load(R.drawable.default_image)
                        .into(fragmentCommunityPostDetailImageView)
                }
                fragmentCommunityPostDetailTextView.text =  it.title
                fragmentCommunityPostDetailWriterNameTextView.text = it.nickName
                fragmentCommunityPostDetailWriteTimeTextView.text = StringFormatUtil.dateTimeToString(it.createAt)
                fragmentCommunityPostDetailContentTextView.text = it.content
                fragmentCommunityPostDetailCommentCountTextView.text = it.commentCnt.toString()
                Log.d(TAG, "init: $it")
                when(it.category) {
                    "FOOD" -> {
                        Glide.with(requireContext())
                            .load(R.drawable.ic_food)
                            .apply(RequestOptions().override(100, 100))
                            .into(fragmentCommunityPostDetailCategoryImage)
                    }
                    "TRAFFIC" -> {
                        Glide.with(requireContext())
                            .load(R.drawable.ic_traffic)
                            .apply(RequestOptions().override(100, 100))
                            .into(fragmentCommunityPostDetailCategoryImage)
                    }
                    "LEISURE" -> {
                        Glide.with(requireContext())
                            .load(R.drawable.ic_leisure)
                            .apply(RequestOptions().override(100, 100))
                            .into(fragmentCommunityPostDetailCategoryImage)
                    }
                    "SHOPPING" -> {
                        Glide.with(requireContext())
                            .load(R.drawable.ic_shopping)
                            .apply(RequestOptions().override(100, 100))
                            .into(fragmentCommunityPostDetailCategoryImage)
                    }
                    "FIXED" -> {
                        Glide.with(requireContext())
                            .load(R.drawable.ic_fixed_expenses)
                            .apply(RequestOptions().override(100, 100))
                            .into(fragmentCommunityPostDetailCategoryImage)
                    }
                    "SELFDEV" -> {
                        Glide.with(requireContext())
                            .load(R.drawable.ic_selfdev)
                            .apply(RequestOptions().override(100, 100))
                            .into(fragmentCommunityPostDetailCategoryImage)
                    }
                    "ETC" -> {
                        Glide.with(requireContext())
                            .load(R.drawable.ic_etc)
                            .apply(RequestOptions().override(100, 100))
                            .into(fragmentCommunityPostDetailCategoryImage)
                    }

                }
                if(it.userId == UUID.fromString(App.sharedPreferences.getString("id"))){
                    fragmentCommunityPostDetailMenuButton.visibility = View.VISIBLE
                }
            }
        }

        adapter.itemClickListener = object : CommentAdapter.ItemClickListener {
            override fun onClick(binding: ItemCommentBinding, position: Int, data: DomainComment) {
              communityPostDetailFragmentViewModel.deleteComment(mainActivityViewModel.seletedPostId, data.id)
                val updatedList = adapter.currentList.toMutableList()
                updatedList.removeAt(position)
                adapter.submitList(updatedList)
                adapter.notifyItemRemoved(position)
            }


        }
        fragmentCommunityPostDetailCommentRecyclerView.adapter = adapter
        fragmentCommunityPostDetailCommentRecyclerView.layoutManager = LinearLayoutManager(requireContext())


        // 댓글 등록 성공
        communityPostDetailFragmentViewModel.commentRegisterSuccess.observe(viewLifecycleOwner){
            if(it){
                communityPostDetailFragmentViewModel.getComments(mainActivityViewModel.seletedPostId)
                fragmentCommunityPostDetailCommentEditView.setText("")
            }
        }
        //댓글 리스트 가져오기
        communityPostDetailFragmentViewModel.commentList.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
        // 메뉴 버튼
        fragmentCommunityPostDetailMenuButton.setOnClickListener {
            showPopupMenu(it)
        }
        //댓글 작성 하기
        fragmentCommunityPostDetailSendButton.setOnClickListener {
            communityPostDetailFragmentViewModel.registerComment(mainActivityViewModel.seletedPostId, fragmentCommunityPostDetailCommentEditView.text.toString())
        }


    }
    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.menuInflater.inflate(R.menu.post_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.update -> {
                    mainActivityViewModel.postUpdateData = communityPostDetailFragmentViewModel.post.value
                    findNavController().navigate(R.id.action_CommunityPostDetailFragment_to_CommunityPostRegisterFragment)
                    return@setOnMenuItemClickListener true
                }
                R.id.delete -> {
                    communityPostDetailFragmentViewModel.deletePost(mainActivityViewModel.seletedPostId)
                    findNavController().navigateUp()
                    return@setOnMenuItemClickListener true
                }
                // 다른 메뉴 항목에 대한 처리 추가 가능
                else -> return@setOnMenuItemClickListener false
            }
        }

        popupMenu.show()
    }









}