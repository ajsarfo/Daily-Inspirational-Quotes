package com.cardnotes.inspirationalquotes.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cardnotes.inspirationalquotes.SHOW_GRADIENT_BACKGROUND
import com.cardnotes.inspirationalquotes.application.Container
import com.cardnotes.inspirationalquotes.application.cache.GradientCache
import com.cardnotes.inspirationalquotes.application.cache.ImageCache
import com.cardnotes.inspirationalquotes.application.cache.PictureCache
import com.cardnotes.inspirationalquotes.application.image.ImageStore
import com.cardnotes.inspirationalquotes.data.application.QuoteRep
import com.cardnotes.inspirationalquotes.data.database.entity.Author
import com.cardnotes.inspirationalquotes.data.database.entity.Category
import com.cardnotes.inspirationalquotes.data.repository.Repository
import com.cardnotes.inspirationalquotes.readSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

object PageItem {
    var message: String? = null
    var uri: Uri? = null
}

class MainUI(
    val authorBlock: List<Author>,
    val categoryBlock: List<Category>,
    val topBlock : List<QuoteRep>
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    val container: Container,
    imageStore: ImageStore,
    @ApplicationContext context: Context
) : ViewModel() {

    private val _pageItem = MutableLiveData<PageItem>()
    val pageItem: LiveData<PageItem>
        get() = _pageItem

    private val _mainUI = MutableLiveData<MainUI>()
    val mainUI: LiveData<MainUI>
    get() = _mainUI

    private val gradientCache = GradientCache<String>(imageStore)
    private val pictureCache = PictureCache<String>(imageStore)
    private lateinit var imageCache: ImageCache<String>

    private lateinit var iterator: Iterator<String>

    init {
        viewModelScope.launch {
            context.readSettings(SHOW_GRADIENT_BACKGROUND, false).collect {
                imageCache = if (it) gradientCache else pictureCache
            }
        }

        viewModelScope.launch {
            //Setup pager
            setupIterator()
            _pageItem.value = iterator.current().let {
                PageItem.apply {
                    message = it
                    uri = imageCache.get(it)
                }
            }
            //Setup block
            _mainUI.value = MainUI(
                repository.database().authorDao().random(4),
                repository.database().categoryDao().random(4),
                mutableListOf<QuoteRep>().apply {
                    addAll(repository.database().authorDao().random(2))
                    addAll(repository.database().categoryDao().random(2))
                    shuffle()
                }
            )
            //Update pager
            while (true) {
                delay(TimeUnit.SECONDS.toMillis(10))
                _pageItem.value = iterator.next().let {
                    PageItem.apply {
                        message = it
                        uri = imageCache.get(it)
                    }
                }
            }
        }
    }

    private suspend fun setupIterator() {
        iterator = Iterator(
            0,
            repository.database().authorQuoteDao().random(20)
                .filter { it.message.length < 200 }
                .take(4)
                .map { it.message }
        )
    }

    private class Iterator<T>(private val initial: Int, private val list: List<T>) {

        private var iterator = initial

        fun next(): T {
            iterator++
            if (iterator >= list.size) iterator = initial
            return list[iterator]
        }

        fun current(): T = list[iterator]
    }
}