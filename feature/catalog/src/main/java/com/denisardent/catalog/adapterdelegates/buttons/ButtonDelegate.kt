package com.denisardent.catalog.adapterdelegates.buttons

import com.denisardent.common.Items.ListItem
import com.denisardent.catalog.databinding.ItemButtonBinding
import com.denisardent.catalog.databinding.ItemButtonsListBinding
import com.denisardent.common.Items.ButtonItem
import com.denisardent.presentation.R
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

class ButtonDelegate {

    companion object{
        private fun buttonDelegate(itemClickedListener: (ButtonItem) -> Unit) = adapterDelegateViewBinding<ButtonItem, ListItem, ItemButtonBinding>(
            { layoutInflater, parent -> ItemButtonBinding.inflate(layoutInflater, parent, false) }
        ){
            binding.button.setOnClickListener {
                itemClickedListener(item)
            }

            bind {
                if (item.isPressed){
                    binding.button.setBackgroundColor(getColor(R.color.dark_blue))
                    binding.button.setTextColor(getColor(R.color.white))
                } else{
                    binding.button.setBackgroundColor(getColor(R.color.light_grey))
                    binding.button.setTextColor(getColor(R.color.grey))
                }
                binding.button.text = item.tag
            }
        }

        private fun buttonsHorizontalAdapter(itemClickedListener: (ButtonItem) -> Unit) = ListDelegationAdapter(
            buttonDelegate(itemClickedListener)
        )


        fun buttonsHorizontalDelegate(itemClickedListener: (ButtonItem) -> Unit) = adapterDelegateViewBinding<ButtonsHorizontalItem, ListItem, ItemButtonsListBinding>(
            {inflater, parentContainer -> ItemButtonsListBinding.inflate(inflater, parentContainer, false).apply {
                recyclerView.adapter = buttonsHorizontalAdapter(itemClickedListener)
            } }
        ){
            bind{
                (binding.recyclerView.adapter as ListDelegationAdapter<List<ListItem>>).apply {
                    items = item.buttons
                    notifyDataSetChanged()
                }
            }
        }
    }
}