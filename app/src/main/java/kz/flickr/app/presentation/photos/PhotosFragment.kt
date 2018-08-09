package kz.flickr.app.presentation.photos

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.stfalcon.frescoimageviewer.ImageViewer
import kz.flickr.app.App
import kz.flickr.app.MainActivity
import kz.flickr.app.R
import kz.flickr.app.data.network.models.Photo
import kz.flickr.app.di.DaggerPhotosPresenterComponent
import kz.flickr.app.presentation.base.BaseFragment
import kz.flickr.app.presentation.utils.getItemAsString
import kz.flickr.app.presentation.utils.hideKeyboard
import kotlinx.android.synthetic.main.fragment_photos.*


class PhotosFragment : BaseFragment(), PhotosView, PhotosAdapter.PhotosAdapterClickListener {

    @InjectPresenter
    lateinit var presenter: PhotosPresenter

    @ProvidePresenter
    internal fun providePresenter(): PhotosPresenter {
        val presenterComponent = DaggerPhotosPresenterComponent.builder()
                .applicationComponent(App.component)
                .build()
        return presenterComponent.getPhotosPresenter()
    }

    override fun getLayoutId() = R.layout.fragment_photos

    lateinit var loadMoreListener: EndlessRecyclerOnScrollListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        loadMoreListener = object : EndlessRecyclerOnScrollListener(rvPhotos.layoutManager) {
            override fun onLoadMore(currentPage: Int) {
                presenter.loadMore()
            }
        }
        rvPhotos.addOnScrollListener(loadMoreListener)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.search, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView

        val searchManager = context?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val componentName = ComponentName(context, MainActivity::class.java)
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnSuggestionListener(object : SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {
                return false
            }

            override fun onSuggestionClick(position: Int): Boolean {
                searchView.setQuery(searchView.suggestionsAdapter.getItemAsString(), true)
                return true
            }

        })
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                presenter.search(query)
                loadMoreListener.reset()

                hideKeyboard(searchView)

                val suggestions = SearchRecentSuggestions(context,
                        SearchHistoryProvider.AUTHORITY, SearchHistoryProvider.MODE)
                suggestions.saveRecentQuery(query, null)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun onPhotoClick(photo: Photo) {
        presenter.onPhotoClick(photo)
    }

    override fun showProgressDialog() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressDialog() {
        progressBar.visibility = View.INVISIBLE
    }

    override fun onSuccess(photos: List<Photo>) {
        rvPhotos.adapter = PhotosAdapter(photos, this)
    }

    override fun onLoadMoreSucceeded(photos: List<Photo>) {
        rvPhotos.adapter.notifyDataSetChanged()
    }

    override fun onError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun showPhotoScreen(url: String) {
        ImageViewer.Builder(context, arrayListOf(url))
                .show()
    }
}
