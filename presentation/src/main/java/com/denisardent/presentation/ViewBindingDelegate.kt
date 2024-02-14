package com.denisardent.presentation

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KProperty

inline fun <reified VB: ViewBinding> Fragment.viewBinding(): ViewBindingDelegate<VB> {
    val fragment = this
    return ViewBindingDelegate(fragment, VB::class.java)
}

class ViewBindingDelegate<VB: ViewBinding>(
    private val fragment: Fragment,
    private val viewBindingClass: Class<VB>
){
    private var binding: VB? = null

    operator fun getValue(thisRef: Any?, property: KProperty<*>): VB{
        val viewLifecycleOwner = fragment.viewLifecycleOwner
        if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.DESTROYED){
            throw IllegalStateException("Called after onDestroyView()")
        } else if (fragment.view != null){
            return getOrCreateBinding(viewLifecycleOwner)
        } else {
            throw IllegalStateException("Called before onViewCreated()")
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun getOrCreateBinding(viewLifecycleOwner: LifecycleOwner): VB{
        return this.binding ?: let {
            val method = viewBindingClass.getMethod("bind", View::class.java)
            val binding = method.invoke(null, fragment.view) as VB

            viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                override fun onDestroy(owner: LifecycleOwner) {
                    super.onDestroy(owner)
                    this@ViewBindingDelegate.binding = null
                }
            })

            this.binding = binding
            binding
        }
    }
}