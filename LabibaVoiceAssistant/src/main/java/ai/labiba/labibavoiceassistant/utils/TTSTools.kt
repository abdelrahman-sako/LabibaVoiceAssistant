package ai.labiba.labibavoiceassistant.utils

import android.media.MediaDataSource
import android.media.MediaPlayer
import android.util.Base64
import android.webkit.URLUtil
import java.util.LinkedList
import java.util.Queue

object TTSTools {
    private val audioQueue: Queue<MediaPlayer> = LinkedList<MediaPlayer>()
    private var onAudioCompleteListener: (() -> Unit)? = null


    fun playAudio(url: String, onAudioCompleteListener: (() -> Unit)? = null) {
        this.onAudioCompleteListener = onAudioCompleteListener


        val mediaPlayer = MediaPlayer()

        //add audio to queue
        audioQueue.offer(mediaPlayer)
        setDataSource(mediaPlayer, url)

        setMediaPlayerListeners(mediaPlayer)

    }


    private fun setDataSource(mediaPlayer: MediaPlayer, url: String) {
        try {
            if (URLUtil.isValidUrl(url)) {
                mediaPlayer.setDataSource(url)
                mediaPlayer.prepareAsync()
            } else {  //base 64
                val data: ByteArray = Base64.decode(url, Base64.DEFAULT)

                //this is modified and not testing
                mediaPlayer.setDataSource(object : MediaDataSource() {
                    override fun close() {

                    }

                    override fun readAt(p0: Long, p1: ByteArray?, p2: Int, p3: Int): Int {
                        val length = size
                        var mSize = p3
                        if (p0 >= length) return -1 // EOF

                        if (p0 + size > length) // requested more than available
                            mSize = (length - p0).toInt() // set size to maximum size possible


                        System.arraycopy(data, p0.toInt(), p1, p2, mSize)
                        return size.toInt()
                    }

                    override fun getSize(): Long {
                        return data.size.toLong()
                    }

                })
            }
        } catch (e: Exception) { //TODO LOGGING
            e.printStackTrace()
        }
    }

    /**
     * This method sets the listeners for the media player in which the audio is started based on the queue
     * */
    private fun setMediaPlayerListeners(mediaPlayer: MediaPlayer) {
        mediaPlayer.setOnPreparedListener {
            if (audioQueue.peek() == it) {
                //if the first audio in the queue is not playing play it
                if (audioQueue.peek()?.isPlaying != true) {
                    audioQueue.peek()?.start()
                }
            }

        }

        mediaPlayer.setOnErrorListener { mp, i, i2 ->
            startNextAudio()
            onAudioCompleteListener?.invoke()
            false
        }


        mediaPlayer.setOnCompletionListener {
            startNextAudio()
            onAudioCompleteListener?.invoke()
        }

    }

    private fun startNextAudio() {
        //remove completed audio from queue
        val player = audioQueue.poll()
        releasePlayer(player)

        //if there is another audio in the queue start it
        if (!audioQueue.isEmpty()) {
            audioQueue.peek()?.start()
        }
    }

    fun stopAndClearAudio() {
        if (audioQueue.isNotEmpty()) {
            audioQueue.forEach {
                it.stop()
                releasePlayer(it)
            }

            audioQueue.clear()
        }
    }

    fun isQueueEmpty() = audioQueue.isEmpty()


    private fun releasePlayer(player:MediaPlayer?){
        if(player?.isPlaying == true)
            player.stop()
        player?.reset()
        player?.release()
    }
}