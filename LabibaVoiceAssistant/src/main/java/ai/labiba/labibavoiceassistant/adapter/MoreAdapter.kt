package ai.labiba.labibavoiceassistant.adapter

import ai.labiba.labibavoiceassistant.databinding.ItemMoreBinding
import ai.labiba.labibavoiceassistant.models.MoreModel
import ai.labiba.labibavoiceassistant.sdkSetupClasses.LabibaVAInternal
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load

class MoreAdapter : Adapter<ViewHolder>() {

    inner class MoreViewHolder(private val binding: ItemMoreBinding) : ViewHolder(binding.root) {

        init {
            binding.moreTitleTextView.setTextColor(Color.parseColor(LabibaVAInternal.labibaVaTheme.moreDialog.textColor))
            binding.moreIconImageView.setColorFilter(Color.parseColor(LabibaVAInternal.labibaVaTheme.moreDialog.iconsColor))
        }

        fun bind(item: MoreModel) {
            binding.moreTitleTextView.text = item.title
            binding.moreIconImageView.load(item.icon) {
                crossfade(true)
            }

            itemView.setOnClickListener {
                callback?.onMoreItemClick(item)
            }
        }


    }

    private val diffCallback = object : DiffUtil.ItemCallback<MoreModel>() {
        override fun areItemsTheSame(
            oldItem: MoreModel,
            newItem: MoreModel
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: MoreModel,
            newItem: MoreModel
        ): Boolean {
            return oldItem == newItem
        }

    }

    private val diff = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoreViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return diff.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = diff.currentList[position]
        (holder as MoreViewHolder).bind(item)
    }

    fun submitList(list: List<MoreModel>) {
        diff.submitList(list)
    }

    // --- interface ---
    private var callback: MoreAdapterCallbackInterface? = null

    fun setAdapterInterface(interF: MoreAdapterCallbackInterface) {
        callback = interF
    }

    interface MoreAdapterCallbackInterface {
        fun onMoreItemClick(item: MoreModel)
    }

}