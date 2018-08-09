package kz.flickr.app

import android.content.Context
import io.reactivex.Single
import junit.framework.Assert.assertNotNull
import kz.flickr.app.data.network.models.Photo
import kz.flickr.app.data.network.models.PhotosWrapper
import kz.flickr.app.domain.interactors.PhotosInteractor
import kz.flickr.app.domain.repositories.PhotosRepository
import kz.flickr.app.presentation.common.RxSchedulersProvider
import kz.flickr.app.presentation.photos.PhotosPresenter
import kz.flickr.app.presentation.photos.PhotosView
import kz.flickr.app.system.ResourceManager
import org.junit.Before
import org.junit.ClassRule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Matchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.net.UnknownHostException

@RunWith(JUnit4::class)
class PhotosPresenterTest {

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxTestRule()
    }

    @Mock
    lateinit var view: PhotosView

    lateinit var presenter: PhotosPresenter

    @Mock
    lateinit var context: Context

    private lateinit var repository: PhotosTestRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        repository = PhotosTestRepository()
        val interactor = PhotosInteractor(repository)
        presenter = PhotosPresenter(interactor, RxSchedulersProvider(), ResourceManager(context))
    }

    @Test
    @Throws(Exception::class)
    fun testCreated() {
        assertNotNull(presenter)
    }

    @Test
    @Throws(Exception::class)
    fun testOnPhotoClick() {
        presenter.attachedViews.add(view)
        presenter.onPhotoClick(Photo("", "", "", "", 1, 1, 0, 0))
        verify(view).showPhotoScreen(Matchers.anyString())

        verifyNoMoreInteractions(view)
    }

    @Test
    @Throws(Exception::class)
    fun testOnFirstAttach() {
        presenter.attachView(view)

        val inOrder = inOrder(view, view, view)
        inOrder.verify(view).showProgressDialog()
        inOrder.verify(view).onSuccess(any())
        inOrder.verify(view).hideProgressDialog()

        verifyNoMoreInteractions(view)
    }

    @Test
    @Throws(Exception::class)
    fun testOnLoadMore() {
        presenter.attachedViews.add(view)
        presenter.photos = mutableListOf()
        presenter.loadMore()

        val inOrder = inOrder(view, view, view)
        inOrder.verify(view).showProgressDialog()
        inOrder.verify(view).onLoadMoreSucceeded(any())
        inOrder.verify(view).hideProgressDialog()

        verifyNoMoreInteractions(view)
    }

    @Test
    @Throws(Exception::class)
    fun testOnNoInternetConnection() {
        `when`(context.getString(anyInt())).thenReturn("")

        presenter.attachedViews.add(view)
        presenter.search("NoInternetTest")

        val inOrder = inOrder(view, view, view)
        inOrder.verify(view).showProgressDialog()
        inOrder.verify(view).onError(anyString())
        inOrder.verify(view).hideProgressDialog()

        verifyNoMoreInteractions(view)
    }

    private inner class PhotosTestRepository : PhotosRepository {
        override fun loadPhotos(page: Int, text: String?): Single<PhotosWrapper> {
            return if (text == "NoInternetTest") {
                Single.error(UnknownHostException(""))
            } else {
                Single.just(PhotosWrapper(listOf(), page, 3, 100, 300))
            }
        }
    }

    private fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    private fun <T> uninitialized(): T = null as T
}
