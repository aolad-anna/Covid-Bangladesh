package com.example.covidbangladesh.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.util.Calendar
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.covidbangladesh.R
import com.example.covidbangladesh.databinding.FragmentHomeBinding

import android.widget.*
import androidx.annotation.RequiresApi
import com.example.covidbangladesh.NavBar
import com.example.covidbangladesh.notify
import com.example.covidbangladesh.models.Movie
import com.example.covidbangladesh.signin
import retrofit.Callback
import retrofit.Response
import retrofit.Retrofit
import java.text.DecimalFormat
import com.example.covidbangladesh.api.ApiInterface

import com.example.covidbangladesh.api.ApiClient.client
import com.example.covidbangladesh.storage.SharedPrefManager

import retrofit.Call
import android.widget.TextView
import com.google.android.gms.ads.*
import com.google.android.gms.common.util.CollectionUtils.listOf
import kotlinx.android.synthetic.main.fragment_home.*


open class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    lateinit var mAdView : AdView
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        _binding!!.progress1.visibility=View.VISIBLE
        _binding!!.progress12.visibility=View.VISIBLE
        _binding!!.progress123.visibility=View.VISIBLE
        _binding!!.progress1234.visibility=View.VISIBLE

        _binding!!.progress9.visibility=View.VISIBLE
        _binding!!.progress98.visibility=View.VISIBLE
        _binding!!.progress987.visibility=View.VISIBLE
        _binding!!.progress9876.visibility=View.VISIBLE

        val adView = AdView(requireContext())

        adView.adSize = AdSize.BANNER

        adView.adUnitId = "ca-app-pub-3940256099942544/6300978111"

        MobileAds.initialize(requireContext()) {}

        mAdView = binding.root.findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)



        val cButton: Button = binding.root.findViewById<View>(R.id.buttonCall1) as Button
        cButton.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:333")
            startActivity(callIntent)
        }

        val tButton: Button = binding.root.findViewById<View>(R.id.tst) as Button
        tButton.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_VIEW)
            callIntent.data = Uri.parse("https://coronatest.brac.net/")
            startActivity(callIntent)
        }

        val jButton: LinearLayout = binding.root.findViewById<View>(R.id.brac) as LinearLayout
        jButton.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_VIEW)
            callIntent.data = Uri.parse("https://coronatest.brac.net/")
            startActivity(callIntent)
        }

        val hButton: Button = binding.root.findViewById<View>(R.id.buttonMail1) as Button
        hButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            val recipients = arrayOf("info@dghs.gov.bd")
            intent.putExtra(Intent.EXTRA_EMAIL, recipients)
            intent.putExtra(Intent.EXTRA_SUBJECT, "Subject text here...")
            intent.putExtra(Intent.EXTRA_TEXT, "Body of the content here...")
            intent.type = "text/html"
            intent.setPackage("com.google.android.gm")
            startActivity(Intent.createChooser(intent, "Send mail"))
        }

        val tabs = binding.root.findViewById(R.id.tabhost) as TabHost
        tabs.setup()
        var spec = tabs.newTabSpec("tag1")
        spec.setContent(R.id.tab1)
        spec.setIndicator("Today Data")

        tabs.addTab(spec)
        spec = tabs.newTabSpec("tag2")
        spec.setContent(R.id.tab2)
        spec.setIndicator("Total Data")
        tabs.addTab(spec)

        val TotalAffect: TextView = binding.root.findViewById(R.id.TotalCase)
        val TotalDie: TextView = binding.root.findViewById(R.id.TotalDied)
        val TotalRecovered: TextView = binding.root.findViewById(R.id.TotalRecover)
        val TotalTested: TextView = binding.root.findViewById(R.id.TotalTest)

        val TodayAffect: TextView = binding.root.findViewById(R.id.TodayCase)
        val TodayDie: TextView = binding.root.findViewById(R.id.TodayDied)
        val TodayRecovered: TextView = binding.root.findViewById(R.id.TodayRecover)
        val TodayTested: TextView = binding.root.findViewById(R.id.TodayTest)

        val LastUpdate: TextView = binding.root.findViewById(R.id.LastUpdated)

        val apiService = client!!.create(ApiInterface::class.java)
        val call: Call<List<Movie>> = apiService.getMovies()
        call.enqueue(object : Callback<List<Movie>> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(response: Response<List<Movie>>?, retrofit: Retrofit?) {

                if (response?.body() != null) {
                    //recyclerAdapter.setMovieListItems(response.body()!!)

                    _binding!!.progress1.visibility=View.GONE
                    _binding!!.progress12.visibility=View.GONE
                    _binding!!.progress123.visibility=View.GONE
                    _binding!!.progress1234.visibility=View.GONE

                    _binding!!.progress9.visibility=View.GONE
                    _binding!!.progress98.visibility=View.GONE
                    _binding!!.progress987.visibility=View.GONE
                    _binding!!.progress9876.visibility=View.GONE


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

                    LastUpdate.text ="Last Updated: " + response.body()[0].updated_at
                }
            }

            override fun onFailure(t: Throwable?) {
                Log.d("onFailure", t.toString());
            }
        })

        val status: TextView = binding.root.findViewById(R.id.textView11)

        Calendar.getInstance()
        val timeOfDay = Calendar.getInstance()[Calendar.HOUR_OF_DAY]

        if (timeOfDay in 5..12) {
            status.text = "Good Morning " + String(Character.toChars(0x1F600))
        } else if (timeOfDay in 12..16) {
            status.text = "Good Afternoon " + String(Character.toChars(0x1F60E))
        } else if (timeOfDay in 16..20) {
            status.text = "Good Evening " + String(Character.toChars(0x1F389))
        } else {
            status.text = "Good Night " + String(Character.toChars(0x1F60A))
        }

        val LButton = binding.root.findViewById<View>(R.id.logo) as ImageView
        LButton.setOnClickListener { view ->
            val intent = Intent(view.context, NavBar::class.java)
            view.context.startActivity(intent)
        }
        val profile_s = binding.root.findViewById<View>(R.id.profile_string) as LinearLayout
        profile_s.setOnClickListener { view ->
            val intent = Intent(view.context, signin::class.java)
            view.context.startActivity(intent)
        }
        val NButton = binding.root.findViewById<View>(R.id.notify2) as ImageView
        NButton.setOnClickListener { view ->
            val intent = Intent(view.context, notify::class.java)
            view.context.startActivity(intent)
        }

        if(SharedPrefManager.getInstance(requireContext()).isLoggedIn) {
            val mPref = SharedPrefManager.getInstance(requireContext())
            val profile_name: TextView = binding.root.findViewById(R.id.profile_name)
            profile_name.text = mPref.data.name.toString()
        }

        return binding.root

    }
    override fun onPause() {
        adView.pause()
        super.onPause()
    }

    // Called when returning to the activity
    override fun onResume() {
        super.onResume()
        adView.resume()
    }

    // Called before the activity is destroyed
//    override fun onDestroy() {
//        adView.destroy()
//        super.onDestroy()
//    }

}