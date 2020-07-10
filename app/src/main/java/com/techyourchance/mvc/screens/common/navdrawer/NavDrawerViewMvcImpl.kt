package com.techyourchance.mvc.screens.common.navdrawer

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.techyourchance.mvc.R
import com.techyourchance.mvc.screens.common.views.BaseObservableViewMvc

class NavDrawerViewMvcImpl(inflater: LayoutInflater, parent: ViewGroup?) :
    BaseObservableViewMvc<NavDrawerViewMvc.Listener?>(), NavDrawerViewMvc {
    private val mDrawerLayout: DrawerLayout
    override val fragmentFrame: FrameLayout
    private val mNavigationView: NavigationView
    override fun openDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START)
    }

    override val isDrawerOpen: Boolean
        get() = mDrawerLayout.isDrawerOpen(GravityCompat.START)

    override fun closeDrawer() {
        mDrawerLayout.closeDrawers()
    }

    init {
        setRootView(inflater.inflate(R.layout.layout_drawer, parent, false))
        mDrawerLayout = findViewById(R.id.drawer_layout)!!
        fragmentFrame = findViewById(R.id.frame_content)!!
        mNavigationView = findViewById(R.id.nav_view)!!
        mNavigationView.setNavigationItemSelectedListener { item ->
            mDrawerLayout.closeDrawers()
            if (item.itemId == R.id.drawer_menu_questions_list) {
                for (listener in listeners) {
                    listener!!.onQuestionsListClicked()
                }
            }
            false
        }
    }
}
