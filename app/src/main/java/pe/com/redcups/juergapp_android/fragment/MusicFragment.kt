package pe.com.redcups.juergapp_android.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector

import pe.com.redcups.juergapp_android.R
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.protocol.types.ImageUri
import kotlinx.android.synthetic.main.fragment_music.*

class MusicFragment : Fragment() {
    private val CLIENT_ID = "0123e5d686434a9dbac22762dc611c6f"
    private val REDIRECT_URI = "juergapp://callback"
    private lateinit var mSpotifyAppRemote: SpotifyAppRemote

    var isPlaying = false
    var canSkipNext = false
    var canSkipPrev = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_music, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toggle_play_button.setOnClickListener {
            if (::mSpotifyAppRemote.isInitialized){
                isPlaying = if(!isPlaying){
                    mSpotifyAppRemote.playerApi.resume()
                    !isPlaying
                }else{
                    mSpotifyAppRemote.playerApi.pause()
                    !isPlaying
                }
            }else{
                Toast.makeText(context,"Not connected yet",Toast.LENGTH_LONG).show()
            }
        }

        prev_button.setOnClickListener{
            if (::mSpotifyAppRemote.isInitialized){
                if(canSkipPrev)
                    mSpotifyAppRemote.playerApi.skipPrevious()
                else
                    Toast.makeText(context,"No more skips for you",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(context,"Not connected yet",Toast.LENGTH_LONG).show()
            }
        }

        next_button.setOnClickListener{
            if (::mSpotifyAppRemote.isInitialized){
                if(canSkipNext)
                    mSpotifyAppRemote.playerApi.skipNext()
                else
                    Toast.makeText(context,"No more skips for you!",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(context,"Not connected yet",Toast.LENGTH_LONG).show()
            }
        }
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
                    Toast.makeText(context,"Error!",Toast.LENGTH_LONG).show()
                    Log.e("MyActivity", throwable.message, throwable)
                }

                override fun onConnected(spotifyAppRemote: SpotifyAppRemote) {
                    mSpotifyAppRemote = spotifyAppRemote
                    Toast.makeText(context,"Connected!",Toast.LENGTH_SHORT).show()
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
                    Toast.makeText(
                        context,
                        "${t.name}",
                        Toast.LENGTH_SHORT).show()
                    song_text_view.text = t.name
                    artists_text_view.text = t.artist.name
                    setArtwork(t.imageUri)
                    track_length_text_view.text = duration
                }
                isPlaying = !playerState.isPaused
                canSkipNext = playerState.playbackRestrictions.canSkipNext
                canSkipPrev = playerState.playbackRestrictions.canSkipPrev
                current_second.text = milisToMMSS(playerState.playbackPosition)
                track_progress.progress = ((playerState.playbackPosition.toDouble()/track.duration) * 100).toInt()
            }
    }

    fun setArtwork(imageUri: ImageUri){
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
