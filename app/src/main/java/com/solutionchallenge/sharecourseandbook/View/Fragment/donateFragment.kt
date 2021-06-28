package com.solutionchallenge.sharecourseandbook.View.Fragment

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.android.billingclient.api.*
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.labters.lottiealertdialoglibrary.ClickListener
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.labters.lottiealertdialoglibrary.LottieAlertDialog
import com.solutionchallenge.sharecourseandbook.Model.FirebaseModels.StandartUser
import com.solutionchallenge.sharecourseandbook.Model.FirebaseModels.StudentUser
import com.solutionchallenge.sharecourseandbook.Model.FirebaseModels.SuccesfulDonate
import com.solutionchallenge.sharecourseandbook.R
import com.solutionchallenge.sharecourseandbook.RemoteApi.RetrofitObject
import com.solutionchallenge.sharecourseandbook.Repository.FireStoreRepository
import com.solutionchallenge.sharecourseandbook.View.Activity.MainActivity
import com.solutionchallenge.sharecourseandbook.ViewModel.FirestoreViewModel
import kotlinx.android.synthetic.main.donate_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.IOException

class donateFragment :Fragment(R.layout.donate_fragment),PurchasesUpdatedListener {

    val args : donateFragmentArgs by navArgs()
    lateinit var contextz:Context
    lateinit var billingClient: BillingClient
    lateinit var circularprogress:CircularProgressDrawable
    lateinit var fireStoreRepository: FireStoreRepository
    lateinit var auth:FirebaseAuth
    lateinit var viewModel:FirestoreViewModel
    var currentUserType:String=""
    var curentUserBalance=0.0
    var currentUserMail=""


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Establish connection to billing client
        //check purchase status from google play store cache on every app start
        contextz=(activity as MainActivity).applicationContext
        viewModel=(activity as MainActivity).viewModel
        auth=(activity as MainActivity).auth
        fireStoreRepository= FireStoreRepository()
        setProgress()
        setBillingClient()
        determineUserType()
        currentUserMail= auth.currentUser?.email.toString()

        if(currentUserType=="Student"){
            CoroutineScope(Dispatchers.IO).launch {
                var studentUser=Firebase.firestore.collection("student").document(auth.currentUser?.email.toString()).get().await().toObject(StudentUser::class.java)!!
                curentUserBalance=studentUser.shareCredit
            }
        }else{
            CoroutineScope(Dispatchers.IO).launch {
                var standartUser=Firebase.firestore.collection("normalUser").document(auth.currentUser?.email.toString()).get().await().toObject(StandartUser::class.java)!!
                curentUserBalance=standartUser.shareCredit

            }
        }

        var courseURL=args.request.courseLink
        CoroutineScope(Dispatchers.IO).launch {
            var course= RetrofitObject.apiService.getCourse(takeIDFromUrl(courseURL)).body()

            withContext(Dispatchers.Main){
                tvCourseNameD.text=course?.title
                tvCreatorOfCourse.text="This course created by "+course?.visible_instructors?.get(0)?.display_name?.toUpperCase()
                tvMajorD.text=args.request.studentUser.major+" Student"+" from "+args.request.studentUser.country
                Glide.with(view).load(course?.image_480x270).placeholder(circularprogress).into(imageView4D)
                tvCoursePriceD.text=args.coursePricex.toString()+""+args.currency

                setUserBalanceByLocation(args.currency)

            }

        }
        

         btnBilling.setOnClickListener {

              //initiate purchase on selected consume item click
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
         btnDonateWithWallet.setOnClickListener {
             var alertDialog : LottieAlertDialog
             alertDialog= LottieAlertDialog.Builder(requireContext(), DialogTypes.TYPE_QUESTION)
                 .setTitle("Are you sure want to donate with your wallet")
                 .setDescription("Your wallet balance is $curentUserBalance")
                 .setPositiveText("Yes")
                 .setNegativeText("No")
                 .setPositiveButtonColor(R.color.thmeBlue)
                 .setPositiveTextColor(Color.parseColor("#ffffff"))
                 .setNegativeButtonColor(Color.parseColor("#ffbb00"))
                 .setNegativeTextColor(Color.parseColor("#0a0906"))
                 .setPositiveListener(object : ClickListener {
                     override fun onClick(dialog: LottieAlertDialog) {
                          if(currentUserMail.isStudent()){
                              if(curentUserBalance>args.coursePricex){
                                  var succesfulDonate=SuccesfulDonate(currentUserMail,args.request)
                                  viewModel.saveSuccesfulDonateForStudent(succesfulDonate,currentUserMail,args.coursePricex.toDouble())
                                  viewModel.deleteRequest(args.request)
                                  createSuccesfulAlertDialog()
                                  dialog.dismiss()
                              }
                              else{
                                  dialog.dismiss()
                                  createWarringAlertDialog()
                              }
                          }else{
                              if(curentUserBalance>args.coursePricex){
                                  var succesfulDonate=SuccesfulDonate(currentUserMail,args.request)
                                  viewModel.saveSuccesfulDonateForNormalUser(succesfulDonate,currentUserMail,args.coursePricex.toDouble())
                                  viewModel.deleteRequest(args.request)
                                  createSuccesfulAlertDialog()
                                  dialog.dismiss()
                              }
                              else{
                                  dialog.dismiss()
                                  createWarringAlertDialog()
                              }

                          }


                     }

                 })
                 .setNegativeListener(object :ClickListener{
                     override fun onClick(dialog: LottieAlertDialog) {
                         dialog.dismiss()
                     }

                 })
                 .build()
             alertDialog.setCancelable(true)
             alertDialog.show()
         }

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
    }


