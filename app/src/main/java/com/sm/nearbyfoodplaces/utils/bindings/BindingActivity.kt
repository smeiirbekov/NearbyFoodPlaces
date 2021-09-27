package com.sm.nearbyfoodplaces.utils.bindings

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

open class BindingActivity<B : ViewBinding> constructor(
    private val inflate: (inflater: LayoutInflater) -> B?
) : AppCompatActivity() {

    private var _binding: B? = null
    protected val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflate.invoke(layoutInflater)
        setContentView(_binding?.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}