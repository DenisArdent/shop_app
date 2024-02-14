package com.denisardent.catalog.adapterdelegates.products

import com.denisardent.catalog.databinding.ItemProductCardBinding
import com.denisardent.catalog.databinding.ItemProductsListBinding
import com.denisardent.common.Items.ImageItem
import com.denisardent.common.Items.ListItem
import com.denisardent.common.Items.ProductItem
import com.denisardent.presentation.databinding.ItemProductImageBinding
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

class ProductsListDelegate {

    companion object{
        private fun productImageDelegate() = adapterDelegateViewBinding<ImageItem, ListItem, ItemProductImageBinding>(
            {layoutInflater, parent -> ItemProductImageBinding.inflate(layoutInflater, parent, false) }
        ){
            bind {
                binding.productImageView.setImageResource(item.imageId)
            }
        }

        private fun productImageAdapter() = ListDelegationAdapter(
            productImageDelegate()
        )

        private fun productDelegate(itemClickedListener: (ProductItem) -> Unit, likedCheckBoxListener: (String, Boolean) -> Unit) = adapterDelegateViewBinding<ProductItem, ListItem, ItemProductCardBinding>(
            { layoutInflater, parent -> ItemProductCardBinding.inflate(layoutInflater, parent, false).apply {
                productImageViewPager.adapter = productImageAdapter()
            }}
        ){
            binding.root.setOnClickListener {
                itemClickedListener(item)
            }

            binding.likeCheckbox.setOnCheckedChangeListener{ buttonView, isChecked ->
                likedCheckBoxListener(item.product.id, isChecked)
            }

            bind {
                val product = item.product
                (binding.productImageViewPager.adapter as ListDelegationAdapter<List<ListItem>>).apply {
                    items = item.product.image.map { ImageItem(it) }
                    notifyDataSetChanged()
                }
                binding.likeCheckbox.isChecked = product.isLiked
                binding.circleIndicator.setViewPager(binding.productImageViewPager)
                binding.title.text = product.title
                binding.oldCost.text = product.price
                binding.newCost.text = product.priceWithDiscount
                binding.unit.text = product.unit
                binding.discount.text = "-${product.discount}%"
                binding.subtitle.text = product.subtitle
                binding.rating.text = product.rating.toString()
                binding.ratingQuantity.text = "(${product.feedbackCount})"
            }
        }

        private fun verticalProductAdapter(itemClickedListener: (ProductItem) -> Unit, likedCheckBoxListener: (String, Boolean) -> Unit) = ListDelegationAdapter(
            productDelegate(itemClickedListener, likedCheckBoxListener)
        )

        fun productsVerticalDelegate(itemClickedListener: (ProductItem) -> Unit, likedCheckBoxListener: (String, Boolean) -> Unit) = adapterDelegateViewBinding<ProductsVerticalItem, ListItem, ItemProductsListBinding>(
            {inflater, parentContainer -> ItemProductsListBinding.inflate(inflater, parentContainer, false).apply {
                recyclerView.adapter = verticalProductAdapter(itemClickedListener, likedCheckBoxListener)
            } }
        ){
            bind{
                (binding.recyclerView.adapter as ListDelegationAdapter<List<ListItem>>).apply {
                    items = item.products
                    notifyDataSetChanged()
                }
            }
        }
    }
}