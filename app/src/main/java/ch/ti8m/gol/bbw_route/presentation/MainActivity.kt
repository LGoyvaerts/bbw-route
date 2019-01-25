package ch.ti8m.gol.bbw_route.presentation

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.widget.TextView
import ch.ti8m.gol.bbw_route.R
import ch.ti8m.gol.bbw_route.databinding.ActivityMainBinding
import ch.ti8m.gol.bbw_route.presentation.fragments.TabHistory
import ch.ti8m.gol.bbw_route.presentation.fragments.TabOverview

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        val mViewPager: ViewPager = binding.container
        mViewPager.adapter = mSectionsPagerAdapter
        mViewPager.offscreenPageLimit = 2

        mViewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tablayoutMain))
        binding.tablayoutMain.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(mViewPager))

        createTabIcons()
    }

    private fun createTabIcons() {
        binding.tablayoutMain.getTabAt(0)?.customView = createTab(R.string.title_overview, R.drawable.ic_location)
        binding.tablayoutMain.getTabAt(1)?.customView = createTab(R.string.title_history, R.drawable.ic_history)
    }

    private fun createTab(tabTitle: Int, icon: Int): TextView {
        val temp = LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as TextView
        temp.setText(tabTitle)
        val intrinsic_bounds = 0
        temp.setCompoundDrawablesWithIntrinsicBounds(intrinsic_bounds, icon, intrinsic_bounds, intrinsic_bounds)
        return temp
    }

    class SectionsPagerAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment? {
            when (position) {
                0 -> return TabOverview.newInstance()
                1 -> return TabHistory.newInstance()
                else -> return null
            }
        }

        override fun getCount(): Int {
            return 2
        }

    }

}
