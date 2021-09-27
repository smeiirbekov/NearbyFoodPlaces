package com.sm.nearbyfoodplaces.ui.adapters

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

class MyClusterRenderer(
    context: Context?,
    map: GoogleMap?,
    clusterManager: ClusterManager<MyClusterItem>?
) : DefaultClusterRenderer<MyClusterItem>(context, map, clusterManager) {

    override fun onBeforeClusterItemRendered(item: MyClusterItem, markerOptions: MarkerOptions) {
        markerOptions.title(item.myTitle)
        markerOptions.snippet(item.mySnippet)
        super.onBeforeClusterItemRendered(item, markerOptions)
    }

}