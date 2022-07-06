package stay.example.fathersdayjesserating

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.viewpager.widget.ViewPager
import com.example.fathersdayjesserating.databinding.ActivityRankingsBinding
import stay.example.fathersdayjesserating.ui.main.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragmentnewold_layout.*

class Rankings : AppCompatActivity() {

    private lateinit var binding: ActivityRankingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding = ActivityRankingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
    }

}