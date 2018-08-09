package kz.flickr.app.presentation.base

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.arellomobile.mvp.MvpAppCompatActivity
import kz.flickr.app.R

abstract class BaseActivity : MvpAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val actionBar = supportActionBar
        if (actionBar == null) {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(isDisplayHomeAsUpEnabled())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    protected abstract fun getLayoutId(): Int

    protected abstract fun isDisplayHomeAsUpEnabled(): Boolean

}