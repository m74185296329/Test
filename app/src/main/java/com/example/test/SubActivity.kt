package com.example.test

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class SubActivity : AppCompatActivity() {

    private var nextPageTextView: TextView? = null
    private var goBackButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sub_activity)

        initElements()
        textViewValueSetting()
        setButtonListener()
    }

    /// 初始化設定元件
    private fun initElements() {
        nextPageTextView = findViewById(R.id.nextPageTextView)
        goBackButton = findViewById(R.id.goBackButton)
    }

    /// 設定返回上一頁的動作
    private fun setButtonListener() {

        val goBackButton = goBackButton ?: return

        goBackButton.setOnClickListener {
            returnValueForPreviousPage("我是上一頁的值")
            finish()
        }
    }

    /// 設定
    private fun textViewValueSetting() {
        nextPageTextView!!.text = getValueForPreviousPage()!!
    }

    /// 取得上一頁傳過來的數值
    private fun getValueForPreviousPage(): String? {

        var title: String? = null

        intent?.extras.let {
            title = it?.getString("Winner", "什麼都沒有")
        }

        return title
    }

    /// 回傳數值到上一頁
    private fun returnValueForPreviousPage(subValue: String) {

        val bundle = Bundle()
        val intent = Intent()

        bundle.putString("SubValue", subValue)
        intent.putExtras(bundle)

        setResult(Activity.RESULT_OK, intent)
    }
}