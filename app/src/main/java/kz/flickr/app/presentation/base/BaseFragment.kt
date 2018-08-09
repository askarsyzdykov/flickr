package kz.flickr.app.presentation.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import kz.flickr.app.App

abstract class BaseFragment : MvpAppCompatFragment(), BaseMvpView {

    open val title: String? = ""

    abstract fun getLayoutId(): Int

    var binding: ViewDataBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component?.injectBaseFragment(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return if (binding != null) {
            binding?.root
        } else {
            inflater.inflate(getLayoutId(), container, false);
        }
    }
}