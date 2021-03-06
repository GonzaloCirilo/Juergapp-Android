package pe.com.redcups.juergapp_android.fragment

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector

import pe.com.redcups.juergapp_android.R
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.protocol.types.Artist
import com.spotify.protocol.types.ImageUri
import kotlinx.android.synthetic.main.fragment_music.*
import pe.com.redcups.core.model.Track
import pe.com.redcups.juergapp_android.adapter.MusicAdapter
import pe.com.redcups.juergapp_android.animation.ProgressBarAnimation

class MusicFragment : Fragment() {
    private val CLIENT_ID = "0123e5d686434a9dbac22762dc611c6f"
    private val REDIRECT_URI = "juergapp://callback"
    private lateinit var mSpotifyAppRemote: SpotifyAppRemote

    private var isPlaying = false
    private var canSkipNext = false
    private var canSkipPrev = false

    private fun List<Artist>.toCustomString(): String{
        val size = this.size
        if (size == 1){
            return this[0].name
        }
        if (size == 2){
            return "${this[0].name} & ${this[1].name}"
        }
        var s = ""
        for (i in 0 until (size - 2)){
            s ="$s${this[i].name}, "
        }
        s = "$s${this[size-2].name} & ${this[size-1].name}"
        return s
    }

    private fun afterSpotifyApiCreated(func: () -> (Unit)){
        if (::mSpotifyAppRemote.isInitialized){
            func()
        }else{
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_music, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        song_text_view.isSelected = true

        toggle_play_button.setOnClickListener {
            afterSpotifyApiCreated {
                if(!isPlaying){
                    mSpotifyAppRemote.playerApi.resume()
                    toggle_play_button.setImageResource(R.drawable.ic_pause_black_24dp)
                }else{
                    mSpotifyAppRemote.playerApi.pause()
                    toggle_play_button.setImageResource(R.drawable.ic_play_arrow_black_24dp)
                }
            }
        }

        prev_button.setOnClickListener{
            afterSpotifyApiCreated {
                if(canSkipPrev)
                    mSpotifyAppRemote.playerApi.skipPrevious()
            }
        }

        next_button.setOnClickListener{
            afterSpotifyApiCreated {
                if(canSkipNext)
                    mSpotifyAppRemote.playerApi.skipNext()
            }
        }

        recycler_view_music.layoutManager = LinearLayoutManager(view!!.context)
        recycler_view_music.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        val tr = mutableListOf(
            Track("id", "Track 1", "Various Artists"),
            Track("id", "Track 2", "Various Artists"),
            Track("id", "Track 3", "Various Artists")
        )
        val adapter = MusicAdapter(tr, context!!)
        recycler_view_music.adapter = adapter
    }

    override fun onStart() {
        super.onStart()

        val connectionParams = ConnectionParams.Builder(CLIENT_ID)
            .setRedirectUri(REDIRECT_URI)
            .showAuthView(true)
            .build()

        SpotifyAppRemote.connect(context, connectionParams,
            object: Connector.ConnectionListener{
                override fun onFailure(throwable: Throwable) {
                    Log.e("MyActivity", throwable.message, throwable)
                }

                override fun onConnected(spotifyAppRemote: SpotifyAppRemote) {
                    mSpotifyAppRemote = spotifyAppRemote
                    connected()
                }
            })
    }

    private fun milisToMMSS(mili: Long) = "${mili/60000}:${((mili/1000)%60).toString().padStart(2,'0')}"

    fun connected(){
        // Subscribe to Observer
        mSpotifyAppRemote.playerApi
            .subscribeToPlayerState()
            .setEventCallback { playerState ->
                val track = playerState.track
                val duration = milisToMMSS(track.duration)
                track?.let {t ->
                    if(song_text_view.text != t.name)
                        song_text_view.text = t.name
                    artists_text_view.text = t.artists.toCustomString()
                    setArtwork(t.imageUri)
                    track_length_text_view.text = duration
                }
                // Setup State values
                isPlaying = !playerState.isPaused
                canSkipNext = playerState.playbackRestrictions.canSkipNext
                canSkipPrev = playerState.playbackRestrictions.canSkipPrev

                // Set Track Progress State
                chronometer.base = SystemClock.elapsedRealtime() - playerState.playbackPosition
                if (isPlaying)
                    chronometer.start()
                else
                    chronometer.stop()
                //current_second.text = milisToMMSS(playerState.playbackPosition)
                track_progress.max = track.duration.toInt()
                track_progress.progress = playerState.playbackPosition.toInt()

                if(isPlaying){
                    val anim = ProgressBarAnimation(track_progress, track_progress.progress.toFloat(), track.duration.toFloat())
                    anim.duration = track.duration - playerState.playbackPosition
                    anim.interpolator = LinearInterpolator()
                    track_progress.startAnimation(anim)
                    toggle_play_button.setImageResource(R.drawable.ic_pause_black_24dp)
                }else{
                    track_progress.clearAnimation()
                    toggle_play_button.setImageResource(R.drawable.ic_play_arrow_black_24dp)
                }
            }
    }

    private fun setArtwork(imageUri: ImageUri){
        mSpotifyAppRemote.imagesApi.getImage(imageUri).setResultCallback {
            artwork_image_view.setImageBitmap(it)
        }
    }

    override fun onStop() {
        super.onStop()
        // Disconnect when leaving the activity
        if (::mSpotifyAppRemote.isInitialized){
            SpotifyAppRemote.disconnect(mSpotifyAppRemote)
        }
    }
}
