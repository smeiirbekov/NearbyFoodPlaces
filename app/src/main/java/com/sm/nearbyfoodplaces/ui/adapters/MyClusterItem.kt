package com.sm.nearbyfoodplaces.ui.adapters

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class MyClusterItem(
    val myPosition: LatLng,
    val myTitle: String?,
    val mySnippet: String?
): ClusterItem {

    override fun getPosition() = myPosition

    override fun getTitle() = myTitle

    override fun getSnippet() = mySnippet
}
