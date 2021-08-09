package com.solutionchallenge.sharecourseandbook.View.Fragment

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.solutionchallenge.sharecourseandbook.R
import com.solutionchallenge.sharecourseandbook.ViewModel.vm
import kotlinx.android.synthetic.main.upload_fragment_fragment.*

class walletFragment :Fragment(R.layout.upload_fragment_fragment) {

    lateinit var auth:FirebaseAuth
    lateinit var viewModel: vm
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





         walletBalanceText.text="100.0₺"


        btnBackToProfile.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_uploadMoneyFragment_to_profileFragment)
        }


         btnBitcoin.setOnClickListener {
            AlertDialog.Builder(requireContext()).setTitle("PAY WITH CRYPTO").setMessage("You will be redirected Coinbase page. Please don't forget using email which you use for this application").setIcon(R.drawable.paywithbtc).
                    setPositiveButton("Continue"){_,_ ->
                        val url = "https://commerce.coinbase.com/checkout/8c6c68ad-ce5f-4ab9-93a2-b97860a8c1f6"
                        val i = Intent(Intent.ACTION_VIEW)
                        i.data = Uri.parse(url)
                        startActivity(i)
                    }.setNegativeButton("No"){_,_ ->

                    }.create().show()
        }

        btnBankTransfer.setOnClickListener {
          textView5.text="TR00520012435434645634532"+"\n"+"Ahmet Burak İlhan"+"\n"+"Akbank A.Ş"
            AlertDialog.Builder(requireContext()).setTitle("TRANSFER WITH BANK").setMessage("Please add your e-mail that used in this application to the description section.").
                    setPositiveButton("OK"){_,_ ->

                    }.setCancelable(false).create().show()
        }

    }


}