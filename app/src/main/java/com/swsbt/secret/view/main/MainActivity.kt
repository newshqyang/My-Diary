package com.swsbt.secret.view.main

import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.swsbt.secret.R
import com.swsbt.secret.databinding.MainActivityBinding
import com.swsbt.secret.helper.adapter.BindingViewAdapter
import com.swsbt.secret.helper.adapter.SingleTypeAdapter
import com.swsbt.secret.helper.extens.bindLifeCycle
import com.swsbt.secret.helper.extens.navigateTo
import com.swsbt.secret.view.base.BaseActivity
import com.swsbt.secret.view.main.viewmodel.DiaryItemWrapper
import com.swsbt.secret.view.main.viewmodel.MainViewModel
import com.swsbt.secret.view.write.WriteActivity
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<MainActivityBinding>(), BindingViewAdapter.ItemClickPresenter<DiaryItemWrapper> {

    private val mViewModel: MainViewModel by viewModel()

    /* 适配器 */
    private val mAdapter by lazy {
        SingleTypeAdapter(mContext, R.layout.main_diary_item, mViewModel.data).apply {
            itemPresenter = this@MainActivity
        }
    }

    override fun onClick(v: View) {
    }

    override fun getLayoutId() = R.layout.main_activity

    override fun loadData(isRefresh: Boolean) {
        mViewModel.getData("", "")
            .bindLifeCycle(this)
            .subscribe({
                if (it.isNotEmpty()) {
                    mViewModel.render(it)
                }
            }, {

            })
    }

    override fun onResume() {
        super.onResume()
        loadData(true)
    }

    override fun initView() {
        mBinding.apply {
            rv.apply {
                layoutManager = LinearLayoutManager(mContext)
                adapter = mAdapter
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_write -> navigateTo(WriteActivity::class.java)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(v: View, item: DiaryItemWrapper) {

    }
}