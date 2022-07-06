package stay.example.fathersdayjesserating

import android.app.Application
import com.facebook.FacebookSdk

class AppController: Application() {
    override fun onCreate() {
        super.onCreate()
        FacebookSdk.sdkInitialize(applicationContext)
    }
}