    fun createSuccesfulAlertDialog(){
        var alertDialog : LottieAlertDialog

        alertDialog=LottieAlertDialog.Builder(requireContext(),DialogTypes.TYPE_SUCCESS)
            .setTitle("Congratulations")
            .setDescription("Your donation done succesfully. We will send deatils with email")
            .setPositiveText("Thanks")
            .setPositiveListener(object :ClickListener{
                override fun onClick(dialog: LottieAlertDialog) {
                    dialog.dismiss()
                    Navigation.findNavController(requireView()).navigate(R.id.action_donateFragment_to_profileFragment)
                }

            })
            .build()
        alertDialog.show()
    }
    fun createWarringAlertDialog(){
        var alertDialog : LottieAlertDialog

        alertDialog=LottieAlertDialog.Builder(requireContext(),DialogTypes.TYPE_ERROR)
            .setTitle("Insufficient Balance")
            .setDescription("Your balance not enough to make this donation. You can load money your wallet")
            .setPositiveText("Load Money")
            .setPositiveButtonColor(Color.parseColor("#ffbb00"))
            .setPositiveListener(object :ClickListener{
                override fun onClick(dialog: LottieAlertDialog) {
                    Navigation.findNavController(requireView()).navigate(R.id.action_donateFragment_to_uploadMoneyFragment)
                    dialog.dismiss()
                }

            }).build()
        alertDialog.show()
    }

    fun String.isStudent():Boolean{
        if(this.contains(".edu"))
            return true
        return false
    }
    fun setUserBalanceByLocation(currency:String){
        if(currency=="$")
            curentUserBalance=curentUserBalance/8
        else if(currency=="€")
            curentUserBalance=curentUserBalance/10
    }
    fun determineUserType(){
        if(auth.currentUser?.email.toString().isStudent())
            currentUserType="Student"
        else
            currentUserType="Normal"
    }
    private fun setBillingClient(){
       billingClient = BillingClient.newBuilder(this.contextz)
           .enablePendingPurchases().setListener(this).build()
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
    private fun handlePurchases(purchases: List<Purchase>) {
        for (purchase in purchases) {
            val index = purchaseItemIDs.indexOf(purchase.sku)

            if (index > -1) {

                if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
                    if (!verifyValidSignature(purchase.originalJson, purchase.signature)) {

                        Toast.makeText(contextz.applicationContext, "Error : Invalid Purchase", Toast.LENGTH_SHORT).show()
                        continue
                    }

                    if (!purchase.isAcknowledged) {
                        val consumeParams = ConsumeParams.newBuilder()
                            .setPurchaseToken(purchase.purchaseToken)
                            .build()
                        billingClient!!.consumeAsync(consumeParams) { billingResult, purchaseToken ->
                            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                                var succesfulDonate=SuccesfulDonate(auth.currentUser?.email.toString(),args.request)
                                Toast.makeText(contextz.applicationContext, "Item " + purchaseItemIDs[index] + "Consumed", Toast.LENGTH_SHORT).show()

                                    viewModel.saveSuccesfulDonateForStudent(succesfulDonate,currentUserMail,args.coursePricex.toDouble())
                                    viewModel.deleteRequest(args.request)
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
    private fun verifyValidSignature(signedData: String, signature: String): Boolean {
        return try {
            val base64Key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqIPdG6tC66bAxgO8n/hLKi7vsKQEf4pP3MqrlapaxZmGvdsekGSqkXS6MMC42HWUDdIkXV64YkbCj4jGIoI8f4qMsv8e5A3oJ+HezT8Ac3VISefMLJ4cXorkGqsafuboySy8yGtHZrmE8ZSglgUvrt6IvlSlV125oVNHOJABhJ8JoKAMy1je+Qh9z0ElpLdT+n6h3/qkIprfQ4HNabXk2x8PEHEaciPkV+e/hbLDMOGo8Ke8lcPOqO0y1PJ99YOY/hp2cLh/uY+X+tJ2dn8O7cTDAt84WvdSXe/DeLA/w5J1OflfVzOX8V7fk7pfONN9oWIUZJHbYdm4/08bsGqq7wIDAQAB"
            com.solutionchallenge.sharecourseandbook.Extra.Security.verifyPurchase(base64Key, signedData, signature)
        } catch (e: IOException) {
            false
        }
    }
    fun takeIDFromUrl(url:String):Int{
        var numberOfSlash=0
        var id:StringBuilder=StringBuilder("")
        for(char in url){
            if(char=='/')
                numberOfSlash++
            else if(numberOfSlash==7){
                id.append(char)
            }
        }
        try {
            var idx=id.toString().toInt()
            return idx
        }catch (e :Exception){
            Log.d("Error","${e.message}")
            return 0
        }
    }
    fun setProgress(){
        circularprogress = CircularProgressDrawable(requireContext())
        circularprogress.strokeWidth = 5f
        circularprogress.centerRadius = 30f
        circularprogress.start()
    }
    companion object {
        const val PREF_FILE = "MyPref"

        private val purchaseItemIDs: ArrayList<String> = object : ArrayList<String>() {
            init {
                add("udemy_token")

            }
        }
        private val purchaseItemDisplay: ArrayList<String?> = ArrayList<String?>()

       }
    }


