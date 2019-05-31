package pe.com.redcups.juergapp_android.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_view_participant.view.*
import pe.com.redcups.core.model.User
import pe.com.redcups.core.utilities.BitmapUtils
import pe.com.redcups.juergapp_android.R


class ParticipantAdapter(context: Context): RecyclerView.Adapter<ParticipantAdapter.ViewHolder>(){

    private var participants: List<User> = listOf(User(0, "cat_id", "biografia", "url"),
        User(0, "otra persona", "biografia", "url"))
    private var inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = inflater.inflate(R.layout.recycler_view_participant, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = participants.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with (holder){
            var participant  = participants[position]
            participantTextView.text = participant.name
            participantAddressTextView.text = participant.name

            participantImageView.setImageResource(R.mipmap.event_image_placeholder)
            if (participant.picture_data != null){
                participantImageView.setImageBitmap(BitmapUtils.stringToBitmap(participant.picture_data!!))
            }
            itemView.setOnClickListener{
                //TODO: navigate to user profile

                // aca le pasas el argumento del participanto por Safe Args
                //https://developer.android.com/guide/navigation/navigation-pass-data
                //val action = ParticipantFragmentDirections.getDetailsAction(participant.id.toString(), participant.name)
                //it.findNavController().navigate(action, options)
            }
        }
    }

    inner class ViewHolder(participantView: View): RecyclerView.ViewHolder(participantView) {
        val participantTextView: TextView = participantView.participant_name
        val participantAddressTextView: TextView = participantView.participant_address
        val participantImageView: ImageView = participantView.participant_image
    }

    // updates Participants Array

    fun setParticipants(participants: List<User>){
        this.participants = participants
        this.notifyDataSetChanged()
    }
}