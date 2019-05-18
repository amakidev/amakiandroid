package br.com.victorcatao.amaki.utils.helpers

import android.Manifest
import android.app.Activity
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle

private val PERMISSIONS_LOCATION = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
private const val REQUEST_CODE = 700

class LocationHelper(context: Activity, private val mOnLocationListener: OnLocationListener) : PermissionDispatcherHelper.OnPermissionResult {

    private val mLocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private var mPermissionDispatcherHelper: PermissionDispatcherHelper = PermissionDispatcherHelper(context, REQUEST_CODE, PERMISSIONS_LOCATION, this)

    private val mLocationListener by lazy {
        object: LocationListener {
            override fun onLocationChanged(p0: Location?) {
                p0?.let { mOnLocationListener.onLocationChanged(it) }
            }

            override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {}
            override fun onProviderEnabled(p0: String?) {}
            override fun onProviderDisabled(p0: String?) {}
        }
    }

    fun start() {
        mPermissionDispatcherHelper.dispatchPermissions()
    }

    @SuppressWarnings("MissingPermission")
    fun stop() {
        mLocationManager.removeUpdates(mLocationListener)
    }

    fun onRequestPermissionsResult(permsRequestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        mPermissionDispatcherHelper.onRequestPermissionsResult(permsRequestCode, permissions, grantResults)
    }

    @SuppressWarnings("MissingPermission")
    private fun startLocationListener() {
        mLocationManager.getProviders(true).forEach {
            mLocationManager.requestLocationUpdates(it, 1, 1f, mLocationListener)
        }
    }

    override fun onAllPermissionsGranted(requestCode: Int) {
        mOnLocationListener.onAllPermissionsGranted()
        startLocationListener()
    }

    override fun onPermissionsDenied(requestCode: Int, deniedPermissions: List<String>, deniedPermissionsWithNeverAskAgainOption: List<String>) {
        mOnLocationListener.onPermissionsDenied()
    }

    interface OnLocationListener {
        fun onLocationChanged(location: Location)
        fun onAllPermissionsGranted()
        fun onPermissionsDenied()
    }
}
