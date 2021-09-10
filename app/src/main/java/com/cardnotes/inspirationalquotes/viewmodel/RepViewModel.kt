package com.cardnotes.inspirationalquotes.viewmodel

import android.os.Bundle
import androidx.lifecycle.*
import com.cardnotes.inspirationalquotes.application.Container
import com.cardnotes.inspirationalquotes.application.ContainerExtra
import com.cardnotes.inspirationalquotes.data.application.QuoteRep
import com.cardnotes.inspirationalquotes.data.database.entity.Proverb
import com.cardnotes.inspirationalquotes.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepViewModel @Inject constructor(
    private val repository: Repository,
    val container: Container,
    private val stateHandle: SavedStateHandle
) : ViewModel() {

    private val _quoteRep = MutableLiveData<List<QuoteRep>>()
    val quoteRep: LiveData<List<QuoteRep>>
        get() = _quoteRep

    var subtitle: String? = null
    var toolbarTitle: String? = null

    private var repBundle: Bundle?
        get() = stateHandle.get("bundle")
        set(value) = stateHandle.set("bundle", value)

    fun init() {
        repBundle?.getInt(ContainerExtra.SELECTED_REP_LIST_TYPE)?.let {
            when (it) {
                ContainerExtra.SELECTED_REP_LIST_LOVE -> {
                    subtitle = "Quotes"
                    toolbarTitle = "Love Quotes"
                }
                ContainerExtra.SELECTED_REP_LIST_PROVERB -> {
                    subtitle = "Proverbs"
                    toolbarTitle = "Proverbs"
                }
                ContainerExtra.SELECTED_REP_LIST_AUTHOR -> {
                    subtitle = "Quotes"
                    toolbarTitle = "Authors"
                }
                ContainerExtra.SELECTED_REP_LIST_CATEGORY -> {
                    subtitle = "Quotes"
                    toolbarTitle = "Categories"
                }
            }
        }
    }

    fun setSelected(quoteRep: QuoteRep) {
        container.selectedQuoteRep = quoteRep
    }

    fun isProverb(quoteRep: QuoteRep): Boolean {
        return quoteRep is Proverb
    }

    fun fetchQuoteReps() {
        viewModelScope.launch {
            repBundle?.getInt(ContainerExtra.SELECTED_REP_LIST_TYPE)?.let {
                with(repository.database()) {
                    _quoteRep.value =  when(it) {
                        ContainerExtra.SELECTED_REP_LIST_LOVE -> loveDao().loves()
                        ContainerExtra.SELECTED_REP_LIST_PROVERB -> proverbDao().proverbs()
                        ContainerExtra.SELECTED_REP_LIST_AUTHOR -> authorDao().authors()
                        ContainerExtra.SELECTED_REP_LIST_CATEGORY -> categoryDao().categories()
                        else -> emptyList()
                        }
                    }
                }
            }
        }

    fun setBundle(bundle: Bundle?) {
        repBundle = bundle
    }
}