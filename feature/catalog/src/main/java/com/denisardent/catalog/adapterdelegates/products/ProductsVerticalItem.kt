package com.denisardent.catalog.adapterdelegates.products

import com.denisardent.common.Items.ListItem

data class ProductsVerticalItem(
    val products: List<ListItem>
): ListItem