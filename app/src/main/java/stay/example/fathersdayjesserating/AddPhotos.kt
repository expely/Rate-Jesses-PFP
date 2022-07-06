package stay.example.fathersdayjesserating

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.amplifyframework.core.Amplify
import com.example.fathersdayjesserating.R
import stay.amplifyframework.datastore.generated.model.HotModel
import com.facebook.*
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import kotlinx.android.synthetic.main.activity_add_photos.*
import org.json.JSONException
import org.json.JSONObject


class AddPhotos : AppCompatActivity() {

    private var selectedImageUri: Uri? = null

    var callBackManager: CallbackManager?=null

    var loginButton: LoginButton?=null

    var id: String?=null
    var profAlbumID: String? = null
    var photosInAlbum: MutableList<Todo> = mutableListOf<Todo>()

    private lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        FacebookSdk.sdkInitialize(applicationContext)
        setContentView(R.layout.activity_add_photos)

        callBackManager = CallbackManager.Factory.create()
        loginButton = findViewById<LoginButton>(R.id.login_button)

        facebook_button.setOnClickListener{
            loginButton?.performClick()
            facebookInit()
        }

        UploadButton.setOnClickListener{
            dbCreate()
        }

    }

    private fun addDataSet(todo: MutableList<Todo>){
        initRecyclerView()
        todoAdapter.submitList(todo)
        visibleUpload()
    }

    private fun visibleUpload(){
        UploadButton.isVisible = true
    }



    private fun initRecyclerView(){
        recycler_view_Photo.apply {
            layoutManager = LinearLayoutManager(this@AddPhotos)
            todoAdapter = TodoAdapter()
            adapter = todoAdapter
        }
    }

    private fun facebookInit() {
        loginButton?.setPermissions("email", "public_profile", "user_photos")
        loginButton?.registerCallback(callBackManager, object : FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult) {
                var request = GraphRequest.newMeRequest(result.accessToken){ `object`, response ->
                    if (`object` != null) {
                        retrieveFacebookData(`object`)
                        Log.d("FBLOGIN_JSON_RES", `object`.toString())
                    }

                }
                var parm = Bundle()
                parm.putString("fields", "id, name, email, gender, birthday")
                request.parameters= parm
                request.executeAsync()
            }

            override fun onCancel() {
                Toast.makeText(applicationContext, "Login Canceled", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: FacebookException) {
                error.printStackTrace()
            }
        })





        }

    private fun retrieveFacebookData(jsonObject: JSONObject){
        try {
            var pictureUrl = "https:graph.facebook.com/${jsonObject.getString("id")}/picture?type=large"
            var name = jsonObject.getString("name")
            var email = jsonObject.getString("email")
            id = jsonObject.getString("id")

            Log.d("FACEBOOK LOGIN", "name: ${name}")

            var graphRequestAlbum = GraphRequest (AccessToken.getCurrentAccessToken(),
                "me/albums",
                null,
                HttpMethod.GET,
                object : GraphRequest.Callback {
                    override fun onCompleted(response: GraphResponse) {
                        val json: JSONObject? = response.getJSONObject()
                        val albums = json?.getJSONArray("data")
                        if (albums != null) {
                            for (i in 0 until albums.length()) {
                                val album = albums.getJSONObject(i)
                                if (album.getString("name").equals("Profile pictures", ignoreCase = true)) {
                                    profAlbumID = album.getString("id")
                                    Log.d("ALBUM ID", profAlbumID.toString())
                                    findPhotos()
                                    break
                                }
                            }
                        }
                    }
                }
            ).executeAsync()


        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callBackManager?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)


    }

    private fun findPhotos() {
        GraphRequest(AccessToken.getCurrentAccessToken(),
            "/${profAlbumID}/photos",
            null,
            HttpMethod.GET,
            object : GraphRequest.Callback {
                override fun onCompleted(response: GraphResponse) {
                    val json: JSONObject? = response.getJSONObject()
                    val photos = json?.getJSONArray("data")
                    Log.d("PHOTOS FIELD", photos.toString())
                    if (photos != null) {
                        for (i in 0 until photos.length()){
                            var photo = photos.getJSONObject(i)
                            photoFinal(photo.getString("id"))
                        }


                    }
                }

            }).executeAsync()



    }
    private fun photoFinal(id: String) {
        GraphRequest(AccessToken.getCurrentAccessToken(),
            "/${id}?fields=images",
            null,
            HttpMethod.GET,
            object : GraphRequest.Callback {
                override fun onCompleted(response: GraphResponse) {
                    val json: JSONObject? = response.getJSONObject()
                    val images = json?.getJSONArray("images")
                    val image = images?.getJSONObject(0)
                    val url = image?.getString("source")

                    getPhoto(url)
                }
            }).executeAsync()
}

    private fun getPhoto(url: String?) {
        photosInAlbum.add(Todo(url))
        addDataSet(photosInAlbum)
    }

    private fun dbCreate(){
        for (i in 0..photosInAlbum.size-1) {
            val copyList = photosInAlbum
            for (j in 0..photosInAlbum.size-1) {
                if (copyList[i].image == photosInAlbum[j].image) {
                    photosInAlbum.removeAt(j)
                }
            }
        }
        for (i in 0..photosInAlbum.size-1) {
            val item: HotModel = HotModel.builder()
                .points("0")
                .url(photosInAlbum[i].image)
                .build()
            Amplify.DataStore.save(
                item,
                { success -> Log.i("Amplify", "Saved item: " + success.item().url) },
                { error -> Log.e("Amplify", "Could not save item to DataStore", error) }
            )
        }
        todoAdapter.removeEverything()
        Toast.makeText(this@AddPhotos, "Photos Uploaded", Toast.LENGTH_SHORT).show()
        val intent = Intent(this@AddPhotos, MainActivity::class.java)
        startActivity(intent)
    }


}