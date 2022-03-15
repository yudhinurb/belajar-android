package com.yudhinurb.zwallet.ui.main.profile

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.yudhinurb.zwallet.R
import com.yudhinurb.zwallet.databinding.FragmentProfileBinding
import com.yudhinurb.zwallet.ui.SplashScreenActivity
import com.yudhinurb.zwallet.ui.main.home.HomeViewModel
import com.yudhinurb.zwallet.ui.viewModelsFactory
import com.yudhinurb.zwallet.utils.Helper.formatPrice
import com.yudhinurb.zwallet.utils.KEY_LOGGED_IN
import com.yudhinurb.zwallet.utils.PREFS_NAME
import javax.net.ssl.HttpsURLConnection

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var prefs: SharedPreferences
    private val viewModel: HomeViewModel by viewModelsFactory { HomeViewModel(requireActivity().application) }

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

        viewModel.getBalance().observe(viewLifecycleOwner) {
            if (it.resource?.status == HttpsURLConnection.HTTP_OK){
                binding.apply {
                    textPhoneNumber.text = it.resource.data?.get(0)?.phone
                    textName.text = it.resource.data?.get(0)?.name
                }
            } else {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Logout Confirmation")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes") {_,_ ->
                    with(prefs.edit()){
                        putBoolean(KEY_LOGGED_IN, false)
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

        binding.btnBack.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_homeFragment)
        }

        binding.menuPersonalInformation.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_personalInformationFragment)
        }

        binding.menuChangePin.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_changePinFragment)
        }
    }

}