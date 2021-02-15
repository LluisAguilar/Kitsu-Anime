package com.applaudo.android.applaudoscodechallenge.ui.alert

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import com.applaudo.android.applaudoscodechallenge.R
import kotlinx.android.synthetic.main.alert_information.*
import java.text.NumberFormat
import java.util.*

class CustomAlerts(val context: Context?) {

    private var mDialogProgress: Dialog? = null
    private lateinit var mAlertDialog: Dialog

    fun startAlertProgress(title: String?) {
        mDialogProgress = Dialog(context!!, R.style.Theme_Dialog_Translucent)
        mDialogProgress!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mDialogProgress!!.setCancelable(false)
        mDialogProgress!!.setContentView(R.layout.alert_loader)
        val titleTxt = mDialogProgress!!.findViewById<TextView>(R.id.titleProgress)
        titleTxt.text = title
        mDialogProgress!!.show()
    }

    fun stopAlertProgress() {
        if (mDialogProgress != null) {
            mDialogProgress!!.dismiss()
            mDialogProgress = null
        }
    }

    fun alertInformation(body:String) {
        mAlertDialog =
            Dialog(context!!, R.style.Theme_Dialog_Translucent)
        mAlertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mAlertDialog.setCancelable(true)
        mAlertDialog.setContentView(R.layout.alert_information)

        mAlertDialog.body_tv.text = body

        mAlertDialog.alert_icon_close.setOnClickListener {
            mAlertDialog.dismiss()
        }

        mAlertDialog.accept_btn.setOnClickListener { view: View? ->
            mAlertDialog.dismiss()
        }

        mAlertDialog.show()
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(
            Objects.requireNonNull(mAlertDialog.window)?.attributes
        )
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        mAlertDialog.window!!.attributes = layoutParams
    }

}