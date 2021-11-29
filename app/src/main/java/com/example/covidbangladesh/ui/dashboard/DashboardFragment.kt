package com.example.covidbangladesh.ui.dashboard

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.util.Calendar
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.covidbangladesh.R
import com.example.covidbangladesh.databinding.FragmentDashboardBinding
import android.widget.*
import com.example.covidbangladesh.NavBar
import com.example.covidbangladesh.notify
import com.example.covidbangladesh.signin
import com.example.covidbangladesh.storage.SharedPrefManager


class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetJavaScriptEnabled", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)



            val myWebView: WebView = binding.root.findViewById(R.id.anna)
            myWebView.settings.javaScriptEnabled = true

            myWebView.loadData(
                "<iframe id=\"map\" src=\"https://dataforgood.facebook.com/covid-survey/embed-content?region=BGD\" width=\"100%\" height=\"100%\" frameborder=\"0\"></iframe>",
                "text/html",
                null
            )


        val cButton: Button = binding.root.findViewById<View>(R.id.buttonCall1) as Button
        cButton.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:333")
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
        val NButton = binding.root.findViewById<View>(R.id.notify1) as ImageView
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

