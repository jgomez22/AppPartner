package com.mobile.apppartner.viewmodels.fragments

import android.arch.lifecycle.ViewModel
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.mobile.apppartner.Components.CircleTransformation
import com.mobile.apppartner.models.api.firebase.ApiFirebase
import com.mobile.apppartner.views.adapter.InteresAdapter
import com.mobile.apppartner.Components.RecyclerItemClickListenr
import com.mobile.apppartner.models.*
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_home.*
import java.time.LocalDateTime

class HomeViewModel : ViewModel() {

    var interesArray: MutableList<Interes> = mutableListOf()
    var userArray: MutableList<UserDatabase> = mutableListOf()
    var userArrayFilterInterest: MutableList<UserDatabase>? = null
    lateinit var currentFragment: Fragment
    var userDatabase: UserDatabase? = null
    var indexOfInterest: Int? = null
    lateinit var ref: DatabaseReference
    var interested = UserMatch()
    var toUser = UserMatch()

    fun sendMatch(id: String) {
        val myUid = FirebaseAuth.getInstance().uid
        getUserMatch(myUid, true)
        getUserMatch(id, false)
    }

    fun getUserMatch(uid: String?, interestedBool: Boolean) {
        FirebaseDatabase.getInstance().getReference("users").child(uid!!)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    var userData = p0.getValue(UserDatabase::class.java)
                    if (interestedBool) {
                        interested.id = userData!!.uid
                        interested.name = userData.fullname
                    } else {
                        val referenceMatch = FirebaseDatabase.getInstance().getReference("match")
                        var keyMatch: String? = referenceMatch.push().key
                        toUser.id = userData!!.uid
                        toUser.name = userData.fullname
                        interested.keyMatch = keyMatch

                        referenceMatch.child(keyMatch!!)
                            .setValue(Match(null, interested, toUser, false, LocalDateTime.now().toString()))
                        FirebaseDatabase.getInstance().getReference("messages").push().setValue(
                            Message(
                                toUser.id,
                                interested.id,
                                LocalDateTime.now().toString(),
                                "Hola " + toUser.name + " soy " + interested.name + " y tenemos intereses en común, responde este mensaje para compartirlos"
                                , false
                            )
                        )
                    }

                }

                override fun onCancelled(p0: DatabaseError) {
                    print(p0.message)
                }

            })
    }

    fun prueba(fragment: Fragment) {
        this.currentFragment = fragment
        currentFragment.rvInteresesHO.setHasFixedSize(true)
        val layout = LinearLayoutManager(currentFragment.context)
        layout.orientation = LinearLayoutManager.HORIZONTAL
        currentFragment.rvInteresesHO.setAdapter(InteresAdapter(interesArray))
        currentFragment.rvInteresesHO.setLayoutManager(layout)

        currentFragment.rvInteresesHO
            .addOnItemTouchListener(
                RecyclerItemClickListenr(currentFragment.context!!,
                    currentFragment.rvInteresesHO,
                    object : RecyclerItemClickListenr.OnItemClickListener {
                        override fun onItemClick(view: View, position: Int) {
                            indexOfInterest = position
                            getRamdonUserWithInterest()
                        }

                        override fun onItemLongClick(view: View?, position: Int) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                    })
            )
        /*
        recyclerView.addOnItemTouchListener(RecyclerItemClickListenr(this, recyclerView, object : RecyclerItemClickListenr.OnItemClickListener {

        override fun onItemClick(view: View, position: Int) {
            //do your work here..
        }
        override fun onItemLongClick(view: View?, position: Int) {
            TODO("do nothing")
        }
    }))
         */
    }

    fun match(fragment: Fragment) {
        this.currentFragment = fragment
        currentFragment.rvInteresesHO.setHasFixedSize(true)
        val layout = LinearLayoutManager(currentFragment.context)
        layout.orientation = LinearLayoutManager.HORIZONTAL
        currentFragment.rvInteresesHO.setAdapter(InteresAdapter(interesArray))
        currentFragment.rvInteresesHO.setLayoutManager(layout)

        currentFragment.rvInteresesHO
            .addOnItemTouchListener(
                RecyclerItemClickListenr(currentFragment.context!!,
                    currentFragment.rvInteresesHO,
                    object : RecyclerItemClickListenr.OnItemClickListener {
                        override fun onItemClick(view: View, position: Int) {
                            indexOfInterest = position
                            getRamdonUserWithInterest()
                        }

                        override fun onItemLongClick(view: View?, position: Int) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                    })
            )
        /*
        recyclerView.addOnItemTouchListener(RecyclerItemClickListenr(this, recyclerView, object : RecyclerItemClickListenr.OnItemClickListener {

        override fun onItemClick(view: View, position: Int) {
            //do your work here..
        }
        override fun onItemLongClick(view: View?, position: Int) {
            TODO("do nothing")
        }
    }))
         */
    }

    fun getInteres(): Observable<MutableList<Interes>> {
        val apiFirebase = ApiFirebase()
        return apiFirebase.getInteres()
    }

    fun getInfoCurrentUser(): Observable<UserDatabase> {
        val apiFirebase = ApiFirebase()
        return apiFirebase.getInfoCurrentUser()
    }

    fun getRamdonUser(): Observable<MutableList<UserDatabase>> {
        val apiFirebase = ApiFirebase()
        return apiFirebase.getRamdonUser(userDatabase!!.campus)
    }

    fun getRamdonUserWithInterest() {
        userArrayFilterInterest = mutableListOf()

        userArray.forEach { user ->
            user.interest.forEach {
                if (it.equals(indexOfInterest)) userArrayFilterInterest!!.add(user)
            }
        }

        if (userArrayFilterInterest!!.size.equals(0)) {
            Toast.makeText(currentFragment.context, "No se encontró resultados.", Toast.LENGTH_LONG).show()
        } else {
            setUserIntoCardProfile()
        }
    }

    fun setUserIntoCardProfile() {
        var us: UserDatabase = UserDatabase()
        if (userArrayFilterInterest == null) {
            us = userArray.random()
        } else {
            us = userArrayFilterInterest!!.random()
        }
        //val us = userArray.random()
        currentFragment.uid.text = us.uid.toString()
        currentFragment.txtName.text = us.fullname
        currentFragment.txtAge.text = us.age
        currentFragment.txtCareer.text = us.career
        currentFragment.txtNumber.text = us.telephone
        currentFragment.txtDescr.text = us.descripcion
        Picasso.get().load(us.url_img).transform(CircleTransformation()).into(currentFragment.ivImageUser)
        //Picasso.get().load(us?.url_img.toString()).transform(CircleTransformation()).into(this.imgPerfilPR)
    }
}