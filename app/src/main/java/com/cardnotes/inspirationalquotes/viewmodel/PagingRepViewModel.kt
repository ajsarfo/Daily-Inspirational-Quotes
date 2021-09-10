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
class PagingRepViewModel @Inject constructor(
    repository: Repository,
    container: Container,
    private val stateHandle: SavedStateHandle
) : PagingBaseViewModel(repository, container) {

    private var quoteBundle: Bundle?
        get() = stateHandle.get("bundle")
        set(value) = stateHandle.set("bundle", value)

    override fun toolbarTitle(): String? {
        return quoteBundle?.let { bundle ->
            bundle.getString(ContainerExtra.SELECTED_QUOTE_REP)?.let { rep ->
                bundle.getInt(ContainerExtra.SELECTED_QUOTE_REP_TYPE).let {
                    when(it) {
                        ContainerExtra.SELECTED_QUOTE_REP_TYPE_PROVERB -> "$rep Proverbs"
                        else -> "$rep Quotes"
                    }
                }
            }
        }
    }

    override fun getPagingFlow(): Flow<PagingData<Quote>>? {
        return quoteBundle?.let { bundle ->
            bundle.getString(ContainerExtra.SELECTED_QUOTE_REP)?.let { rep ->
                bundle.getInt(ContainerExtra.SELECTED_QUOTE_REP_TYPE).let {
                    when (it) {
                        ContainerExtra.SELECTED_QUOTE_REP_TYPE_LOVE -> getPagingFlow {
                            repository.database()
                                .loveQuoteDao()
                                .pagingSource(rep) as PagingSource<Int, Quote>
                        }

                        ContainerExtra.SELECTED_QUOTE_REP_TYPE_PROVERB -> getPagingFlow {
                            repository.database()
                                .proverbQuoteDao()
                                .pagingSource(rep) as PagingSource<Int, Quote>
                        }

                        ContainerExtra.SELECTED_QUOTE_REP_TYPE_AUTHOR -> getPagingFlow {
                            repository.database()
                                .authorQuoteDao()
                                .pagingSource(rep) as PagingSource<Int, Quote>
                        }

                        ContainerExtra.SELECTED_QUOTE_REP_TYPE_CATEGORY -> getPagingFlow {
                            repository.database()
                                .categoryQuoteDao()
                                .pagingSource(rep) as PagingSource<Int, Quote>
                        }
                        else -> null
                    }
                }
            }
        }
    }

    override fun setBundle(bundle: Bundle?) {
        if(quoteBundle == null) quoteBundle = bundle
    }
}