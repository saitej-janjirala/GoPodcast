package com.saitejajanjirala.gopodcast.util

import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object NetworkUtils {
    fun ConnectivityManager.isInternetAvailable():Boolean{
        val network = this.activeNetwork ?: return false
        val capabilities = this.getNetworkCapabilities(network) ?: return false
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
    }
}