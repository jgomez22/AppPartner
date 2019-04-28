package com.mobile.apppartner.Views

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.mobile.apppartner.R
import com.mobile.apppartner.ViewModels.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import com.microsoft.aad.adal.AuthenticationCallback
import com.microsoft.aad.adal.AuthenticationResult
import com.mobile.apppartner.ApiOffice365.AuthenticationManager
import com.mobile.apppartner.ApiOffice365.Constants
import java.net.URI
import java.util.UUID

class LoginActivity : AppCompatActivity() {

    lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        this.btnActivarCuenta.setOnClickListener {
            onConnectButtonClick()
        }

        this.btnLogin.setOnClickListener {
            val email = this.txtCorreoLo.text.toString()
            val password = this.txtPasswordLo.text.toString()
            viewModel.logInViewModel(email,password,this)
                .subscribe(
                    {it->
                        Toast.makeText(this,"cuenta logeada correctamente",Toast.LENGTH_LONG).show()
                    }
                    ,{error->
                        Toast.makeText(this,error.message,Toast.LENGTH_LONG).show()
                    })
        }
    }

    fun onConnectButtonClick(){
        showConnectingInProgressUI()
        //check that client id and redirect have been set correctly
        try {
            UUID.fromString(Constants.CLIENT_ID)
            URI.create(Constants.REDIRECT_URI)
        } catch (e: IllegalArgumentException) {
            Toast.makeText(
                applicationContext, "Ocurrio un error", Toast.LENGTH_LONG
            ).show()
            resetUIForConnect()
            return
        }

        AuthenticationManager.getInstance().setContextActivity(this)
        AuthenticationManager.getInstance().connect(
            object : AuthenticationCallback<AuthenticationResult> {
                /**
                 * If the connection is successful, the activity extracts the username and
                 * displayableId values from the authentication result object and sends them
                 * to the SendMail activity.
                 * @param result The authentication result object that contains information about
                 * the user and the tokens.
                 */
                override fun onSuccess(result: AuthenticationResult) {
                    viewModel.goToRegister(result.userInfo.displayableId.toString(),
                        result.userInfo.givenName.toString()+" "+result.userInfo.familyName.toString(),
                        this@LoginActivity)

                    resetUIForConnect()
                }

                override fun onError(e: Exception) {

                    showConnectErrorUI()
                }
            })
    }

    private fun resetUIForConnect() {
        pbCargar.setVisibility(View.GONE)
    }

    private fun showConnectingInProgressUI() {
        pbCargar.setVisibility(View.VISIBLE)
    }

    private fun showConnectErrorUI() {
        pbCargar.setVisibility(View.GONE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //Log.i(TAG, "onActivityResult - AuthenticationActivity has come back with results")
        super.onActivityResult(requestCode, resultCode, data)
        AuthenticationManager
            .getInstance()
            .getAuthenticationContext()
            .onActivityResult(requestCode, resultCode, data)
    }
}
