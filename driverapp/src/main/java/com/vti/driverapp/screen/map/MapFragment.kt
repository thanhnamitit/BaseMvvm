package com.vti.driverapp.screen.map

import android.os.Handler
import com.google.android.gms.maps.GoogleMap
import com.vti.base.navigation.NavigationBuilder
import com.vti.common.screen.BaseMainViewModel
import com.vti.common.screen.map.BaseMapFragment
import com.vti.driverapp.R
import com.vti.driverapp.screen.MainViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel
import kotlin.reflect.KClass

class MapFragment : BaseMapFragment<MapViewModel>() {
    val mainViewModel by sharedViewModel<MainViewModel>()
    override fun requireMainViewModel(): BaseMainViewModel = mainViewModel
    override fun getViewModelType(): KClass<MapViewModel> = MapViewModel::class

    override fun onEverythingReady(googleMap: GoogleMap) {
        Handler().postDelayed({ navigation?.updateBackgroudColor(R.color.md_black_1000) }, 3000)
    }

    override fun getNavigationBuilder(): NavigationBuilder<*>? {
        return NavigationBuilder.default().title("Chiu chiu").titleTextColor(R.color.md_black_1000).navigationIconRes(R.mipmap.ic_launcher_round).backgroundColor(R.color.md_red_A700)
    }
}