package com.navanvine.fakesms.screen.main

import com.navanvine.fakesms.R
import com.navanvine.fakesms.base.BaseFragment
import com.navanvine.fakesms.databinding.FragmentFakeSmsBinding

class FakeSmsFragment : BaseFragment<FragmentFakeSmsBinding, FakeSmsViewModel>() {
    override val viewModelType: Class<FakeSmsViewModel>
        get() = FakeSmsViewModel::class.java
    override val layoutId: Int
        get() = R.layout.fragment_fake_sms

}
