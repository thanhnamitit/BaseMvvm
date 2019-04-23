package com.navanvine.app.screen.main

import androidx.recyclerview.widget.LinearLayoutManager
import com.navanvine.app.R
import com.navanvine.app.base.BaseFragment
import com.navanvine.app.databinding.FragmentFakeSmsBinding
import kotlin.reflect.KClass

class FakeSmsFragment : BaseFragment<FragmentFakeSmsBinding, FakeSmsViewModel>() {
    override fun getViewModelType(): KClass<FakeSmsViewModel> {
        return FakeSmsViewModel::class
    }

    override fun getLayoutId(): Int = R.layout.fragment_fake_sms

    override fun onViewReady() {
        super.onViewReady()
        setupRecyclerView()
    }

    fun setupRecyclerView() {
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = FakeSmsAdapter(viewModel.modelsProcessor)
    }
}
