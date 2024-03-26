package com.d208.domain.model

import java.util.UUID

data class DomainComment(val id : Long, val userId : UUID, val nickName : String, var content : String, val createdAt : Long,)
