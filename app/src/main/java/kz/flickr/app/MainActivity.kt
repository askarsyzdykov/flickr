package kz.flickr.app

import kz.flickr.app.presentation.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun getLayoutId() = R.layout.activity_main

    override fun isDisplayHomeAsUpEnabled() = false

}
