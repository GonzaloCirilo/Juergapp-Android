package pe.com.redcups.juergapp_android.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_view_contact.view.*
import pe.com.redcups.juergapp_android.R


data class Contact(
    var name: String,
    var phone: String,
    var invited: Boolean = false
)
class ContactsAdapter(context: Context): RecyclerView.Adapter<ContactsAdapter.ViewHolder>(){
    private var contacts: List<Contact> = emptyList()
    private var inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsAdapter.ViewHolder {
        val view = inflater.inflate(R.layout.recycler_view_contact, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            val contact = contacts[position]
            contactNameTextView.text = contact.name
            contactPhoneTextView.text = contact.phone
            contactInviteButton.text = if (contact.invited) "¡Invitado!" else "Invitar"

            contactInviteButton.setOnClickListener{
                // invitar
                contact.invited = !contact.invited
                contactInviteButton.text = if (contact.invited) "¡Invitado!" else "Invitar"
                contactInviteButton.background.setTint(itemView.resources.getColor(R.color.background_material_light))
                contactInviteButton.setTextColor(itemView.resources.getColor(R.color.primary_dark_material_dark))
                // invitar con backend ps mascota
            }
        }
    }
    override fun getItemCount(): Int = contacts.size

    inner class ViewHolder(contactView: View):RecyclerView.ViewHolder(contactView){
        val contactNameTextView = contactView.contact_name_textview
        val contactPhoneTextView = contactView.contact_phone_textview
        val contactInviteButton = contactView.contact_invite_button

    }
    fun setContacts(contacts: List<Contact>){
        this.contacts = contacts
        this.notifyDataSetChanged()
    }
}