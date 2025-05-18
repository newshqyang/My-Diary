package com.swsbt.secret.view.write

import android.content.Intent
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.swsbt.secret.R
import com.swsbt.secret.databinding.WriteActBinding
import com.swsbt.secret.helper.adapter.BindingViewAdapter
import com.swsbt.secret.helper.adapter.SingleTypeAdapter
import com.swsbt.secret.helper.utils.ImageUtil
import com.swsbt.secret.helper.utils.IsEmpty
import com.swsbt.secret.helper.utils.ToastUtil
import com.swsbt.secret.view.base.BaseActivity
import com.swsbt.secret.view.write.viewmodel.PictureItemWrapper
import com.swsbt.secret.view.write.viewmodel.WriteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class WriteActivity : BaseActivity<WriteActBinding>(), BindingViewAdapter.ItemClickPresenter<PictureItemWrapper> {

    private val mViewModel: WriteViewModel by viewModel()

    private val mAdapter by lazy {
        SingleTypeAdapter(this, R.layout.write_pic_item, mViewModel.picList).apply {
            itemPresenter = this@WriteActivity
        }
    }

    override fun getLayoutId() = R.layout.write_act

    override fun loadData(isRefresh: Boolean) {
    }

    override fun initView() {
        mBinding.apply {
            vm = mViewModel
            presenter = this@WriteActivity
            rvPic.apply {
                layoutManager = LinearLayoutManager(
                    this@WriteActivity,
                    RecyclerView.HORIZONTAL,
                    false
                )
                adapter = mAdapter
            }
        }
    }

    override fun initEvent() {
        mBinding.contentEdit.addTextChangedListener {
            if (IsEmpty.check(it)) {
                mBinding.saveTv.isEnabled = false
                mBinding.saveTv.background = getDrawable(R.drawable.button_all_right_background_disabled)
            } else {
                mBinding.saveTv.isEnabled = true
                mBinding.saveTv.background = getDrawable(R.drawable.button_all_right_background)
            }
        }
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.search_iv -> mViewModel.changeSearchState()
            R.id.pic_iv -> ImageUtil.selectPic(this)
            R.id.save_tv -> save()
        }
    }

    /* 保存 */
    private fun save() {
        CoroutineScope(Dispatchers.Main).launch {
            mViewModel.perfectDiary()
            mViewModel.insert()
            ToastUtil.bottom(this@WriteActivity, "已保存")
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK && requestCode == ImageUtil.SELECT_PICTURE) {
            val uri = data?.data
            if (data == null || uri == null) {
                super.onActivityResult(requestCode, resultCode, data)
                ToastUtil.text(this, "无法获取图片")
                return
            }
            mViewModel.addPic(uri.toString())
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onItemClick(v: View, item: PictureItemWrapper) {
        when (v.id) {
            R.id.cancel_ib -> mViewModel.cancelPic(item.p)
        }
    }

}