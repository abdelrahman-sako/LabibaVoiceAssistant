package ai.labiba.labibavoiceassistant.adapter.chatAdapter

import ai.labiba.labibavoiceassistant.R
import ai.labiba.labibavoiceassistant.adapter.chatAdapter.holders.BotAudioViewHolder
import ai.labiba.labibavoiceassistant.adapter.chatAdapter.holders.BotImageViewHolder
import ai.labiba.labibavoiceassistant.adapter.chatAdapter.holders.BotTextViewHolder
import ai.labiba.labibavoiceassistant.adapter.chatAdapter.holders.BotVideoViewHolder
import ai.labiba.labibavoiceassistant.adapter.chatAdapter.holders.CustomViewViewHolder
import ai.labiba.labibavoiceassistant.adapter.chatAdapter.holders.TypingViewHolder
import ai.labiba.labibavoiceassistant.adapter.chatAdapter.holders.UserImageViewHolder
import ai.labiba.labibavoiceassistant.adapter.chatAdapter.holders.UserTextViewHolder
import ai.labiba.labibavoiceassistant.enums.ChatType
import ai.labiba.labibavoiceassistant.models.Card
import ai.labiba.labibavoiceassistant.models.Chat
import ai.labiba.labibavoiceassistant.models.Choice
import ai.labiba.labibavoiceassistant.utils.Tools
import ai.labiba.labibavoiceassistant.utils.Views
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.util.UUID

