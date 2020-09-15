package com.example.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*





class MainActivity : AppCompatActivity() {
    private var inputName: EditText? = null
    private var radioGroup: RadioGroup? = null
    private var startGame: Button? = null
    private var nextPage: Button? = null

    private var labelName: TextView? = null
    private var labelMe: TextView? = null
    private var labelWinner: TextView? = null
    private var labelPC: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initElements()
        setButtonListener()

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        getReturnValueForNextPage(requestCode, resultCode, data)
    }

    private fun initElements() {
        inputName = findViewById(R.id.input_name)
        radioGroup = findViewById(R.id.radioGroup)
        startGame = findViewById(R.id.start_game)
        nextPage = findViewById(R.id.nextPage)

        labelName = findViewById<TextView>(R.id.label_name)
        labelMe = findViewById<TextView>(R.id.label_me)
        labelWinner = findViewById<TextView>(R.id.label_winner)
        labelPC = findViewById<TextView>(R.id.label_pc)
    }
    private val moraResultsMap: Map<Int, String>
        get() { return initMoraResultsMap() }
    private fun initMoraResultsMap(): Map<Int, String> {
        return mapOf(0 to getString(R.string.select_scissors), 1 to getString(R.string.select_rock), 2 to getString(R.string.select_paper))
    }

    private fun setButtonListener() {

        val startGame = startGame ?: return
        val nextPage = nextPage ?: return

        startGame.setOnClickListener { moraResult() }
        nextPage.setOnClickListener { nextPage() }
    }
    private fun moraResult() {

        val inputName = inputName ?: return
        val labelName = labelName ?: return
        val labelMe = labelMe ?: return
        val labelWinner = labelWinner ?: return
        val labelPC = labelPC ?: return

        if (inputName.length() < 1) {
            Toast.makeText(this, "請輸入姓名", Toast.LENGTH_LONG).show(); return
        }
        val resultForMe = moraResultForMe()
        val resultForPC = moraResultForPC()
        val moraResult = moraWinner(resultForMe, resultForPC)

        labelName.text = String.format("%s\n%s", getString(R.string.label_name), inputName.text)
        labelMe.text = "${getString(R.string.label_me)}\n${moraResultsMap[resultForMe]}"
        labelPC.text = "${getString(R.string.label_pc)}\n${moraResultsMap[resultForPC]}"
        labelWinner.text = "${getString(R.string.label_winner)}\n${moraWinner(moraResult)}"
    }
    private fun moraWinner(result: Boolean?): String {
        val isWin = result ?: return "平手"
        if (isWin) { return "玩家贏" }
        return "電腦贏"
    }
    private fun moraWinner(meResult: Int, pcResult: Int): Boolean? {

        var result: Boolean? = null

        if (meResult == 0) {
            when (pcResult) {
                1 -> result = false
                2 -> result = true
            }

            return result
        }

        if (meResult == 1) {
            when (pcResult) {
                0 -> result = true
                2 -> result = false
            }

            return result
        }

        if (meResult == 2) {
            when (pcResult) {
                0 -> result = false
                1 -> result = true
            }

            return result
        }

        return result
    }
    private fun moraResultForMe(): Int {

        val selectScissors = findViewById<RadioButton>(R.id.select_scissors)
        val selectRock = findViewById<RadioButton>(R.id.select_rock)
        val selectPaper = findViewById<RadioButton>(R.id.select_paper)

        var answer = 0

        when {
            selectScissors.isChecked -> answer = 0
            selectRock.isChecked -> answer = 1
            selectPaper.isChecked -> answer = 2
        }

        return answer
    }

    /// 電腦出拳的結果 (random 0 ~ 2)
    private fun moraResultForPC(): Int {
        return (Math.random() * 2).toInt()
    }

    /// 跳到下一頁 (帶值)
    private fun nextPage() {

        val bundle = bundleMaker()
        val intent = Intent(this, SubActivity::class.java)

        bundle.putString("Winner", labelWinner?.text.toString())
        bundle.putString("UserName", inputName?.text.toString())
        intent.putExtras(bundle)

        startActivityForResult(intent, 1)
    }

    /// 產生要帶去下一頁的值
    private fun bundleMaker(): Bundle {

        val bundle = Bundle()

        bundle.putString("Winner", labelWinner?.text.toString())
        bundle.putString("UserName", inputName?.text.toString())

        return bundle
    }

    private fun getReturnValueForNextPage(requestCode: Int, resultCode: Int, data: Intent?) {

        data?.extras.let {
            val subValue = it?.getString("SubValue")
            Toast.makeText(this, subValue, Toast.LENGTH_LONG).show()
        }
    }
}