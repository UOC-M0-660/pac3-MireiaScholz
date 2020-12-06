package edu.uoc.pac3.twitch.streams

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import edu.uoc.pac3.R
import edu.uoc.pac3.data.streams.Stream

class StreamListAdapter(private var streams: List<Stream>, private val context: Context) :
    RecyclerView.Adapter<StreamListAdapter.ViewHolder>() {

    private fun getStream(position: Int): Stream {
        return streams[position]
    }

    fun setStreams(streams: List<Stream>) {
        this.streams = streams
        // Reloads the RecyclerView with new adapter data
        notifyDataSetChanged()
    }

    // Creates View Holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.stream_list, parent, false)
        return ViewHolder(view)
    }

    // Binds re-usable View for a given position
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val stream = getStream(position)
        holder.titleView.text = stream.title
        holder.usernameView.text = stream.userName
        val thumbnailUrl = stream.thumbnailUrl?.replace("{width}", "400")?.replace("{height}", "200")
        Glide.with(context)
            .load(thumbnailUrl)
            .into(holder.thumbnailView)

        // TODO: Detalles del stream
//        holder.view.setOnClickListener { view ->
//            stream.uid?.let { uid ->
//                val intent = Intent(view.context, BookDetailActivity::class.java).apply {
//                    putExtra(BookDetailFragment.ARG_ITEM_ID, uid)
//                }
//                view.context.startActivity(intent)
//                (view.context as Activity).overridePendingTransition(
//                    R.anim.translate_in_bottom,
//                    R.anim.translate_in_top
//                )
//            }
//        }

    }

    // Returns total items in Adapter
    override fun getItemCount(): Int {
        return streams.size
    }

    // Holds an instance to the view for re-use
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleView: TextView = view.findViewById(R.id.title)
        val usernameView: TextView = view.findViewById(R.id.username)
        val thumbnailView: ImageView = view.findViewById(R.id.thumbnail)
    }

}