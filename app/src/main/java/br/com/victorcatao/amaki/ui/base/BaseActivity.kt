package br.com.victorcatao.amaki.ui.base

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem

import br.com.victorcatao.amaki.R

abstract class BaseActivity : AppCompatActivity() {

    protected val TAG = if (javaClass.enclosingClass != null) javaClass.enclosingClass.simpleName else javaClass.simpleName

    val context: Context
        get() = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //FIX ORIENTATION TO PORTRAIT
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    //TOOLBAR METHODS
    fun setToolbar(title: String, displayHomeAsUpEnabled: Boolean) {
        setToolbar(title)
        supportActionBar!!.setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled)
    }

    //ACTION BAR METHODS
    fun setToolbar(title: String) {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        setTitle(title)
    }

    //MENU METHODS
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                //this is used because when user hits home button the previous view is reconstructed
                //and when back button (at navbar) is pressed this doesn't happen,
                //so this makes the previous view never reconstructed when home is hit.
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}