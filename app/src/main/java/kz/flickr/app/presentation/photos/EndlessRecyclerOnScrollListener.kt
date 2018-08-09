package kz.flickr.app.presentation.photos

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

abstract class EndlessRecyclerOnScrollListener : RecyclerView.OnScrollListener {

    private var previousTotal = 0 // The total number of items in the dataset after the last load
    private var loading = true // True if we are still waiting for the last set of data to load.
    private val visibleThreshold = 5 // The minimum amount of items to have below your current scroll position before loading more.
    internal var firstVisibleItem: Int = 0
    internal var visibleItemCount: Int = 0
    internal var totalItemCount: Int = 0

    private var currentPage = 1

    private var mLayoutManager: RecyclerView.LayoutManager? = null

    constructor(linearLayoutManager: LinearLayoutManager) {
        this.mLayoutManager = linearLayoutManager
    }

    constructor(gridLayoutManager: RecyclerView.LayoutManager) {
        this.mLayoutManager = gridLayoutManager
    }

    fun reset() {
        previousTotal = 0
        firstVisibleItem = 0
        visibleItemCount = 0
        totalItemCount = 0
    }

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        visibleItemCount = recyclerView!!.childCount
        totalItemCount = mLayoutManager!!.itemCount


        if (mLayoutManager is LinearLayoutManager) {
            firstVisibleItem = (mLayoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        }

        if (mLayoutManager is GridLayoutManager) {
            firstVisibleItem = (mLayoutManager as GridLayoutManager).findFirstVisibleItemPosition()
        }

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false
                previousTotal = totalItemCount
            }
        }
        if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
            // End has been reached

            // Do something
            currentPage++

            onLoadMore(currentPage)

            loading = true
        }
    }

    abstract fun onLoadMore(currentPage: Int)

}