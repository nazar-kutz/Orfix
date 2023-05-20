package com.example.orfix.ui.component.composition.layer.data

import android.graphics.Rect
import android.graphics.drawable.Drawable

class UIButton(_icon: Drawable?, _name: String) {

    var isVisible = false
    var name:String = _name
    var rect: Rect = Rect()
    var iconRect: Rect = Rect()
    var icon: Drawable? = _icon
}