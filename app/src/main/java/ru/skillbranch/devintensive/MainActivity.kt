package ru.skillbranch.devintensive

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.devintensive.extensions.hideKeyboard
import ru.skillbranch.devintensive.models.Bender
import ru.skillbranch.devintensive.models.Bender.*

private const val KEY_STATUS = "STATUS"
private const val KEY_QUESTION = "QUESTION"

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var benderImage: ImageView
    lateinit var textTxt: TextView
    lateinit var messageEt: EditText
    lateinit var sendBtn: ImageView

    lateinit var benderObj: Bender

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBender(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        benderImage = iv_bender
        textTxt = tv_text
        messageEt = et_message
        messageEt.setOnEditorActionListener { view, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                sendBtn.performClick()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        sendBtn = iv_send
        sendBtn.setOnClickListener(this)

        updateState(benderObj.askQuestion(), benderObj.status.color)
    }

    private fun initBender(savedInstanceState: Bundle?) {
        val benderStatus = with(savedInstanceState?.getString(KEY_STATUS) ?: Status.NORMAL.name) {
            Status.valueOf(this)
        }
        val benderQuestion = with(savedInstanceState?.getString(KEY_QUESTION) ?: Question.NAME.name) {
            Question.valueOf(this)
        }

        benderObj = Bender(benderStatus, benderQuestion)
    }

    private fun updateState(phrase: String, color: Triple<Int, Int, Int>) {
        updatePhrase(phrase)
        updateColor(color)
    }

    private fun updatePhrase(phrase: String) {
        textTxt.text = phrase
    }

    private fun updateColor(color: Triple<Int, Int, Int>) {
        val (r, g, b) = color
        benderImage.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        saveBenderObj(outState)
    }

    private fun saveBenderObj(outState: Bundle?) {
        outState?.putString(KEY_STATUS, benderObj.status.name)
        outState?.putString(KEY_QUESTION, benderObj.question.name)
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.iv_send) {
            onMessageSend()
            hideKeyboard()
        }
    }

    private fun onMessageSend() {
        val (phrase, color) = benderObj.listenAnswer(messageEt.text.toString())
        messageEt.setText("")
        updateState(phrase, color)
    }
}
