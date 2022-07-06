package stay.example.fathersdayjesserating.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import stay.example.fathersdayjesserating.FragmentNewOld
import stay.example.fathersdayjesserating.FragmentTop
import com.example.fathersdayjesserating.R

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position){
            0 -> fragment = FragmentNewOld()
            1 -> fragment = FragmentTop()
        }
        return fragment!!
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 2
    }
}