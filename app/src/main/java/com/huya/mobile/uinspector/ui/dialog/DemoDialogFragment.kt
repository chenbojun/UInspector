package com.huya.mobile.uinspector.ui.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.huya.mobile.uinspector.demo.R
import kotlinx.android.synthetic.main.fragment_slideshow.*

/**
 * @author YvesCheung
 * 2020/12/30
 */
class DemoDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_slideshow, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        text_slideshow.text = "I am DialogFragment"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MyDialog(requireContext(), theme)
    }

    class MyDialog(context: Context, theme: Int) : Dialog(context, theme) {

        override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
            return super.dispatchTouchEvent(ev)
        }
    }
}