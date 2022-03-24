package com.yudhinurb.zwallet.ui.layout.main.profile

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
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yudhinurb.zwallet.R
import com.yudhinurb.zwallet.databinding.FragmentProfileBinding
import com.yudhinurb.zwallet.ui.layout.SplashScreenActivity
import com.yudhinurb.zwallet.ui.layout.main.home.HomeViewModel
import com.yudhinurb.zwallet.ui.layout.viewModelsFactory
import com.yudhinurb.zwallet.utils.BASE_URL
import com.yudhinurb.zwallet.utils.KEY_LOGGED_IN
import com.yudhinurb.zwallet.utils.PREFS_NAME
import dagger.hilt.android.AndroidEntryPoint
import javax.net.ssl.HttpsURLConnection

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var prefs: SharedPreferences
    private val viewModel: HomeViewModel by activityViewModels()

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
                    Glide.with(imageProfile).load(BASE_URL +it.resource.data?.get(0)?.image).apply(
                        RequestOptions.circleCropTransform().placeholder(R.drawable.ic_baseline_broken_image_24)
                    ).into(imageProfile)
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
            findNavController().popBackStack()
        }

        binding.menuPersonalInformation.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_personalInformationFragment)
        }

        binding.menuChangePin.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_changePinFragment)
        }

        binding.menuChangePassword.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_changePasswordFragment)
        }
    }

}