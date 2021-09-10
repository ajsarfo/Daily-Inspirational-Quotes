package com.cardnotes.inspirationalquotes.application.listener

import com.cardnotes.inspirationalquotes.application.enums.Destination

interface NavigationListener {
    fun navigate(destination: Destination)
}