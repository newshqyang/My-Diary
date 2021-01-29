package com.swsbt.secret.view.write.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class WriteViewModel : ViewModel() {

    val content = ObservableField("")
    val searchState = ObservableField(false)    //搜索状态，默认 false “未搜索” ，true “正在搜索”

}