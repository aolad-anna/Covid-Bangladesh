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
import kotlinx.android.synthetic.main.activity_pop_up_window.popup_window_background
import kotlinx.android.synthetic.main.activity_pop_up_window.popup_window_button
import kotlinx.android.synthetic.main.activity_pop_up_window.popup_window_text
import kotlinx.android.synthetic.main.activity_pop_up_window.popup_window_title
import kotlinx.android.synthetic.main.activity_pop_up_window.popup_window_view_with_border
import kotlinx.android.synthetic.main.activity_pop_up_window1.*
import retrofit.Call
import retrofit.Callback
import retrofit.Response
import retrofit.Retrofit
import java.text.DecimalFormat

class PopUpWindow1 : AppCompatActivity() {
    private var popupTitle = ""
    private var popupText = ""
    private var popupButton = ""
    private var darkStatusBar = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(0, 0)
        setContentView(R.layout.activity_pop_up_window1)

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
        progress9.visibility=View.VISIBLE
        progress98.visibility=View.VISIBLE
        progress987.visibility=View.VISIBLE
        progress9876.visibility=View.VISIBLE

        val TotalAffect: TextView = findViewById(R.id.TotalCase)
        val TotalDie: TextView = findViewById(R.id.TotalDied)
        val TotalRecovered: TextView = findViewById(R.id.TotalRecover)
        val TotalTested: TextView = findViewById(R.id.TotalTest)
        val apiService = ApiClient.client!!.create(ApiInterface::class.java)
        val call: Call<List<Movie>> = apiService.getMovies()
        call.enqueue(object : Callback<List<Movie>> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(response: Response<List<Movie>>?, retrofit: Retrofit?) {

                if (response?.body() != null) {
                    //recyclerAdapter.setMovieListItems(response.body()!!)
                    val responseData = response?.body()

                    progress9.visibility=View.GONE
                    progress98.visibility=View.GONE
                    progress987.visibility=View.GONE
                    progress9876.visibility=View.GONE

                    TotalAffect.text = response.body()[0].newAffected_total
                    val number: String = TotalAffect.text.toString()
                    val amount = number.toDouble()
                    val formatter = DecimalFormat("#,###")
                    val formatted = formatter.format(amount)

                    TotalAffect.text = formatted

                    TotalDie.text = response.body()[0].newDied_total
                    val number1: String = TotalDie.text.toString()
                    val amount1 = number1.toDouble()
                    val formatter1 = DecimalFormat("#,###")
                    val formatted1 = formatter1.format(amount1)

                    TotalDie.text = formatted1

                    TotalRecovered.text = response.body()[0].newRecover_total
                    val number2: String = TotalRecovered.text.toString()
                    val amount2 = number2.toDouble()
                    val formatter2 = DecimalFormat("#,###")
                    val formatted2 = formatter2.format(amount2)

                    TotalRecovered.text = formatted2

                    TotalTested.text = response.body()[0].newTest_total
                    val number3: String = TotalTested.text.toString()
                    val amount3 = number3.toDouble()
                    val formatter3 = DecimalFormat("#,###")
                    val formatted3 = formatter3.format(amount3)

                    TotalTested.text = formatted3
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