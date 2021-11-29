package com.example.covidbangladesh.ui.notifications

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.util.Calendar
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.covidbangladesh.NavBar
import com.example.covidbangladesh.R
import com.example.covidbangladesh.api.ApiClient
import com.example.covidbangladesh.api.ApiInterface
import com.example.covidbangladesh.databinding.FragmentNotificationsBinding
import com.example.covidbangladesh.notify
import com.example.covidbangladesh.models.Vaccine
import com.example.covidbangladesh.signin
import com.example.covidbangladesh.storage.SharedPrefManager
import kotlinx.android.synthetic.main.fragment_notifications.*
import retrofit.Call
import retrofit.Callback
import retrofit.Response
import retrofit.Retrofit
import java.io.IOException
import java.text.DecimalFormat

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private var _binding: FragmentNotificationsBinding? = null

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
        notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)

        _binding!!.progress1.visibility = View.VISIBLE
        _binding!!.progress12.visibility = View.VISIBLE
        _binding!!.progress123.visibility = View.VISIBLE
        _binding!!.progress1234.visibility = View.VISIBLE
        _binding!!.progress12345.visibility = View.VISIBLE
        _binding!!.progress123456.visibility = View.VISIBLE


        val sButton: Button = binding.root.findViewById<View>(R.id.tst) as Button
        sButton.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_VIEW)
            callIntent.data = Uri.parse("https://surokkha.gov.bd")
            startActivity(callIntent)
        }

        val pButton: LinearLayout = binding.root.findViewById<View>(R.id.surokkha) as LinearLayout
        pButton.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_VIEW)
            callIntent.data = Uri.parse("https://surokkha.gov.bd")
            startActivity(callIntent)
        }

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

        val Vaccine_data: TextView = binding.root.findViewById(R.id.Vaccine_data)
        val Reg_data: TextView = binding.root.findViewById(R.id.Reg_data)

        val First_doses_data: TextView = binding.root.findViewById(R.id.First_doses_data)
        val Secound_doses_data: TextView = binding.root.findViewById(R.id.Secound_doses_data)

        val First_against_data: TextView = binding.root.findViewById(R.id.First_against_data)
        val Secound_against_data: TextView = binding.root.findViewById(R.id.Secound_against_data)

        val LastUpdate: TextView = binding.root.findViewById(R.id.LastUpdated)


        val apiService = ApiClient.client!!.create(ApiInterface::class.java)
        val call: Call<List<Vaccine>> = apiService.getVaccine()
        call.enqueue(object : Callback<List<Vaccine>> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(response: Response<List<Vaccine>>?, retrofit: Retrofit?) {
                if (response?.body() != null) {

                    if(response.isSuccess) {
                        try {
                            progress1.visibility = GONE
                            progress12.visibility = GONE
                            progress123.visibility = GONE
                            progress1234.visibility = GONE
                            progress12345.visibility = GONE
                            progress123456.visibility =GONE
                        }
                        catch (e: IOException) {
                            Log.d("onFailure", e.toString());
                        }

                    }

                        Vaccine_data.text = response.body()[0].Vaccine_data
                        val number: String = Vaccine_data.text.toString()
                        val amount = number.toDouble()
                        val formatter = DecimalFormat("#,###")
                        val formatted = formatter.format(amount)

                        Vaccine_data.text = formatted

                        Reg_data.text = response.body()[0].Reg_data
                        val number1: String = Reg_data.text.toString()
                        val amount1 = number1.toDouble()
                        val formatter1 = DecimalFormat("#,###")
                        val formatted1 = formatter1.format(amount1)

                        Reg_data.text = formatted1

                        First_doses_data.text = response.body()[0].First_doses_data
                        val number2: String = First_doses_data.text.toString()
                        val amount2 = number2.toDouble()
                        val formatter2 = DecimalFormat("#,###")
                        val formatted2 = formatter2.format(amount2)

                        First_doses_data.text = formatted2

                        Secound_doses_data.text = response.body()[0].Secound_doses_data
                        val number3: String = Secound_doses_data.text.toString()
                        val amount3 = number3.toDouble()
                        val formatter3 = DecimalFormat("#,###")
                        val formatted3 = formatter3.format(amount3)

                        Secound_doses_data.text = formatted3

                        First_against_data.text = response.body()[0].First_against_data
                        val number4: String = First_against_data.text.toString()
                        val amount4 = number4.toDouble()
                        val formatter4 = DecimalFormat("#,###")
                        val formatted4 = formatter4.format(amount4) + "%"

                        First_against_data.text = formatted4

                        Secound_against_data.text = response.body()[0].Secound_against_data
                        val number5: String = Secound_against_data.text.toString()
                        val amount5 = number5.toDouble()
                        val formatter5 = DecimalFormat("#,###")
                        val formatted5 = formatter5.format(amount5) + "%"

                        Secound_against_data.text = formatted5

                        LastUpdate.text = "Last Updated: " + response.body()[0].updated_at
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

        val mordana = binding.root.findViewById<View>(R.id.mordana) as TableLayout
        mordana.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_VIEW)
            callIntent.data = Uri.parse("https://covid19.trackvaccines.org/vaccines/22/")
            startActivity(callIntent)
        }

        val phzer = binding.root.findViewById<View>(R.id.phzer) as TableLayout
        phzer.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_VIEW)
            callIntent.data = Uri.parse("https://covid19.trackvaccines.org/vaccines/6/")
            startActivity(callIntent)
        }
        val astra = binding.root.findViewById<View>(R.id.astra) as TableLayout
        astra.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_VIEW)
            callIntent.data = Uri.parse("https://covid19.trackvaccines.org/vaccines/48/")
            startActivity(callIntent)
        }
        val sino = binding.root.findViewById<View>(R.id.sino) as TableLayout
        sino.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_VIEW)
            callIntent.data = Uri.parse("https://covid19.trackvaccines.org/vaccines/5/")
            startActivity(callIntent)
        }
        val sputnik = binding.root.findViewById<View>(R.id.sputnik) as TableLayout
        sputnik.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_VIEW)
            callIntent.data = Uri.parse("https://covid19.trackvaccines.org/vaccines/12/")
            startActivity(callIntent)
        }
        val sinovac = binding.root.findViewById<View>(R.id.sinovac) as TableLayout
        sinovac.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_VIEW)
            callIntent.data = Uri.parse("https://covid19.trackvaccines.org/vaccines/7/")
            startActivity(callIntent)
        }
        val jonson = binding.root.findViewById<View>(R.id.jonson) as TableLayout
        jonson.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_VIEW)
            callIntent.data = Uri.parse("https://covid19.trackvaccines.org/vaccines/1/")
            startActivity(callIntent)
        }
        val profile_s = binding.root.findViewById<View>(R.id.profile_string) as LinearLayout
        profile_s.setOnClickListener { view ->
            val intent = Intent(view.context, signin::class.java)
            view.context.startActivity(intent)
        }
        val NButton = binding.root.findViewById<View>(R.id.notify4) as ImageView
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