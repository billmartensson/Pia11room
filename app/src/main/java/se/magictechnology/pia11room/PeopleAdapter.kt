package se.magictechnology.pia11room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PeopleAdapter(val mainact : MainActivity) : RecyclerView.Adapter<PeopleAdapter.ViewHolder>() {

    var people = mutableListOf<User>()

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val personName : TextView

        init {
            personName = view.findViewById(R.id.usernameTV)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.personName.text = people[position].firstName + " " + people[position].lastName

        holder.itemView.setOnClickListener {
            mainact.clickRow(people[position])
        }

    }

    override fun getItemCount(): Int {
        return people.size
    }

}