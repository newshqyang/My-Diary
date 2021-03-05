package com.swsbt.secret.view.write.viewmodel

import android.net.Uri
import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.swsbt.secret.model.local.entity.DiaryEntity
import com.swsbt.secret.model.repository.DiaryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WriteViewModel(val repo: DiaryRepository) : ViewModel() {

    val content = ObservableField("")
    val searchState = ObservableField(false)    //搜索状态，默认 false “未搜索” ，true “正在搜索”
    val picList = ObservableArrayList<PictureItemWrapper>()
    private var diary = DiaryEntity(date = System.currentTimeMillis())
    val pics = HashSet<String>()

    /* 获取diary */
    fun perfectDiary() {
        diary.content = content.get().toString()
        Log.d("WVM", "perfectDiary: ${diary.content}")
        diary.title = getTitle(diary.content)
        diary.picturePaths = getPicPaths()
        Log.d("WVM", "save: ${diary.picturePaths}")
    }

    /* 保存 */
    fun insert() = repo.insert(diary)

    /* 把图片路径集合转为字符串 */
    private fun getPicPaths(): String {
        if (pics.size == 0) {
            return ""
        }
        val sb = StringBuffer()
        for (path in pics) {
            sb.append(path).append('|')
        }
        return sb.deleteCharAt(sb.length - 1).toString()
    }

    /* 改变搜索状态 */
    fun changeSearchState() {
        searchState.set(!(searchState.get()?:false))
    }

    /* 加载图片列表 */
    fun loadPicList() {
        picList.clear()
        picList.addAll(pics.map {
            PictureItemWrapper(it)
        })
    }

    /* 添加图片 */
    fun addPic(uri: String): Boolean {
        if (!pics.add(uri)) {
            return false
        }
        loadPicList()
        return true
    }

    /* 取消图片 */
    fun cancelPic(p: String) {
        pics.remove(p)
        Log.d("WVM", "cancelPic: ${pics.size}")
        loadPicList()
    }




    /*
    获取标题
     */
    private fun getTitle(content: String): String {
        //如果用户没有输入标题，判断输入内容，如果输入内容去除空格和回车后，小于10个字符，就将这几个字符作为标题
        //如果超过10个字符，就截取去除空格和回车后的前10个字符作为标题，同时后面加上省略号。
        val font_number_max = 10
        var title = content.trim { it <= ' ' } //获取content内容
        val end = title.indexOf("\n") //获取掐头去尾后第一个换行符坐标
        title = if (end == -1) {
            if (title.length <= font_number_max) {
                return title
            } else {
                title.substring(0, font_number_max) + "..."
            }
        } else {
            if (title.substring(0, end).length <= font_number_max) {
                return title.substring(0, end) //截取第一个换行符前的内容
            } else {
                title.substring(0, font_number_max) + "..."
            }
        }
        return title
    }
}