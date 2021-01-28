package com.swsbt.secret.view.main.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModel
import com.swsbt.secret.helper.extens.async
import com.swsbt.secret.model.local.entity.DiaryEntity
import com.swsbt.secret.model.repository.DiaryRepository

class MainViewModel(val repo: DiaryRepository) : ViewModel() {

    val data = ObservableArrayList<DiaryItemWrapper>()
    var page = 0
    var pageSize = 10

    fun getData(title: String, content: String) = repo.getData(title, content, page, pageSize)
        .async()

    /* 渲染UI */
    fun render(list: List<DiaryEntity>) {
        data.clear()
        data.addAll(list.map { DiaryItemWrapper(it) })
    }
}