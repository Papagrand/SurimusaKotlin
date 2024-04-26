package com.example.surimusakotlin.data

interface ScreenSwitchable {
    fun showError()

    fun showNoData()

    fun hideError()

    fun showData()

    fun showDeleteCross()

    fun hideDeleteCross()

    fun showSearchHistory()

    fun hideSearchHistory()

    fun showFoodRecyclerView()
    fun hideFoodRecyclerView()


}