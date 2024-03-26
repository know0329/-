package com.d208.domain.model

import java.util.UUID

data class DomainPost(var id : Long,
                      val nickName : String,
                      var createdAt : Long,
                      var title : String,
                      var postType : String,
                      var category : String,
                      var viewCount : Int,
                      var likePost : Boolean,
                      var likeCount : Int
                      )
