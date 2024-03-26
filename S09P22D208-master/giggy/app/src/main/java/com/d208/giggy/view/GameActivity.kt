package com.d208.giggy.view

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.unity3d.player.UnityPlayer
import com.unity3d.player.UnityPlayerActivity
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "GameActivity giggy"
class GameActivity : UnityPlayerActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        Toast.makeText(this, "${intent.getStringExtra("userId")}", Toast.LENGTH_SHORT).show()
        UnityPlayer.UnitySendMessage("GameManager", "getData", intent.getStringExtra("userId"))
        val unityActivity =  UnityPlayer.currentActivity
        val gameIntent = unityActivity.intent
        Log.d(TAG, "onCreate: ${gameIntent.getStringExtra("Score")}")
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    override fun onPause() {
        super.onPause()
//        val score = UnityPlayer.UnitySendMessage("GameManager", "getScore", "")
//        Log.d(TAG, "onPause: $score 점")

    }


    override fun onDestroy() {
        super.onDestroy()
        val resultIntent = Intent()
        resultIntent.putExtra("data", "1")
        setResult(RESULT_OK, resultIntent)
        Log.d("게임", "꺼짐")

    }

}