package com.huya.mobile.uinspector.impl.properties.layoutParam

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.PARENT_ID
import com.huya.mobile.uinspector.impl.utils.dpStr
import com.huya.mobile.uinspector.impl.utils.idToString

/**
 * @author YvesCheung
 * 2021/1/4
 */
open class ConstraintLayoutParamsPropertiesParser(val context: Context, lp: LayoutParams) :
    LayoutParamsPropertiesParser<LayoutParams>(lp) {

    override fun parse(props: MutableMap<String, Any?>) {
        super.parse(props)

        //todo: resolved start/end to left/right
        checkConstraint("leftToLeft", lp.leftToLeft, props)
        checkConstraint("leftToRight", lp.leftToRight, props)
        checkConstraint("startToStart", lp.startToStart, props)
        checkConstraint("startToEnd", lp.startToEnd, props)

        checkConstraint("rightToLeft", lp.rightToLeft, props)
        checkConstraint("rightToRight", lp.rightToRight, props)
        checkConstraint("endToEnd", lp.endToEnd, props)
        checkConstraint("endToStart", lp.endToStart, props)

        checkConstraint("topToBottom", lp.topToBottom, props)
        checkConstraint("topToTop", lp.topToTop, props)

        checkConstraint("bottomToTop", lp.bottomToTop, props)
        checkConstraint("bottomToBottom", lp.bottomToBottom, props)

        checkConstraint("baselineToBaseline", lp.baselineToBaseline, props)

        checkConstraint("circleConstraint", lp.circleConstraint, props)
        if (lp.circleRadius != 0) {
            props["circleRadius"] = lp.circleRadius
        }
        if (lp.circleAngle != 0f) {
            props["circleAngle"] = lp.circleAngle
        }

        if (lp.horizontalBias != 0.5f) {
            props["horizontalBias"] = lp.horizontalBias
        }
        if (lp.verticalBias != 0.5f) {
            props["verticalBias"] = lp.verticalBias
        }

        if (lp.horizontalWeight != -1f) {
            props["horizontalWeight"] = lp.horizontalWeight
        }
        if (lp.verticalWeight != -1f) {
            props["verticalWeight"] = lp.verticalWeight
        }

        checkGoneMargin("goneBottomMargin", lp.goneBottomMargin, props)
        checkGoneMargin("goneTopMargin", lp.goneTopMargin, props)
        checkGoneMargin("goneStartMargin", lp.goneStartMargin, props)
        checkGoneMargin("goneLeftMargin", lp.goneLeftMargin, props)
        checkGoneMargin("goneEndMargin", lp.goneEndMargin, props)
        checkGoneMargin("goneRightMargin", lp.goneRightMargin, props)
    }

    private fun checkConstraint(name: String, constraint: Int, props: MutableMap<String, Any?>) {
        if (constraint != -1) {
            props[name] =
                if (constraint == PARENT_ID) "parent"
                else idToString(context, constraint)
        }
    }

    private fun checkGoneMargin(name: String, margin: Int, props: MutableMap<String, Any?>) {
        if (margin > 0) {
            props[name] = margin.dpStr
        }
    }
}