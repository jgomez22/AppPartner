package com.mobile.apppartner.ViewModels

import android.app.Activity
import android.arch.lifecycle.ViewModel
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.provider.MediaStore
import com.mobile.apppartner.Models.ApiClient
import com.mobile.apppartner.Models.UserPartner
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_register.*

class RegisterViewModel:ViewModel() {

    lateinit var apiClient:ApiClient

    var bitmapDrawable:BitmapDrawable?=null
        private set

    fun createAccount(email:String,password:String,activity: Activity):Observable<UserPartner>{
        this.apiClient = ApiClient(activity)
        return apiClient.createUser(email,password)
    }

    fun openImage(activity:Activity){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/"
        activity.startActivityForResult(intent,0)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?,activity: Activity){
        if(requestCode==0 && resultCode== Activity.RESULT_OK && data !=null){
            val uri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(activity.contentResolver,uri)
            bitmapDrawable = BitmapDrawable(bitmap)
            activity.imgPerfilRe.setBackgroundDrawable(bitmapDrawable)
        }
    }
}