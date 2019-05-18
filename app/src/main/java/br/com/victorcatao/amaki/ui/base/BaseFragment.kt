package br.com.victorcatao.amaki.ui.base

import android.support.v4.app.Fragment
import br.com.victorcatao.amaki.utils.extensions.hideKeyboard
import br.com.victorcatao.amaki.utils.extensions.showKeyboard
import br.com.victorcatao.amaki.utils.extensions.showToast

abstract class BaseFragment : Fragment() {

    //TOAST METHODS
    fun showToast(string: String) {
        activity?.showToast(string)
    }

    //KEYBOARD METHODS
    fun hideSoftKeyboard() {
        activity?.hideKeyboard()
    }

    fun showSoftKeyBoard() {
        activity?.showKeyboard()
    }
}
