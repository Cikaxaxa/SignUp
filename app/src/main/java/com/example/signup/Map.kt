package com.example.signup

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.SpinnerAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.arcgismaps.ApiKey
import com.arcgismaps.ArcGISEnvironment
import com.arcgismaps.location.LocationDisplayAutoPanMode
import com.arcgismaps.mapping.ArcGISMap
import com.arcgismaps.mapping.BasemapStyle
import com.arcgismaps.mapping.view.MapView
import com.example.signup.databinding.ActivityMapBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class Map : AppCompatActivity() {

    private lateinit var mapView : MapView
    private lateinit var activityMapBinding: ActivityMapBinding
    private lateinit var spinnerAdapter: SpinnerAdapter




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        // authentication with an API key or named user is
        // required to access basemaps and other location services
        ArcGISEnvironment.apiKey = ApiKey.create("AAPK88529847185949ea825e3574ecc6c9b1qRO6NownHM5HmeA7l4dVMvm180y_oJcgTjabZ5qq3gdntEyRiFsBdzCVm7WItErn")

        ArcGISEnvironment.applicationContext = applicationContext



        // set up data binding for the activity

        activityMapBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        lifecycle.addObserver(activityMapBinding.mapView)

        // create and add a map with a navigation night basemap style
        val map = ArcGISMap(BasemapStyle.ArcGISNavigationNight)
        activityMapBinding.mapView.map = map



        val locationDisplay = mapView.locationDisplay

        lifecycleScope.launch {
            // listen to changes in the status of the location data source
            locationDisplay.dataSource.start()
                .onSuccess {
                    // permission already granted, so start the location display
                    activityMapBinding.spinner.select(1)
                }.onFailure {
                    // check permissions to see if failure may be due to lack of permissions
                    requestPermissions()
                }
        }

        // populate the list for the location display options for the spinner's adapter
        val panModeSpinnerElements = arrayListOf(
            ItemData("Stop", R.drawable.locationdisplaydisabled),
            ItemData("On", R.drawable.locationdisplayon),
            ItemData("Re-center", R.drawable.locationdisplayrecenter),
            ItemData("Navigation", R.drawable.locationdisplaynavigation),
            ItemData("Compass", R.drawable.locationdisplayheading)
        )

        activityMapBinding.spinner.apply {
            adapter = SpinnerAdapter(this@Map, R.id.locationTextView, panModeSpinnerElements)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    when (panModeSpinnerElements[position].text) {
                        "Stop" ->  // stop location display
                            lifecycleScope.launch {
                                locationDisplay.dataSource.stop()
                            }
                        "On" ->  // start location display
                            lifecycleScope.launch {
                                locationDisplay.dataSource.start()
                            }
                        "Re-center" -> {
                            // re-center MapView on location
                            locationDisplay.setAutoPanMode(LocationDisplayAutoPanMode.Recenter)
                        }
                        "Navigation" -> {
                            // start navigation mode
                            locationDisplay.setAutoPanMode(LocationDisplayAutoPanMode.Navigation)
                        }
                        "Compass" -> {
                            // start compass navigation mode
                            locationDisplay.setAutoPanMode(LocationDisplayAutoPanMode.CompassNavigation)
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }
    }

    /**
     * Request fine and coarse location permissions for API level 23+.
     */
    private fun requestPermissions() {
        // coarse location permission
        val permissionCheckCoarseLocation =
            ContextCompat.checkSelfPermission(this@Map, ACCESS_COARSE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED
        // fine location permission
        val permissionCheckFineLocation =
            ContextCompat.checkSelfPermission(this@Map, ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED

        // if permissions are not already granted, request permission from the user
        if (!(permissionCheckCoarseLocation && permissionCheckFineLocation)) {
            ActivityCompat.requestPermissions(
                this@Map,
                arrayOf(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION),
                2
            )
        } else {
            // permission already granted, so start the location display
            lifecycleScope.launch {
                mapView.locationDisplay.dataSource.start().onSuccess {
                    activityMapBinding.spinner.setSelection(1, true)
                }
            }
        }
    }

    /**
     * Handle the permissions request response.
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            lifecycleScope.launch {
                mapView.locationDisplay.dataSource.start().onSuccess {
                    activityMapBinding.spinner.setSelection(1, true)
                }
            }
        } else {
            Snackbar.make(
                mapView,
                "Location permissions required to run this sample!",
                Snackbar.LENGTH_LONG
            ).show()
            // update UI to reflect that the location display did not actually start
            activityMapBinding.spinner.setSelection(0, true)
        }
    }
}