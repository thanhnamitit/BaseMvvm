package com.navanvine.fakesms.screen.main

import com.navanvine.fakesms.R
import com.navanvine.fakesms.base.BaseFragment
import com.navanvine.fakesms.databinding.FragmentFakeSmsBinding

class FakeSmsFragment : BaseFragment<FragmentFakeSmsBinding, FakeSmsViewModel>() {
    override fun getViewModelType(): Class<FakeSmsViewModel> = FakeSmsViewModel::class.java

    override fun getLayoutId(): Int = R.layout.fragment_fake_sms

}
