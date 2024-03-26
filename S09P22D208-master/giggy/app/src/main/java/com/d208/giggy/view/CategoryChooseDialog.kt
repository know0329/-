package com.d208.giggy.view

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.d208.giggy.R

class CategoryChooseDialog(context : Context, var currentCategory : String, var fragment : Fragment) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_category_choose)

        val food = findViewById<LinearLayout>(R.id.dialog_category_choose_food_layout)
        val traffic = findViewById<LinearLayout>(R.id.dialog_category_choose_traffic_layout)
        val leisure= findViewById<LinearLayout>(R.id.dialog_category_choose_leisure_layout)
        val shopping = findViewById<LinearLayout>(R.id.dialog_category_choose_shopping_layout)
        val fixed = findViewById<LinearLayout>(R.id.dialog_category_choose_fixed_layout)
        val etc = findViewById<LinearLayout>(R.id.dialog_category_choose_etc_layout)

        when(currentCategory){
            "식품" -> food.setBackgroundColor(Color.parseColor("#D0F5A9"))
            "교통" -> traffic.setBackgroundColor(Color.parseColor("#D0F5A9"))
            "여가" -> leisure.setBackgroundColor(Color.parseColor("#D0F5A9"))
            "고정지출" -> fixed.setBackgroundColor(Color.parseColor("#D0F5A9"))
            "쇼핑" -> shopping.setBackgroundColor(Color.parseColor("#D0F5A9"))
            "기타" -> etc.setBackgroundColor(Color.parseColor("#D0F5A9"))
        }
        food.setOnClickListener {
            (fragment as TransactionDetailFragment).updateCategory("식품")
            dismiss()
        }
        traffic.setOnClickListener {
            (fragment as TransactionDetailFragment).updateCategory("교통")
            dismiss()
        }
        leisure.setOnClickListener {
            (fragment as TransactionDetailFragment).updateCategory("여가")
            dismiss()
        }
        shopping.setOnClickListener {
            (fragment as TransactionDetailFragment).updateCategory("쇼핑")
            dismiss()
        }
        fixed.setOnClickListener {
            (fragment as TransactionDetailFragment).updateCategory("고정지출")
            dismiss()
        }
        etc.setOnClickListener {
            (fragment as TransactionDetailFragment).updateCategory("기타")
            dismiss()
        }



    }
}