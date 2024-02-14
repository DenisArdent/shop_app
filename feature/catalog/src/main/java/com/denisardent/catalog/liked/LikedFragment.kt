package com.denisardent.catalog.liked

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.denisardent.catalog.CatalogViewModel
import com.denisardent.catalog.R
import com.denisardent.catalog.adapterdelegates.buttons.ButtonDelegate
import com.denisardent.catalog.adapterdelegates.buttons.ButtonsHorizontalItem
import com.denisardent.catalog.adapterdelegates.products.ProductsListDelegate
import com.denisardent.catalog.adapterdelegates.products.ProductsVerticalItem
import com.denisardent.catalog.databinding.FragmentCatalogBinding
import com.denisardent.catalog.databinding.FragmentLikedBinding
import com.denisardent.common.Items.ProductItem
import com.denisardent.presentation.viewBinding
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LikedFragment: Fragment(R.layout.fragment_liked) {
    private val binding by viewBinding<FragmentLikedBinding>()

    private val adapter = ListDelegationAdapter(
        ProductsListDelegate.productsVerticalDelegate (
            {
                findNavController().navigate(R.id.action_likedFragment_to_productFragment, bundleOf(
                    Pair("id", it.product.id)
                )
                )
            },
            { id, isChecked ->
                viewModel.setIsLiked(id, isChecked)
            }
        )
    )
    val viewModel: LikedViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.likedState.collectLatest { likedState ->
                    adapter.apply {
                        items = listOf(
                            ProductsVerticalItem(likedState.map { ProductItem(it) })
                        )
                        notifyDataSetChanged()
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.updateList()
    }

}