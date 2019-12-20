package com.oozeetech.sddiam.Widget;

import android.content.Context;
import android.util.AttributeSet;

public class MaterialButton extends com.rey.material.widget.Button {

    public MaterialButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TextViewHelper.setTypeface(context, this, attrs);
    }
};