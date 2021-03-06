package com.solutionchallenge.sharecourseandbook.View.Fragment

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.sip.SipSession
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.android.billingclient.api.*
import com.solutionchallenge.sharecourseandbook.R
import com.solutionchallenge.sharecourseandbook.Security.verifyPurchase
import com.solutionchallenge.sharecourseandbook.View.Activity.MainActivity
import kotlinx.android.synthetic.main.donate_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.security.Security

class donateFragment :Fragment(R.layout.donate_fragment),PurchasesUpdatedListener {

   val args : donateFragmentArgs by navArgs()
    lateinit var contextz:Context
    lateinit var billingClient: BillingClient
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Establish connection to billing client
        //check purchase status from google play store cache on every app start
        contextz=(activity as MainActivity).applicationContext
        billingClient = BillingClient.newBuilder(this.contextz)
            .enablePendingPurchases().setListener(this).build()
        billingClient!!.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    val queryPurchase = billingClient!!.queryPurchases(BillingClient.SkuType.INAPP)
                    val queryPurchases = queryPurchase.purchasesList
                    if (queryPurchases != null && queryPurchases.size > 0) {
                        handlePurchases(queryPurchases)
                    }
                }
            }

            override fun onBillingServiceDisconnected() {}
        })
        btnBilling.setOnClickListener { //initiate purchase on selected consume item click
            //check if service is already connected
            if (billingClient!!.isReady) {
                initiatePurchase("udemy_token")
            } else {
                billingClient = BillingClient.newBuilder(contextz).enablePendingPurchases().setListener(this).build()
                billingClient!!.startConnection(object : BillingClientStateListener {
                    override fun onBillingSetupFinished(billingResult: BillingResult) {
                        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                            initiatePurchase("udemy_token")
                        } else {
                            Toast.makeText(contextz.applicationContext, "Error " + billingResult.debugMessage, Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onBillingServiceDisconnected() {}
                })
            }
        }
    }







    private fun initiatePurchase(PRODUCT_ID: String) {
        val skuList: MutableList<String> = ArrayList()
        skuList.add(PRODUCT_ID)
        val params = SkuDetailsParams.newBuilder()
        params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP)
        billingClient!!.querySkuDetailsAsync(params.build()
        ) { billingResult, skuDetailsList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                if (skuDetailsList != null && skuDetailsList.size > 0) {
                    val flowParams = BillingFlowParams.newBuilder()
                        .setSkuDetails(skuDetailsList[0])
                        .build()
                    billingClient!!.launchBillingFlow(activity as Activity, flowParams)
                } else {
                    Toast.makeText(contextz.applicationContext, "Purchase Item $PRODUCT_ID not Found", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(contextz.applicationContext,
                    " Error " + billingResult.debugMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onPurchasesUpdated(billingResult: BillingResult, purchases: List<Purchase>?) {
        //if item newly purchased
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            handlePurchases(purchases)
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED) {
            val queryAlreadyPurchasesResult = billingClient!!.queryPurchases(BillingClient.SkuType.INAPP)
            val alreadyPurchases = queryAlreadyPurchasesResult.purchasesList
            alreadyPurchases?.let { handlePurchases(it) }
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            Toast.makeText(contextz.applicationContext, "Purchase Canceled", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(contextz.applicationContext, "Error " + billingResult.debugMessage, Toast.LENGTH_SHORT).show()
        }
    }

    fun handlePurchases(purchases: List<Purchase>) {
        for (purchase in purchases) {
            val index = purchaseItemIDs.indexOf(purchase.sku)
            //purchase found
            if (index > -1) {

                //if item is purchased
                if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
                    if (!verifyValidSignature(purchase.originalJson, purchase.signature)) {
                        // Invalid purchase
                        // show error to user
                        Toast.makeText(contextz.applicationContext, "Error : Invalid Purchase", Toast.LENGTH_SHORT).show()
                        continue  //skip current iteration only because other items in purchase list must be checked if present
                    }
                    // else purchase is valid
                    //if item is purchased and not consumed
                    if (!purchase.isAcknowledged) {
                        val consumeParams = ConsumeParams.newBuilder()
                            .setPurchaseToken(purchase.purchaseToken)
                            .build()
                        billingClient!!.consumeAsync(consumeParams) { billingResult, purchaseToken ->
                            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {

                                Toast.makeText(contextz.applicationContext, "Item " + purchaseItemIDs[index] + "Consumed", Toast.LENGTH_SHORT).show()

                            }
                        }
                    }
                } else if (purchase.purchaseState == Purchase.PurchaseState.PENDING) {
                    Toast.makeText(contextz.applicationContext, purchaseItemIDs[index] + " Purchase is Pending. Please complete Transaction", Toast.LENGTH_SHORT).show()
                } else if (purchase.purchaseState == Purchase.PurchaseState.UNSPECIFIED_STATE) {
                    Toast.makeText(contextz.applicationContext, purchaseItemIDs[index] + " Purchase Status Unknown", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /**
     * Verifies that the purchase was signed correctly for this developer's public key.
     *
     * Note: It's strongly recommended to perform such check on your backend since hackers can
     * replace this method with "constant true" if they decompile/rebuild your app.
     *
     */
    private fun verifyValidSignature(signedData: String, signature: String): Boolean {
        return try {
            //for old playconsole
            // To get key go to Developer Console > Select your app > Development Tools > Services & APIs.
            //for new play console
            //To get key go to Developer Console > Select your app > Monetize > Monetization setup
            val base64Key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqIPdG6tC66bAxgO8n/hLKi7vsKQEf4pP3MqrlapaxZmGvdsekGSqkXS6MMC42HWUDdIkXV64YkbCj4jGIoI8f4qMsv8e5A3oJ+HezT8Ac3VISefMLJ4cXorkGqsafuboySy8yGtHZrmE8ZSglgUvrt6IvlSlV125oVNHOJABhJ8JoKAMy1je+Qh9z0ElpLdT+n6h3/qkIprfQ4HNabXk2x8PEHEaciPkV+e/hbLDMOGo8Ke8lcPOqO0y1PJ99YOY/hp2cLh/uY+X+tJ2dn8O7cTDAt84WvdSXe/DeLA/w5J1OflfVzOX8V7fk7pfONN9oWIUZJHbYdm4/08bsGqq7wIDAQAB"
            com.solutionchallenge.sharecourseandbook.Security.verifyPurchase(base64Key, signedData, signature)
        } catch (e: IOException) {
            false
        }
    }



    companion object {
        const val PREF_FILE = "MyPref"

        //note add unique product ids
        //use same id for preference key
        private val purchaseItemIDs: ArrayList<String> = object : ArrayList<String>() {
            init {
                add("udemy_token")

            }
        }
        private val purchaseItemDisplay: ArrayList<String?> = ArrayList<String?>()

       }
    }


