package br.com.victorcatao.amaki.utils

import android.content.Context
import android.support.v7.widget.LinearLayoutManager

class CustomLinearLayoutManager(context: Context) : LinearLayoutManager(context) {
    override fun supportsPredictiveItemAnimations(): Boolean {
        return false
    }
}

