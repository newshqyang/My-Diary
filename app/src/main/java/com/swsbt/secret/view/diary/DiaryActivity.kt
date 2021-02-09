package com.swsbt.secret.view.diary

import android.view.View
import com.swsbt.secret.R
import com.swsbt.secret.databinding.DiaryActBinding
import com.swsbt.secret.view.base.BaseActivity
import com.swsbt.secret.view.diary.viewmodel.DiaryViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class DiaryActivity : BaseActivity<DiaryActBinding>() {

    private val mViewModel: DiaryViewModel by viewModel()

    override fun getLayoutId() = R.layout.diary_act

    override fun initView() {
        setShowBackNav()
        mBinding.apply {
            vm = mViewModel
            presenter = this@DiaryActivity
        }

        intent?.extras?.getInt("id")?.let {
            mViewModel.id = it
        }

        intent?.extras?.getString("title")?.let {
            title = it
        }

        if (mViewModel.id != 0) {
            return
        }
        title = getString(R.string.setting_exchange_title)
    }

    override fun onClick(v: View) {
    }

    override fun loadData(isRefresh: Boolean) {
        if (mViewModel.id == 0) {
            mViewModel.content.set(getString(R.string.setting_exchange_content))
            return
        }
        mViewModel.getDiary()
    }
}