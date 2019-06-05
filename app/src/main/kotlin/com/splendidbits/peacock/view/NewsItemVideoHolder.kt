package com.splendidbits.peacock.view

import android.content.Context
import android.net.Uri
import android.view.View
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.video.VideoListener
import com.splendidbits.peacock.main.PeacockApplication
import im.ene.toro.ToroPlayer
import im.ene.toro.ToroUtil
import im.ene.toro.exoplayer.ExoCreator
import im.ene.toro.exoplayer.ExoPlayerViewHelper
import im.ene.toro.media.PlaybackInfo
import im.ene.toro.widget.Container
import kotlinx.android.synthetic.main.view_holder_news_video.view.*
import javax.inject.Inject


class NewsItemVideoHolder(itemView: View) : AbstractNewsItemHolder(itemView), ToroPlayer, VideoListener, ToroPlayer.EventListener {
    @Inject
    lateinit var context: Context

    @Inject
    lateinit var exoCreator: ExoCreator

    private val videoPlayerContainer = itemView.videoPlayerContainer
    private var exoPlayerHelper: ExoPlayerViewHelper? = null
    private val videoPlayerView: PlayerView = itemView.videoPlayer
    private var videoOverlayPill = itemView.videoVolumePill
    val summaryText = itemView.summary

    init {
        PeacockApplication.graph.inject(this)
        videoPlayerView.player = exoCreator.createPlayer()
    }

    var videoUri: Uri = Uri.EMPTY
        set(value) {
            exoPlayerHelper = ExoPlayerViewHelper(this, value, "m3u8", exoCreator)
            exoPlayerHelper?.addPlayerEventListener(this)
        }

    override fun initialize(container: Container, playbackInfo: PlaybackInfo) {
        exoPlayerHelper?.initialize(container, playbackInfo)
        exoPlayerHelper?.volume = 0F
        exoPlayerHelper?.play()

        // Un-mute video on video tap.
        videoPlayerContainer.setOnClickListener {
            videoOverlayPill.visibility = View.GONE
            exoPlayerHelper?.volume = 1F
        }
    }

    override fun isPlaying(): Boolean {
        return exoPlayerHelper?.isPlaying ?: false
    }

    override fun getPlayerView(): View {
        return videoPlayerView
    }

    override fun pause() {
        exoPlayerHelper?.pause()
    }

    override fun wantsToPlay(): Boolean {
        return (ToroUtil.visibleAreaOffset(this, videoPlayerContainer) > 0.65)
    }

    override fun play() {
        exoPlayerHelper?.play()
    }

    override fun getCurrentPlaybackInfo(): PlaybackInfo {
        return exoPlayerHelper?.latestPlaybackInfo ?: PlaybackInfo.SCRAP
    }

    override fun release() {
        onCompleted()

        videoPlayerContainer.setOnClickListener(null)
        exoPlayerHelper?.removePlayerEventListener(this)
        exoPlayerHelper?.release()
    }

    override fun onVideoSizeChanged(width: Int, height: Int, unappliedRotationDegrees: Int, pixelWidthHeightRatio: Float) {
    }

    override fun onBuffering() {
    }

    override fun onPlaying() {
        teaseImage.visibility = View.GONE
        videoOverlayPill.visibility = View.VISIBLE
    }

    override fun onPaused() {
    }

    override fun onCompleted() {
        teaseImage.visibility = View.VISIBLE
        videoOverlayPill.visibility = View.GONE
    }

    override fun onRenderedFirstFrame() {
    }

    override fun getPlayerOrder(): Int {
        return adapterPosition
    }
}
