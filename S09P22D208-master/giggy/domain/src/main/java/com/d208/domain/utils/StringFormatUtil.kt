package com.d208.domain.utils

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Locale

class StringFormatUtil {
    companion object {

        fun dateToString(date: Long): String {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
            return dateFormat.format(date)
        }

        fun dateTimeToString(date: Long): String {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA)
            return dateFormat.format(date)
        }

        fun moneyToWon(money: Int): String {
            return "${DecimalFormat("#,###").format(money)} 원"
        }
    }
}
