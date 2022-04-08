package lostankit7.android.contactlist.viewholder

import android.view.View
import androidx.annotation.LayoutRes
import lostankit7.android.contactlist.R
import lostankit7.android.contactlist.model.ErrorState
import lostankit7.android.contactlist.base.AbstractViewHolder

class ErrorStateViewHolder(view: View) : AbstractViewHolder<ErrorState>(view) {

    companion object {
        @LayoutRes val LAYOUT = R.layout.item_rv_error_state
    }

    override fun bind(element: ErrorState) {

    }
}