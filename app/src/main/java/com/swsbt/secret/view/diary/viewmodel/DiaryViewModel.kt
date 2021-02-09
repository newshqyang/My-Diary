package com.swsbt.secret.view.diary.viewmodel

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.swsbt.secret.helper.utils.DateUtil
import com.swsbt.secret.model.repository.DiaryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DiaryViewModel(val repo: DiaryRepository) : ViewModel() {

    val content = ObservableField("")
    val date = ObservableField("")
    val picPaths = ObservableArrayList<String>()
    var id = 0

    fun getDiary() = CoroutineScope(Dispatchers.Main).launch {
        val r = repo.get(id)
        content.set(r.content)
        date.set(DateUtil.str(r.date))
    }


}