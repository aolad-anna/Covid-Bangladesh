package com.example.covidbangladesh

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import androidx.core.graphics.ColorUtils
import com.example.covidbangladesh.api.ApiClient
import com.example.covidbangladesh.api.ApiInterface
import com.example.covidbangladesh.models.Movie
import kotlinx.android.synthetic.main.activity_pop_up_window.*
import retrofit.Call
import retrofit.Callback
import retrofit.Response
import retrofit.Retrofit
import java.text.DecimalFormat

class PopUpWindow : AppCompatActivity() {
    private var popupTitle = ""
    private var popupText = ""
    private var popupButton = ""
    private var darkStatusBar = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(0, 0)
        setContentView(R.layout.activity_pop_up_window)

        // Get the data
        val bundle = intent.extras
        popupTitle = bundle?.getString("popuptitle", "Title") ?: ""
        popupText = bundle?.getString("popuptext", "Text") ?: ""
        popupButton = bundle?.getString("popupbtn", "Button") ?: ""
        darkStatusBar = bundle?.getBoolean("darkstatusbar", false) ?: false

        // Set the data
        popup_window_title.text = popupTitle
        popup_window_text.text = popupText
        popup_window_button.text = popupButton
        // Set the Status bar appearance for different API levels
        if (Build.VERSION.SDK_INT in 19..20) {
            setWindowFlag(this, true)
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // If you want dark status bar, set darkStatusBar to true
                if (darkStatusBar) {
                    this.window.decorView.systemUiVisibility =
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
                this.window.statusBarColor = Color.TRANSPARENT
                setWindowFlag(this, false)
            }
        }

        // Fade animation for the background of Popup Window
        val alpha = 100 //between 0-255
        val alphaColor = ColorUtils.setAlphaComponent(Color.parseColor("#000000"), alpha)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), Color.TRANSPARENT, alphaColor)
        colorAnimation.duration = 500 // milliseconds
        colorAnimation.addUpdateListener { animator ->
            popup_window_background.setBackgroundColor(animator.animatedValue as Int)
        }
        colorAnimation.start()


        // Fade animation for the Popup Window
        popup_window_view_with_border.alpha = 0f
        popup_window_view_with_border.animate().alpha(1f).setDuration(500).setInterpolator(
            DecelerateInterpolator()
        ).start()


        // Close the Popup Window when you press the button
        popup_window_button.setOnClickListener {
            onBackPressed()
        }
        progress1.visibility=View.VISIBLE
        progress12.visibility=View.VISIBLE
        progress123.visibility=View.VISIBLE
        progress1234.visibility=View.VISIBLE

        val TodayAffect: TextView = findViewById(R.id.TodayCase)
        val TodayDie: TextView = findViewById(R.id.TodayDied)
        val TodayRecovered: TextView = findViewById(R.id.TodayRecover)
        val TodayTested: TextView = findViewById(R.id.TodayTest)

        val apiService = ApiClient.client!!.create(ApiInterface::class.java)
        val call: Call<List<Movie>> = apiService.getMovies()
        call.enqueue(object : Callback<List<Movie>> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(response: Response<List<Movie>>?, retrofit: Retrofit?) {

                if (response?.body() != null) {
                    //recyclerAdapter.setMovieListItems(response.body()!!)
                    val responseData = response?.body()

                    progress1.visibility=View.GONE
                    progress12.visibility=View.GONE
                    progress123.visibility=View.GONE
                    progress1234.visibility=View.GONE

                    TodayAffect.text = response.body()[0].newAffected
                    val number4: String = TodayAffect.text.toString()
                    val amount4 = number4.toDouble()
                    val formatter4 = DecimalFormat("#,###")
                    val formatted4 = formatter4.format(amount4)

                    TodayAffect.text = formatted4

                    TodayDie.text = response.body()[0].newDied
                    val number5: String = TodayDie.text.toString()
                    val amount5 = number5.toDouble()
                    val formatter5 = DecimalFormat("#,###")
                    val formatted5 = formatter5.format(amount5)

                    TodayDie.text = formatted5

                    TodayRecovered.text = response.body()[0].newRecover
                    val number6: String = TodayRecovered.text.toString()
                    val amount6 = number6.toDouble()
                    val formatter6 = DecimalFormat("#,###")
                    val formatted6 = formatter6.format(amount6)

                    TodayRecovered.text = formatted6

                    TodayTested.text = response.body()[0].newTest
                    val number7: String = TodayTested.text.toString()
                    val amount7 = number7.toDouble()
                    val formatter7 = DecimalFormat("#,###")
                    val formatted7 = formatter7.format(amount7)

                    TodayTested.text = formatted7
                }
            }

            override fun onFailure(t: Throwable?) {
                val pp =""
            }

        })

    }

    private fun setWindowFlag(activity: Activity, on: Boolean) {
        val win = activity.window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        } else {
            winParams.flags = winParams.flags and WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS.inv()
        }
        win.attributes = winParams
    }


    override fun onBackPressed() {
        // Fade animation for the background of Popup Window when you press the back button
        val alpha = 100 // between 0-255
        val alphaColor = ColorUtils.setAlphaComponent(Color.parseColor("#000000"), alpha)
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), alphaColor, Color.TRANSPARENT)
        colorAnimation.duration = 500 // milliseconds
        colorAnimation.addUpdateListener { animator ->
            popup_window_background.setBackgroundColor(
                animator.animatedValue as Int
            )
        }

        // Fade animation for the Popup Window when you press the back button
        popup_window_view_with_border.animate().alpha(0f).setDuration(500).setInterpolator(
            DecelerateInterpolator()
        ).start()

        // After animation finish, close the Activity
        colorAnimation.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                finish()
                overridePendingTransition(0, 0)
            }
        })
        colorAnimation.start()
    }


}