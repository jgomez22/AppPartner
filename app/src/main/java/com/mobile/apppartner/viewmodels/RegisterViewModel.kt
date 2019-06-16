package com.mobile.apppartner.viewmodels

import android.app.Activity
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.mobile.apppartner.models.ApiClient
import com.mobile.apppartner.models.UserDatabase
import com.mobile.apppartner.models.UserPartner
import com.mobile.apppartner.models.api.firebase.ApiFirebase
import com.mobile.apppartner.models.api.firebase.ApiFirebaseStorage
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*

class RegisterViewModel:ViewModel() {

    lateinit var apiFirebaseStorage:ApiFirebaseStorage
    lateinit var apiFirebase:ApiFirebase
    lateinit var apiClient:ApiClient
    var valueOfInterest:MutableList<Int>?=null
    var uri:Uri? = null

    var bitmapDrawable:BitmapDrawable?=null
        private set

    fun validateUser(pass:String,activity:Activity):Observable<String>{
        this.apiClient = ApiClient(activity)
        return apiClient.validateUser(uri,pass,valueOfInterest)
    }

    fun createAccount(email:String,password:String,activity: Activity):Observable<UserPartner>{
        this.apiFirebase = ApiFirebase()
        return apiFirebase.createUser(email,password,activity)
    }

    fun openImage(activity:Activity){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/"
        activity.startActivityForResult(intent,0)
    }

    fun finishRegister(activity:Activity,correo:String,nombre:String,campus:String,career:String){
        val filename = UUID.randomUUID()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")
        ref.putFile(uri!!).addOnSuccessListener {
            ref.downloadUrl.addOnSuccessListener {
                saveUserToDatabase(activity,it.toString(),correo,nombre,campus,career)
            }
        }.addOnFailureListener {
            invisibleRegistro(activity)
            Toast.makeText(activity.applicationContext,"Ocurrio un error. (Error 2)", Toast.LENGTH_LONG).show()
        }
    }

    fun saveUserToDatabase(activity:Activity,uri:String,correo:String,nombre:String,campus:String,career:String){
        val uid = FirebaseAuth.getInstance().uid?:""
        val ref1 =FirebaseDatabase.getInstance().getReference("/users/$uid")
        //cambiarlo despues :)
        val interes = mutableListOf<Int>(2,4,7)
        val user = UserDatabase(uid,correo,nombre,uri,interes)
        user.campus=campus
        user.career=career
        user.interest = valueOfInterest!!
        ref1.setValue(user).addOnSuccessListener {
            FirebaseAuth.getInstance().signOut()
            invisibleRegistro(activity)
            Toast.makeText(activity.applicationContext,"Registro correctamente: $nombre", Toast.LENGTH_LONG).show()
            activity.finish()
        }.addOnFailureListener {
            invisibleRegistro(activity)
            Toast.makeText(activity.applicationContext,"Ocurrio un error. (Error 3)", Toast.LENGTH_LONG).show()
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?,activity: Activity){
        if(requestCode==0 && resultCode== Activity.RESULT_OK && data !=null){
            uri = data.data
            activity.imgPerfilRe.setImageURI(uri)
        }
    }

    fun visibleRegistro(activity: Activity) {
        activity.pbCargarRegistro.setVisibility(View.VISIBLE)
    }

    fun invisibleRegistro(activity: Activity) {
        activity.pbCargarRegistro.setVisibility(View.GONE)
    }


}