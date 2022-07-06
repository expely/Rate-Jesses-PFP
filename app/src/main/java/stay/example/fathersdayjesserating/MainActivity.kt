package stay.example.fathersdayjesserating

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.AWSDataStorePlugin
import com.example.fathersdayjesserating.R
import com.facebook.CallbackManager
import com.facebook.FacebookSdk
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.stylelogin.*
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_main)

        configureAmplify()

        button3.setOnClickListener{
            val intent = Intent(this@MainActivity, Rankings::class.java)
            startActivity(intent)
        }

        buttonLogin.setOnClickListener(object:View.OnClickListener{
            override fun onClick(view: View?)  {
                val dialog = Dialog(this@MainActivity)
                dialog.setContentView(R.layout.stylelogin)
                val imageView = dialog.findViewById<ImageView>(R.id.btnclose)
                imageView.setOnClickListener(object:View.OnClickListener {
                     override fun onClick(v: View?) {
                        dialog.dismiss()
                    }
                })

                val btnlogin = dialog.findViewById<Button>(R.id.btnlogin)
                btnlogin.setOnClickListener(object:View.OnClickListener {
                    override fun onClick(v: View?) {
                        var username: String? = ""
                        var password: String? = ""
                        try {
                            username = dialog.txtusername.text.toString()
                            password = dialog.txtpassword.text.toString()
                            println(username + password)
                        } catch (e: NullPointerException) {
                            Toast.makeText(this@MainActivity, "Username or Password cannot be blank", Toast.LENGTH_LONG).show()
                        }
                        if (username == "Jesse Stay" && password == "sugar555") {
                           val intent = Intent(this@MainActivity, AddPhotos::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@MainActivity, "Username or Password was invalid", Toast.LENGTH_LONG).show()
                        }
                    }
                })

                dialog.show()
            }
        })

    }

    private fun configureAmplify() {
        try {
            Amplify.addPlugin(AWSApiPlugin())
            Amplify.addPlugin(AWSDataStorePlugin())
            Amplify.configure(applicationContext)
            Log.i("Amplify", "Initialized Amplify")
        } catch (e: AmplifyException) {
            Log.e("Amplify", "Could not initialize Amplify", e)
        }
    }

}