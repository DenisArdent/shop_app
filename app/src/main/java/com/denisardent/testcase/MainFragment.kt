package com.denisardent.testcase

import androidx.fragment.app.Fragment
import com.denisardent.presentation.viewBinding
import com.denisardent.testcase.databinding.FragmentMainBinding

class MainFragment: Fragment(R.layout.fragment_main) {
    val binding by viewBinding<FragmentMainBinding>()
}