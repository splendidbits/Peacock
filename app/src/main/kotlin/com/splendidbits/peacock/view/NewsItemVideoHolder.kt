package com.splendidbits.peacock.view

import android.media.session.PlaybackState
import android.net.Uri
import android.view.View
import im.ene.toro.ToroPlayer
import im.ene.toro.ToroUtil
import im.ene.toro.exoplayer.Playable
import im.ene.toro.media.PlaybackInfo
import im.ene.toro.widget.Container
import kotlinx.android.synthetic.main.view_holder_news_video.view.*

class NewsItemVideoHolder(itemView: View) : AbstractNewsItemHolder(itemView), ToroPlayer {
    val teaseImage = itemView.teaseImage
    val headlineText = itemView.headline
    val summaryText = itemView.summary
    var videoUri = Uri.EMPTY
    var videoVolumePill = itemView.videoVolumePill

    private val videoPlayerContainer = itemView.videoPlayerContainer
    private val videoPlayer = itemView.videoPlayer
    private var exoPlayerHelper: ExoPlayerViewHelper? = null

    private val listener = object : Playable.DefaultEventListener() {
        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            super.onPlayerStateChanged(playWhenReady, playbackState)

            if (playbackState == PlaybackState.STATE_PLAYING) {
                teaseImage.visibility = View.GONE
            } else {
                teaseImage.visibility = View.VISIBLE
            }
        }
    }

    override fun initialize(container: Container, playbackInfo: PlaybackInfo) {
        if (exoPlayerHelper == null || exoPlayerHelper?.videoUri != videoUri) {
            exoPlayerHelper = ExoPlayerViewHelper(this, videoUri)
        }

        // Un-mute video on video tap.
        videoPlayerContainer.setOnClickListener {
            exoPlayerHelper?.volumeInfo?.isMute = false
            exoPlayerHelper?.volume = 1F
            videoVolumePill.visibility = View.GONE
        }

        exoPlayerHelper?.removeEventListener(listener)
        exoPlayerHelper?.addEventListener(listener)

        exoPlayerHelper?.initialize(container, playbackInfo)
        exoPlayerHelper?.volumeInfo?.isMute = true
        exoPlayerHelper?.volume = 0F
    }

    override fun isPlaying(): Boolean {
        return exoPlayerHelper?.isPlaying ?: false
    }

    override fun getPlayerView(): View {
        return videoPlayer
    }

    override fun pause() {
        exoPlayerHelper?.pause()
    }

    override fun wantsToPlay(): Boolean {
        return (ToroUtil.visibleAreaOffset(this, videoPlayerContainer) >= 0.65)
    }

    override fun play() {
        exoPlayerHelper?.play()
    }

    override fun getCurrentPlaybackInfo(): PlaybackInfo {
        return exoPlayerHelper?.latestPlaybackInfo ?: PlaybackInfo.SCRAP
    }

    override fun release() {
        teaseImage.visibility = View.VISIBLE
        videoPlayerContainer.setOnClickListener(null)
        exoPlayerHelper?.release()
        exoPlayerHelper?.removeEventListener(listener)
    }

    override fun getPlayerOrder(): Int {
        return 0
    }
}

// Kotlin extended class so that we can return uri which we can't in the original
class ExoPlayerViewHelper(toroPlayer: ToroPlayer, val videoUri: Uri = Uri.EMPTY):
        im.ene.toro.exoplayer.ExoPlayerViewHelper(toroPlayer, videoUri)