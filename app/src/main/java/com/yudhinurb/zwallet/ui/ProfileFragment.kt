package com.yudhinurb.zwallet.ui

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.yudhinurb.zwallet.R
import com.yudhinurb.zwallet.adapter.TransactionAdapter
import com.yudhinurb.zwallet.databinding.FragmentHomeBinding
import com.yudhinurb.zwallet.databinding.FragmentProfileBinding
import com.yudhinurb.zwallet.utils.KEY_LOGGED_IN
import com.yudhinurb.zwallet.utils.PREFS_NAME

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var prefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefs = context?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)!!

        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Logout Confirmation")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes") {_,_ ->
                    with(prefs.edit()){
                        putBoolean(com.yudhinurb.zwallet.utils.KEY_LOGGED_IN, false)
                        apply()
                    }
                    val intent = Intent(activity, SplashScreenActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }
                .setNegativeButton("Cancel") {_,_ ->
                    return@setNegativeButton
                }
                .show()
//            Navigation.findNavController(view).navigate(R.id.homeActionProfile)
        }
    }

}