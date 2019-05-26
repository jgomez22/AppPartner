package com.mobile.apppartner.ViewModels

import android.app.Activity
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.mobile.apppartner.Models.ApiClient
import com.mobile.apppartner.Models.UserDatabase
import com.mobile.apppartner.Models.UserPartner
import com.mobile.apppartner.Views.LoginActivity
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*

class RegisterViewModel:ViewModel() {

    lateinit var apiClient:ApiClient
    var valueOfInterest:MutableList<Int>?=null
    var uri:Uri? = null

    var bitmapDrawable:BitmapDrawable?=null
        private set

    fun validateUser(pass:String,context:Context):Boolean{
        if(uri==null){
            Toast.makeText(context,"Sube una imagen de perfil.", Toast.LENGTH_LONG).show()
            return false
        }
        if(pass=="" || pass==null) {
            Toast.makeText(context,"Contraseña vacio.", Toast.LENGTH_LONG).show()
            return false
        }
        if(pass.length<7){
            Toast.makeText(context,"Contraseña debe tener mayor a 7 caracteres.", Toast.LENGTH_LONG).show()
            return false
        }
        if(valueOfInterest?.size==0 || valueOfInterest==null){
            Toast.makeText(context,"Selecione por lo menos un interés.", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    fun createAccount(email:String,password:String,activity: Activity):Observable<UserPartner>{
        this.apiClient = ApiClient(activity)
        return apiClient.createUser(email,password)
    }

    fun openImage(activity:Activity){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/"
        activity.startActivityForResult(intent,0)
    }

    fun finishRegister(correo:String,nombre:String,campus:String,career:String){
        val filename = UUID.randomUUID()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")
        ref.putFile(uri!!).addOnSuccessListener {
            ref.downloadUrl.addOnSuccessListener {
                saveUserToDatabase(it.toString(),correo,nombre,campus,career)
            }
        }
    }

    fun saveUserToDatabase(uri:String,correo:String,nombre:String,campus:String,career:String){
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

        }.addOnFailureListener {
            print(it.message.toString())
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?,activity: Activity){
        if(requestCode==0 && resultCode== Activity.RESULT_OK && data !=null){
            uri = data.data
            //val bitmap = MediaStore.Images.Media.getBitmap(activity.contentResolver,uri)
            //bitmapDrawable = BitmapDrawable(bitmap)
            //activity.imgPerfilRe.setBackgroundDrawable(bitmapDrawable)
            activity.imgPerfilRe.setImageURI(uri)
        }
    }
}