package com.example.covidbangladesh.ui.map2

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.covidbangladesh.databinding.FragmentMap2Binding
import android.annotation.SuppressLint
import android.content.Context
import android.widget.*

import android.net.Uri
import com.example.covidbangladesh.*
import com.example.covidbangladesh.storage.SharedPrefManager
import kotlinx.android.synthetic.main.fragment_map2.*


class map2 : Fragment() {
    private var _binding: FragmentMap2Binding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: Map2ViewModel

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(Map2ViewModel::class.java)
        _binding = FragmentMap2Binding.inflate(inflater, container, false)

        val LButton = binding.root.findViewById<View>(R.id.logo) as ImageView
        LButton.setOnClickListener { view ->
            val intent = Intent(view.context, NavBar::class.java)
            view.context.startActivity(intent)
        }
        val PButton = binding.root.findViewById<View>(R.id.popup) as LinearLayout
        PButton.setOnClickListener {
            val intent = Intent(view?.context, PopUpWindow::class.java)
            intent.putExtra("popuptitle", "Today's Covid Data")
            intent.putExtra("popuptext", "")
            intent.putExtra("popupbtn", "Close")
            intent.putExtra("darkstatusbar", false)
            startActivity(intent)
        }

        val P1Button = binding.root.findViewById<View>(R.id.popup1) as LinearLayout
        P1Button.setOnClickListener {
            val intent = Intent(view?.context, PopUpWindow1::class.java)
            intent.putExtra("popuptitle", "Total Covid Data")
            intent.putExtra("popuptext", "")
            intent.putExtra("popupbtn", "Close")
            intent.putExtra("darkstatusbar", false)
            startActivity(intent)
        }
        val P2Button = binding.root.findViewById<View>(R.id.popup2) as LinearLayout
        P2Button.setOnClickListener {
            val intent = Intent(view?.context, PopUpWindow2::class.java)
            intent.putExtra("popuptitle", "Vaccine Information")
            intent.putExtra("popuptext", "")
            intent.putExtra("popupbtn", "Close")
            intent.putExtra("darkstatusbar", false)
            startActivity(intent)
        }
        val P3Button = binding.root.findViewById<View>(R.id.popup3) as LinearLayout
        P3Button.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_VIEW)
            callIntent.data = Uri.parse("https://surokkha.gov.bd")
            startActivity(callIntent)
        }
        val P4Button = binding.root.findViewById<View>(R.id.popup4) as LinearLayout
        P4Button.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_VIEW)
            callIntent.data = Uri.parse("https://coronatest.brac.net/")
            startActivity(callIntent)
        }
        val P5Button = binding.root.findViewById<View>(R.id.popupMore) as LinearLayout
        P5Button.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_VIEW)
            callIntent.data = Uri.parse("https://www.aoladanna.info/")
            startActivity(callIntent)
        }
        val P6utton = binding.root.findViewById<View>(R.id.popupMore2) as LinearLayout
        P6utton.setOnClickListener { view ->
            val intent = Intent(view.context, About_us::class.java)
            view.context.startActivity(intent)
        }
        val profile_s = binding.root.findViewById<View>(R.id.profile_string) as LinearLayout
        profile_s.setOnClickListener { view ->
            val intent = Intent(view.context, signin::class.java)
            view.context.startActivity(intent)
        }
        val NButton = binding.root.findViewById<View>(R.id.notify3) as ImageView
        NButton.setOnClickListener { view ->
            val intent = Intent(view.context, notify::class.java)
            view.context.startActivity(intent)
        }
        if(SharedPrefManager.getInstance(requireContext()).isLoggedIn) {
            _binding!!.signout.visibility=View.VISIBLE
            val mPref = SharedPrefManager.getInstance(requireContext())
            val profile_name: TextView = binding.root.findViewById(R.id.profile_name)
            profile_name.text = mPref.data.name.toString()
        }
        val outButton = binding.root.findViewById<View>(R.id.signout) as Button
        outButton.setOnClickListener { view ->
            val mPref = SharedPrefManager.getInstance(requireContext())
            mPref.clear(profile_name)
            Toast.makeText(
                requireContext(),
                "Signout Successfull",
                Toast.LENGTH_LONG
            ).show()
            _binding!!.signout.visibility=View.GONE
            val intent = Intent(view.context, NavBar::class.java)
            view.context.startActivity(intent)
        }

        return binding.root
    }


}