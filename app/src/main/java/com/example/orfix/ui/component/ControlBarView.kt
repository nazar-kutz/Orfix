package com.example.orfix.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.*
import androidx.core.content.ContextCompat
import com.example.orfix.R


class ControlBarView(
    context: Context,
    attrs: AttributeSet
) : LinearLayout(context, attrs) {

    private val generateBtn: Button

    var isTrackChanged: Boolean

    init {
        LayoutInflater.from(context).inflate(R.layout.component_control_bar, this, true)
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ControlBarView,
            0, 0
        ).apply {
            try {
                isTrackChanged = getBoolean(R.styleable.ControlBarView_isTrackChanged, true)
                generateBtn = findViewById(R.id.button_generate)
                updateView()
            } finally {
                recycle()
            }
        }
    }

    private fun updateView() {
        generateBtn.backgroundTintList = if (isTrackChanged) ContextCompat.getColorStateList(context, R.color.white_main) else ContextCompat.getColorStateList(context, R.color.white_main_inactive)
    }

}