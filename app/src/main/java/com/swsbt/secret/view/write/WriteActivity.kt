package com.swsbt.secret.view.write

import android.view.View
import com.swsbt.secret.R
import com.swsbt.secret.databinding.WriteActBinding
import com.swsbt.secret.view.base.BaseActivity
import com.swsbt.secret.view.write.viewmodel.WriteViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class WriteActivity : BaseActivity<WriteActBinding>() {

    private val mViewModel: WriteViewModel by viewModel()

    override fun getLayoutId() = R.layout.write_act

    override fun loadData(isRefresh: Boolean) {
    }

    override fun initView() {
    }


    override fun onClick(v: View) {
    }
}