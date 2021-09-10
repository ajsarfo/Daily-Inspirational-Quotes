package com.cardnotes.inspirationalquotes.viewmodel

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.cardnotes.inspirationalquotes.application.Container
import com.cardnotes.inspirationalquotes.application.ContainerExtra
import com.cardnotes.inspirationalquotes.data.application.Quote
import com.cardnotes.inspirationalquotes.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PagingSingleViewModel @Inject constructor(
    repository: Repository,
    container: Container,
    private val stateHandle: SavedStateHandle
) : PagingBaseViewModel(repository, container) {

    private var singleBundle: Bundle?
        get() = stateHandle.get("bundle")
        set(value) = stateHandle.set("bundle", value)

    override fun toolbarTitle(): String? {
        return singleBundle?.let { bundle ->
            bundle.getString(ContainerExtra.SELECTED_SINGLE_TYPE)?.let {
                "$it Quotes"
            }
        }
    }

    override fun getPagingFlow(): Flow<PagingData<Quote>>? {
        return singleBundle?.let { bundle ->
            bundle.getString(ContainerExtra.SELECTED_SINGLE_TYPE)?.let {
                when (it) {
                    ContainerExtra.SELECTED_SINGLE_TYPE_POPULAR -> getPagingFlow {
                        repository.database()
                            .popularQuoteDao()
                            .pagingSource() as PagingSource<Int, Quote>
                    }
                    else -> getPagingFlow {
                        repository.database()
                            .loveQuoteDao()
                            .pagingSource(it) as PagingSource<Int, Quote>
                    }
                }
            }
        }
    }

    override fun setBundle(bundle: Bundle?) {
        if(singleBundle == null) singleBundle = bundle
    }
}