class ChatAdapter(private val listUpdatedListener: (() -> Unit)? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Chat>() {
        override fun areItemsTheSame(
            oldItem: Chat,
            newItem: Chat
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Chat,
            newItem: Chat
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val diff = AsyncListDiffer(this, diffCallback)

    override fun getItemViewType(position: Int) =
        when (diff.currentList[position].type) {
            ChatType.TYPING -> R.layout.item_typing
            ChatType.USER_TEXT -> R.layout.item_text_bubble_user
            ChatType.USER_IMAGE -> R.layout.item_image_user
            ChatType.BOT_TEXT -> R.layout.item_text_bubble_bot
            ChatType.CHOICES -> R.layout.item_recycler_view
            ChatType.CARDS -> R.layout.item_recycler_view
            ChatType.MEDIA_IMAGE -> R.layout.item_image_bot
            ChatType.MEDIA_VIDEO -> R.layout.item_video
            ChatType.MEDIA_AUDIO -> R.layout.item_audio
            ChatType.CUSTOM -> R.layout.item_va_custom_view
            ChatType.NON -> 0
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)

        return when (viewType) {
            R.layout.item_typing -> TypingViewHolder(
                view
            )

            R.layout.item_text_bubble_user -> UserTextViewHolder(
                view
            )

            R.layout.item_text_bubble_bot -> BotTextViewHolder(
                view
            )

            R.layout.item_image_user -> UserImageViewHolder(
                view
            )

            R.layout.item_image_bot -> BotImageViewHolder(
                view
            )

            R.layout.item_video -> BotVideoViewHolder(
                view
            )
            R.layout.item_va_custom_view -> {
                CustomViewViewHolder(
                    view
                )
            }
            else -> {
                BotAudioViewHolder(
                    view
                )
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = diff.currentList[position]
        when (holder) {
            is TypingViewHolder -> {
                holder.onBind()
            }

            is UserTextViewHolder -> {
                holder.onBind(item.text ?: "")
                Views.animateView(holder.itemView, R.anim.layout_animation_fall_down)
            }

            is BotTextViewHolder -> {

                if (position == 0 && diff.currentList.size > 1) { //scale down if user text exists
                    Views.animateView(holder.itemView, R.anim.layout_animation_scale_down)
                    holder.onBind(item.text ?: "", true)

                } else {  //default animation
                    Views.animateView(holder.itemView, R.anim.layout_animation_fall_down)
                    holder.onBind(item.text ?: "", false)

                }

            }

            is UserImageViewHolder -> {
                if (item.mediaUrl.isNullOrEmpty() && item.image != null) {
                    holder.onBind(item.image!!)
                } else {
                    holder.onBind(item.mediaUrl ?: "")
                }
            }

            is BotImageViewHolder -> {
                holder.onBind(item.mediaUrl ?: "")
            }

            is BotVideoViewHolder -> {
                holder.onBind(item.mediaUrl ?: "")
            }

            is BotAudioViewHolder -> {
                holder.onBind(item.mediaUrl ?: "")
            }
            is CustomViewViewHolder -> {
                holder.bind(item)
                Views.animateView(holder.itemView, R.anim.layout_animation_fall_down)
            }
        }
    }

    override fun getItemCount() = diff.currentList.size

    //*************
    // Set Full list

    fun submitList(list: List<Chat>, onListSubmitted: (() -> Unit)? = null) {

        //filter deeplink items
        val filteredList = Tools.filterDeepLinkItemsAndCallback(list).toMutableList()

        //todo remove this when choices are handled
        if (filteredList.size == 1 && filteredList.first().type == ChatType.CHOICES) {
            filteredList.clear()
        }

        if (filteredList.isNotEmpty()) {
            diff.submitList(filteredList) {
                listUpdatedListener?.invoke()
                onListSubmitted?.invoke()
            }
        }

    }

    fun clearAllData() {
        diff.submitList(null)
    }

    fun addCustomView(view: View) {
        var list = diff.currentList.toMutableList()

        list = list.filter { it.type != ChatType.TYPING }.toMutableList()

        list.add(
            Chat().setData(
                chatType = ChatType.CUSTOM,
                textMessage = UUID.randomUUID().toString(),
                customView = view
            )
        )
        diff.submitList(list) {
            listUpdatedListener?.invoke()
        }

    }

    fun addList(list: List<Chat>) {
        cleanList()
        val fullList = ArrayList(diff.currentList)
        fullList.addAll(list)
        diff.submitList(fullList)
    }

    //****************
    // Adding items

    fun addTyping() {
        diff.submitList(ArrayList(diff.currentList).also {
            it.add(Chat().setData(chatType = ChatType.TYPING))
        }) {
            listUpdatedListener?.invoke()
        }
    }

    fun addUserText(message: String, onUserTextAdded: (() -> Unit)? = null) {
        val temp = diff.currentList.toMutableList()
        temp.add(Chat().setData(chatType = ChatType.USER_TEXT, textMessage = message))

        diff.submitList(
            temp
        ) {
            listUpdatedListener?.invoke()
            onUserTextAdded?.invoke()

            //apply scale down to bot text
            if (temp.size > 1) {
                temp[0].scaleDown = true
                notifyItemChanged(0)
            }
        }

//        diff.submitList(ArrayList(diff.currentList).also {
//            it.add(Chat().setData(chatType = ChatType.USER_TEXT, textMessage = message))
//        }){
//            listUpdatedListener.invoke()
//        }
    }

    fun addOrModifyUserText(message: String, onUserTextAdded: (() -> Unit)? = null) {
        val temp = diff.currentList.toMutableList()
        val item = temp.find { it.type == ChatType.USER_TEXT }

        if (item != null) {
            item.text = message
        } else {
            temp.add(Chat().setData(chatType = ChatType.USER_TEXT, textMessage = message))
        }




        diff.submitList(
            temp
        ) {
            notifyItemChanged(diff.currentList.size - 1)
            onUserTextAdded?.invoke()
            listUpdatedListener?.invoke()
        }
    }

    fun addBotText(message: String) {
        val list = ArrayList(diff.currentList)
        list.add(Chat().setData(chatType = ChatType.BOT_TEXT, textMessage = message))
        diff.submitList(list) {
            listUpdatedListener?.invoke()
        }
    }

    fun addChoices(choicesList: List<Choice>) {
        val list = ArrayList(diff.currentList)
        list.add(Chat().setData(chatType = ChatType.CHOICES, choices = choicesList))
        diff.submitList(list) {
            listUpdatedListener?.invoke()
        }
    }


    fun addCards(cardsList: List<Card>) {
        val list = ArrayList(diff.currentList)
        list.add(Chat().setData(chatType = ChatType.CARDS, cards = cardsList))

        //filter deeplink items
        val filteredList = Tools.filterDeepLinkItemsAndCallback(list)

        if (filteredList.isNotEmpty()) {
            diff.submitList(filteredList) {
                listUpdatedListener?.invoke()
            }
        }
    }

    fun addUserImage(url: String) {
        val list = ArrayList(diff.currentList)
        list.add(Chat().setData(chatType = ChatType.USER_IMAGE, mediaUrl = url))
        diff.submitList(list) {
            listUpdatedListener?.invoke()
        }
    }

    fun addUserLocation(image: Bitmap) {
        val list = ArrayList(diff.currentList)
        list.add(Chat().setData(chatType = ChatType.USER_IMAGE, image = image))
        diff.submitList(list) {
            listUpdatedListener?.invoke()
        }
    }

    fun addBotImage(url: String) {
        val list = ArrayList(diff.currentList)
        list.add(Chat().setData(chatType = ChatType.MEDIA_IMAGE, mediaUrl = url))
        diff.submitList(list) {
            listUpdatedListener?.invoke()
        }
    }

    fun addVideo(url: String) {
        val list = ArrayList(diff.currentList)
        list.add(Chat().setData(chatType = ChatType.MEDIA_VIDEO, mediaUrl = url))
        diff.submitList(list) {
            listUpdatedListener?.invoke()
        }
    }

    fun addAudio(url: String) {
        val list = ArrayList(diff.currentList)
        list.add(Chat().setData(chatType = ChatType.MEDIA_AUDIO, mediaUrl = url))
        diff.submitList(list) {
            listUpdatedListener?.invoke()
        }
    }

    //***************
    //Removing items

    fun removeType(type: Array<ChatType>, onSubmitList: (() -> Unit)? = null) {
        if (diff.currentList.size > 0) {
            val list: MutableList<Chat> = mutableListOf()

            diff.currentList.forEachIndexed { index, item ->
                if (!type.contains(item.type)) {
                    list.add(item)
                }
            }



            diff.submitList(list) {
                listUpdatedListener?.invoke()
                onSubmitList?.invoke()
            }
        }
    }

    fun cleanList(finishListener: (() -> Unit)? = null) {
        if (diff.currentList.size > 0) {
            val list: MutableList<Chat> = mutableListOf()

            diff.currentList.forEachIndexed { index, item ->
                if (item.type != ChatType.TYPING && item.type != ChatType.CHOICES) {
                    list.add(item)
                }
            }

            diff.submitList(list) {
                finishListener?.invoke()
                listUpdatedListener?.invoke()
            }
        }
    }


    fun removeItem(item: Chat) {
        diff.submitList(ArrayList(diff.currentList).also {
            it.remove(item)
        }) {
            listUpdatedListener?.invoke()
        }
    }


}