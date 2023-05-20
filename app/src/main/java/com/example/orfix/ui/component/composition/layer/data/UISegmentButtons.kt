package com.example.orfix.ui.component.composition.layer.data

import android.graphics.Rect

class UISegmentButtons(_iconSize: Int) {
    var rect: Rect = Rect()
    var buttons = ArrayList<UIButton>()
    var buttonSize = _iconSize
    val SPACE_BETWEEN_BUTTONS = 30


    fun setButtonsRect(buttonsRectCenterX: Int, buttonsRectCenterY: Int){
        if (buttons.isNotEmpty()){
            rect.left = buttonsRectCenterX - ((buttons.size * buttonSize) / 2) - ((SPACE_BETWEEN_BUTTONS * buttons.size - 1) / 4)
            rect.right = buttonsRectCenterX + ((buttons.size * buttonSize) / 2) + ((SPACE_BETWEEN_BUTTONS * buttons.size - 1) / 4)
            rect.top = buttonsRectCenterY - (buttonSize / 2)
            rect.bottom = buttonsRectCenterY + (buttonSize / 2)

            var leftSidePointer = rect.left
            for(button in buttons){
                button.rect.left = leftSidePointer
                button.rect.top = rect.top
                button.rect.right = leftSidePointer + buttonSize
                button.rect.bottom = rect.bottom

//                leftSidePointer += rect.width() / buttons.size
                leftSidePointer += (buttonSize + SPACE_BETWEEN_BUTTONS)
            }
        }
    }
}