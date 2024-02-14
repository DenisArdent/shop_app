package com.denisardent.catalog.adapterdelegates.buttons

import com.denisardent.common.Items.ListItem

data class ButtonsHorizontalItem(
    val buttons: List<ListItem>
): ListItem
