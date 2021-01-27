package com.swsbt.secret

import com.swsbt.secret.model.local.AppDatabase
import com.swsbt.secret.model.repository.DiaryRepository
import com.swsbt.secret.view.main.viewmodel.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    factory { DiaryRepository(AppDatabase.INSTANCE.diaryDao()) }

    viewModel {
        MainViewModel(get())
    }
}