package com.swsbt.secret.view.main.viewmodel

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.swsbt.secret.helper.utils.DateUtil
import com.swsbt.secret.model.local.entity.DiaryEntity
import com.swsbt.secret.model.repository.DiaryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(val repo: DiaryRepository) : ViewModel() {

    private val TAG = javaClass.simpleName

    val data = ObservableArrayList<DiaryItemWrapper>()
    val key = ObservableField("")
    val content = ObservableField("")
    var page = 0
    var pageSize = 10

    fun snapshotData() = CoroutineScope(Dispatchers.Main).launch {
        val r = repo.snapshotData(key.get().toString(), page, pageSize)
        render(r)
    }


    /* 渲染UI */
    fun render(list: List<DiaryEntity>) {
        data.clear()
        var m = DateUtil.m()
        data.addAll(list.map {
            val itm = DateUtil.m(it.date)
            if (m != itm) {
                m = itm
                DiaryItemWrapper(it, true)
            } else {
                DiaryItemWrapper(it)
            }
        })
    }
}