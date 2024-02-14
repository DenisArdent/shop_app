package com.denisardent.product

import com.denisardent.common.Items.ImageItem
import com.denisardent.common.Items.ListItem
import com.denisardent.presentation.databinding.ItemProductImageBinding
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

class ProductDelegate {
    companion object{
        fun productImageDelegate() = adapterDelegateViewBinding<ImageItem, ListItem, ItemProductImageBinding>(
            {layoutInflater, parent -> ItemProductImageBinding.inflate(layoutInflater, parent, false) }
        ){
            bind {
                binding.productImageView.setImageResource(item.imageId)
            }
        }
    }
}