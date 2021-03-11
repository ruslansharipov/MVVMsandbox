package ru.surfstudio.android.mvvmsandbox.pagination

import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import ru.surfstudio.android.easyadapter.pagination.EasyPaginationAdapter
import ru.surfstudio.android.easyadapter.pagination.PaginationState
import ru.surfstudio.android.mvvmsandbox.R

/**
 * @param loaderSkeletonRes скелетон для лоадера пагинации
 */
class PaginationFooterItemController(
    @LayoutRes val loaderSkeletonRes: Int
) : EasyPaginationAdapter.BasePaginationFooterController<PaginationFooterItemController.Holder>() {

    override fun createViewHolder(
        parent: ViewGroup,
        listener: EasyPaginationAdapter.OnShowMoreListener
    ): Holder {
        return Holder(parent, listener)
    }

    // fixme https://jira.surfstudio.ru/browse/ANDDEP-1114
    override fun getState(): PaginationState {
        return PaginationState.READY
    }

    inner class Holder(
        parent: ViewGroup,
        listener: EasyPaginationAdapter.OnShowMoreListener
    ) : EasyPaginationAdapter.BasePaginationFooterHolder(parent, R.layout.list_item_pagination_footer) {

        private val loaderPb: ProgressBar = itemView.findViewById(R.id.pagination_footer_pb)
        private val retryBtn: Button = itemView.findViewById(R.id.pagination_footer_retry_btn)

        init {
            retryBtn.setOnClickListener { listener.onShowMore() }
        }

        override fun bind(state: PaginationState) {
            val isLoading = state == PaginationState.READY
            val isError = state == PaginationState.ERROR

            loaderPb.isVisible = isLoading
            retryBtn.isVisible = isError
        }
    }
}
