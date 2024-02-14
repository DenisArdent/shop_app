package com.denisardent.catalog

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.denisardent.catalog.adapterdelegates.buttons.ButtonDelegate
import com.denisardent.catalog.adapterdelegates.products.ProductsListDelegate
import com.denisardent.catalog.adapterdelegates.buttons.ButtonsHorizontalItem
import com.denisardent.common.Items.ProductItem
import com.denisardent.catalog.adapterdelegates.products.ProductsVerticalItem
import com.denisardent.catalog.databinding.FragmentCatalogBinding
import com.denisardent.presentation.viewBinding
import com.google.android.material.checkbox.MaterialCheckBox
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CatalogFragment: Fragment(R.layout.fragment_catalog) {
    private val binding by viewBinding<FragmentCatalogBinding>()
    private val adapter = ListDelegationAdapter(
        ProductsListDelegate.productsVerticalDelegate (
            {
                findNavController().navigate(R.id.action_catalogFragment_to_productFragment, bundleOf(
                    Pair("id", it.product.id)
                ))
            },
            { id, isChecked ->
                viewModel.setIsLiked(id, isChecked)
            }
        ),
        ButtonDelegate.buttonsHorizontalDelegate{ buttonItem ->
            viewModel.changeTag(buttonItem.tag)
        }
    )
    val viewModel: CatalogViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.catalogState.collectLatest { catalogUiState ->
                    adapter.apply {
                        items = listOf(
                            ButtonsHorizontalItem(catalogUiState.buttonList),
                            ProductsVerticalItem(catalogUiState.productList.map { ProductItem(it) })
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

