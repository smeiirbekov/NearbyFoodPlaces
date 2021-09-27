package com.sm.nearbyfoodplaces.ui.adapters

import com.sm.domain.models.ArcGisPlace
import com.sm.nearbyfoodplaces.databinding.PlacesItemBinding
import com.sm.nearbyfoodplaces.utils.bindings.BindingBasicRvAdapter

class PlacesAdapter(
    private val onItemClick: (ArcGisPlace) -> Unit
): BindingBasicRvAdapter<ArcGisPlace, PlacesItemBinding>(PlacesItemBinding::inflate) {

    override fun bind(item: ArcGisPlace, binding: PlacesItemBinding) {
        binding.run {
            tvTitle.text = item.address
            tvSnippet.text = item.attributes?.type
            root.setOnClickListener { onItemClick.invoke(item) }
        }
    }

}