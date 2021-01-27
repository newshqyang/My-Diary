package com.swsbt.secret.view.main

import android.view.Menu
import android.view.View
import com.swsbt.secret.R
import com.swsbt.secret.databinding.MainActivityBinding
import com.swsbt.secret.view.base.BaseActivity
import com.swsbt.secret.view.main.viewmodel.MainViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<MainActivityBinding>() {

    private val mViewModel: MainViewModel by viewModel()

    override fun onClick(v: View?) {
    }

    override fun getLayoutId() = R.layout.main_activity

    override fun loadDate(isRefresh: Boolean) {
    }

    override fun initView() {
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}