package com.d208.giggy.view

import android.content.ContentValues
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.d208.giggy.R
import com.d208.giggy.base.BaseActivity
import com.d208.giggy.databinding.ActivityMainBinding
import com.d208.giggy.di.App
import com.d208.giggy.utils.GameInteractionListener
import com.d208.giggy.viewmodel.MainActivityViewModel

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.kakao.sdk.common.util.Utility
import com.unity3d.player.UnityPlayerActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate),
    GameInteractionListener {
    private val mainActivityViewModel : MainActivityViewModel by viewModels()
    private lateinit var navController: NavController
    var waitTime = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//

        val keyHash = Utility.getKeyHash(this)
        Log.e("Key", "keyHash: $keyHash")
//        /** KakaoSDK init */
////        KakaoSdk.init(this, this.getString(R.string.kakao_app_key))

        // 액티비티를 세로모드로 고정
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setupNavHost()


        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(ContentValues.TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast

            if (token != null) {
                mainActivityViewModel.fcmToken = token
                Log.d(ContentValues.TAG, "토큰 생성: ${mainActivityViewModel.fcmToken}")
            }
//            Log.d(TAG, msg)1111
//            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })
        initDynamicLink()
    }

    override fun startGame() {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("userId", App.sharedPreferences.getString("id"))
        startActivityForResult(intent, UnityPlayerActivity.UNITY_PLAYER_REQUEST_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        showSnackbar("${data!!.getStringExtra("data")}")

        if (requestCode == UnityPlayerActivity.UNITY_PLAYER_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "꺼짐", Toast.LENGTH_SHORT).show()

            }
        }
    }
    private fun setupNavHost() {
        // NavHostFragment를 가져와서 설정합니다.
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController
    }
    private fun initDynamicLink() {
        val dynamicLinkData = intent.extras
        if (dynamicLinkData != null) {
            var dataStr = "DynamicLink 수신받은 값\n"
            for (key in dynamicLinkData.keySet()) {
                dataStr += "key: $key / value: ${dynamicLinkData.getString(key)}\n"
            }

            Log.d(ContentValues.TAG, "알림: $dataStr")
        }
    }
    override fun onBackPressed() {
        try {
            if(navController.currentDestination?.id == R.id.HomeFragment){
//                finishAffinity()
                if (System.currentTimeMillis() - waitTime >= 1500) {
                    waitTime = System.currentTimeMillis()
                    Toast.makeText(
                        this,
                        "뒤로가기 버튼을 한번 더 누르면 종료됩니다",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    finishAffinity() // 액티비티 종료
                }
            }
            else if (onBackPressedDispatcher.hasEnabledCallbacks()) {
                super.onBackPressed()
            } else {
                when (navController.currentDestination?.id) {
                    R.id.LoginFragment -> {
                        finishAffinity()
                        if (System.currentTimeMillis() - waitTime >= 1500) {
                            waitTime = System.currentTimeMillis()
                            Toast.makeText(
                                this,
                                "뒤로가기 버튼을 한번 더 누르면 종료됩니다",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            finishAffinity() // 액티비티 종료
                        }
                    }
                    null -> super.onBackPressed()
                    else -> super.onBackPressed()
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }


}