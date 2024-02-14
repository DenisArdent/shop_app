package com.denisardent.product

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.denisardent.common.Items.ImageItem
import com.denisardent.common.Items.ListItem
import com.denisardent.common.Product
import com.denisardent.presentation.viewBinding
import com.denisardent.product.databinding.FragmentProductBinding
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProductFragment: Fragment(R.layout.fragment_product) {
    private val binding by viewBinding<FragmentProductBinding>()

    @Inject
    lateinit var factory: ProductViewModel.Factory

    val id by lazy {
        arguments?.getString("id") ?:""
    }

    private val viewModel by viewModels<ProductViewModel>{
        ProductViewModel.provideProductViewModelFactory(factory, id)
    }

    private val adapter = ListDelegationAdapter(
        ProductDelegate.productImageDelegate()
    )


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpScreenUtils()


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.productState.collectLatest { product ->
                    if (product == null){
                        binding.progressBar.visibility = View.VISIBLE
                    } else{
                        setupScreenUI(product)
                    }

                }
            }
        }
    }

    private fun setUpScreenUtils(){
        binding.productImageViewPager.adapter = adapter
        binding.exposeButton.setOnClickListener {
            if (binding.exposeButton.text == "Скрыть"){
                binding.exposeButton.text = "Подробнее"
                binding.sizeableLayout.visibility = View.GONE
            }else{
                binding.exposeButton.text = "Скрыть"
                binding.sizeableLayout.visibility = View.VISIBLE
            }
        }
    }

    private fun setupScreenUI(product: Product){
        val unit = product.unit
        val characteristics = product.info
        with(binding){
            progressBar.visibility = View.GONE
            adapter.apply {
                items = product.image.map { ImageItem(it) }
                notifyDataSetChanged()
            }
            circleIndicator.setViewPager(productImageViewPager)
            brand.text = product.title
            subtitle.text = product.subtitle
            available.text = "Доступно для заказа ${product.available} штук"
            rating.text = product.rating.toString()
            ratingBar.rating = product.rating
            reviewCount.text = "(${product.feedbackCount})"
            price.text = "${product.priceWithDiscount}$unit"
            oldPrice.text = "${product.price}$unit"
            discount.text = "-${product.discount}%"
            brandButtonTv.text = product.title
            descriptionText.text = product.description
            try {
                characteristics1Start.text = characteristics[0].title
                characteristics1End.text = characteristics[0].value
            } catch (_: Exception){ }
            try {
                characteristics2Start.text = characteristics[1].title
                characteristics2End.text = characteristics[1].value
            } catch (_: Exception){ }
            try {
                characteristics3Start.text = characteristics[2].title
                characteristics3End.text = characteristics[2].value
            } catch (_: Exception){ }
        }
    }
}