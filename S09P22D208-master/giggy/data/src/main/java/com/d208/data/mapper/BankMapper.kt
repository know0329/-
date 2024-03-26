package com.d208.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.d208.data.remote.model.AnalysisResponse
import com.d208.data.remote.model.TransactionResponse
import com.d208.domain.model.DomainAnalysisResponse
import com.d208.domain.model.DomainTransaction
import java.time.ZoneOffset

object BankMapper {

    @RequiresApi(Build.VERSION_CODES.O)
    fun searchTransactionMapper(
        response : List<TransactionResponse>?
    ) : MutableList<DomainTransaction> ?{
        return if (response != null) {
            var list = mutableListOf<DomainTransaction>()
            for(data in response){
                list.add(DomainTransaction(
                    id = data.id,
                    content = data.content,
                    transactionDate = data.transactionDate,
                    transactionType = data.transactionType,
                    category = data.category,
                    amount = data.amount,
                    deposit = data.deposit,
                    withdraw = data.withdraw,
                ))
            }
            list
        } else mutableListOf()
    }
    fun searchMonths(
        response : MutableList<String>?

    ) : MutableList<String>? {
        return response
    }

    fun getAnalysis(
        response : MutableList<AnalysisResponse>?
    ) : MutableList<DomainAnalysisResponse>? {
        return if(response != null){
            var list = mutableListOf<DomainAnalysisResponse>()
            for(data in response){
                list.add(DomainAnalysisResponse(
                    categoryName = data.categoryName,
                    price = data.price,
                ))
            }
            list
        } else response
    }
}