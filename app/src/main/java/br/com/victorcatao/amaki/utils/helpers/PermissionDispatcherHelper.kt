package br.com.victorcatao.amaki.utils.helpers

import android.annotation.TargetApi
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat

class PermissionDispatcherHelper {
    private var mPermissions: Array<String>? = null
    private var mActivity: Activity? = null
    private var mFragment: Fragment? = null
    private val mRequestCode: Int
    private var mOnPermissionResult: OnPermissionResult? = null

    constructor(activity: Activity, requestCode: Int, permissions: Array<String>, onPermissionResult: OnPermissionResult) {
        mPermissions = permissions
        mActivity = activity
        mRequestCode = requestCode
        mOnPermissionResult = onPermissionResult
    }

    constructor(fragment: Fragment, requestCode: Int, permissions: Array<String>, onPermissionResult: OnPermissionResult) {
        mPermissions = permissions
        mFragment = fragment
        mActivity = fragment.activity
        mRequestCode = requestCode
        mOnPermissionResult = onPermissionResult
    }

    fun dispatchPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (areAllPermissionsGranted()) {
                mOnPermissionResult!!.onAllPermissionsGranted(mRequestCode)
            } else {
                requestPermissions(mPermissions, mRequestCode)
            }
        } else {
            //permissions before SDK M (23) are granted when user installs the app
            mOnPermissionResult!!.onAllPermissionsGranted(mRequestCode)
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun requestPermissions(permissions: Array<String>?, requestCode: Int) {
        if (isFromFragment()) {
            mFragment!!.requestPermissions(permissions!!, requestCode)
        } else {
            mActivity!!.requestPermissions(permissions!!, requestCode)
        }
    }

    private fun isFromFragment() = mFragment != null

    private fun checkPermissions(permission: String) = ContextCompat.checkSelfPermission(mActivity!!, permission)

    private fun shouldShowRequestPermissionRationale(permission: String) = if (isFromFragment()) {
        mFragment!!.shouldShowRequestPermissionRationale(permission)
    } else {
        ActivityCompat.shouldShowRequestPermissionRationale(mActivity!!, permission)
    }

    private fun areAllPermissionsGranted() = mPermissions!!.none {
        checkPermissions(it) != PackageManager.PERMISSION_GRANTED
    }

    private fun getDeniedPermissions(permissions: Array<String>, grantResults: IntArray): List<String> {
        val deniedPermissions = mutableListOf<String>()
        grantResults.forEachIndexed { index, result ->
            if (result != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(permissions[index])
            }
        }
        return deniedPermissions
    }

    private fun getPermissionsWithNeverAskAgainOption(permissions: Array<String>): List<String> {
        val permissionsWithNeverAskAgainOption = mutableListOf<String>()
        permissions.forEach {
            if (!shouldShowRequestPermissionRationale(it)) {
                permissionsWithNeverAskAgainOption.add(it)
            }
        }
        return permissionsWithNeverAskAgainOption
    }

    fun onRequestPermissionsResult(permsRequestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (permsRequestCode == mRequestCode) {

            val deniedPermissions = getDeniedPermissions(permissions, grantResults)

            if (deniedPermissions.isEmpty()) {
                mOnPermissionResult?.onAllPermissionsGranted(mRequestCode)
            } else {
                val deniedPermissionsWithNeverAskAgain = getPermissionsWithNeverAskAgainOption(deniedPermissions.toTypedArray())
                if (deniedPermissionsWithNeverAskAgain.isEmpty()) {
                    mOnPermissionResult?.onPermissionsDenied(mRequestCode, deniedPermissions, listOf())
                } else {
                    mOnPermissionResult?.onPermissionsDenied(mRequestCode, deniedPermissions, deniedPermissionsWithNeverAskAgain)
                }
            }
        }
    }


    interface OnPermissionResult {
        fun onAllPermissionsGranted(requestCode: Int)

        fun onPermissionsDenied(requestCode: Int, deniedPermissions: List<String>, deniedPermissionsWithNeverAskAgainOption: List<String>)
    }
}