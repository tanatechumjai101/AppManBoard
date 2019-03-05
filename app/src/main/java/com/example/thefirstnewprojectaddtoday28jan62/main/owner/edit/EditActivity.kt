package com.example.thefirstnewprojectaddtoday28jan62.main.owner.edit

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.webkit.WebChromeClient
import com.example.thefirstnewprojectaddtoday28jan62.R
import com.example.thefirstnewprojectaddtoday28jan62.model.Data
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.toolbar_form_activity.*


class EditActivity : AppCompatActivity() {
    private var data: Data? = null
    private lateinit var mActivity: Activity
    private lateinit var nPreview: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        if (intent.extras != null) {
            data = intent?.extras?.getParcelable("data")
            setDataNow(data!!)
        }
        webview_edit.setEditorHeight(200)
        webview_edit.setEditorFontSize(22)
        webview_edit.setEditorFontColor(Color.BLACK)
        webview_edit.setPadding(20, 10, 20, 10)
        webview_edit.isEnabled = true

        webview_edit.setOnTextChangeListener { text ->
            var mPreview = text.toString()
            nPreview = mPreview
        }


        ib_back_pageForm.setOnClickListener {
            finish()
        }

        ib_done_pageForm.setOnClickListener {

            data = intent?.extras?.getParcelable("data")
            data!!.subject = ed_subject_edit.text.toString()

            val body = "<!DOCTYPE html><html><body>"
            val content = body + data!!.detail + "</body></html>"
            webview_edit.webChromeClient = WebChromeClient()
            data!!.detail = ""+webview_edit.loadDataWithBaseURL(null, content, "text/html; charset=utf-8", "UTF-8", "about:blank")


            intent.putExtra("new_data", data)
            setResult(Activity.RESULT_OK, intent)
            closeKeyboard()
            finish()

        }

        action_undo_edit.setOnClickListener {
            webview_edit.undo()
        }

        action_redo_edit.setOnClickListener {
            webview_edit.redo()
        }

        action_bold_edit.setOnClickListener {
            webview_edit.setBold()
        }

        action_italic_edit.setOnClickListener {
            webview_edit.setItalic()
        }

        action_subscript_edit.setOnClickListener {
            webview_edit.setSubscript()
        }

        action_superscript_edit.setOnClickListener {
            webview_edit.setSuperscript()
        }

        action_strikethrough_edit.setOnClickListener {
            webview_edit.setStrikeThrough()
        }

        action_underline_edit.setOnClickListener {
            webview_edit.setUnderline()
        }

        action_heading1_edit.setOnClickListener {
            webview_edit.setHeading(1)
        }

        action_heading2_edit.setOnClickListener {
            webview_edit.setHeading(2)
        }

        action_heading3_edit.setOnClickListener {
            webview_edit.setHeading(3)
        }

        action_heading4_edit.setOnClickListener {
            webview_edit.setHeading(4)
        }


        action_heading5_edit.setOnClickListener {
            webview_edit.setHeading(5)
        }

        action_heading6_edit.setOnClickListener {
            webview_edit.setHeading(6)
        }

        action_txt_color_edit.setOnClickListener(object : View.OnClickListener {
            private var isChanged: Boolean = false

            override fun onClick(v: View) {
                webview_edit.setTextColor(
                    if (isChanged) {
                        Color.BLACK
                    } else {
                        Color.RED
                    }
                )
                isChanged = !isChanged
            }
        })

        action_bg_color_edit.setOnClickListener(object : View.OnClickListener {
            private var isChanged: Boolean = false

            override fun onClick(v: View) {
                webview_edit.setTextBackgroundColor(
                    if (isChanged) {
                        Color.TRANSPARENT
                    } else {
                        Color.YELLOW
                    }
                )
                isChanged = !isChanged
            }
        })

        action_indent_edit.setOnClickListener {
            webview_edit.setIndent()
        }

        action_outdent_edit.setOnClickListener {
            webview_edit.setOutdent()
        }

        action_align_left_edit.setOnClickListener {
            webview_edit.setAlignLeft()
        }

        action_align_center_edit.setOnClickListener {
            webview_edit.setAlignCenter()
        }

        action_align_right_edit.setOnClickListener {
            webview_edit.setAlignRight()
        }

        action_blockquote_edit.setOnClickListener {
            webview_edit.setBlockquote()
        }

        action_insert_bullets_edit.setOnClickListener {
            webview_edit.setBullets()
        }

        action_insert_numbers_edit.setOnClickListener {
            webview_edit.setNumbers()
        }



        action_insert_link_edit.setOnClickListener {
            webview_edit.insertLink(
                "https://github.com/wasabeef",
                "career@appman.co.th"
            )
        }

        action_insert_checkbox_edit.setOnClickListener {
            webview_edit.insertTodo()
        }
    }

    private fun setDataNow(data: Data?) {
        ed_subject_edit.setText("${data?.subject}")
        val body = "<!DOCTYPE html><html><body>"
        val content = body + data!!.detail + "</body></html>"
        webview_edit.loadDataWithBaseURL(null, content, "text/html; charset=utf-8", "UTF-8", "about:blank")

    }


    override fun onBackPressed() {
//        super.onBackPressed()
        AlertDialog.Builder(this@EditActivity)
            .setTitle("Are you sure ?")
            .setMessage("Do you want to close this page?")
            .setPositiveButton("yes") { dialog, which -> finish() }
            .setNegativeButton("no") { dialog, which -> }
            .show()
    }

    private fun closeKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}
