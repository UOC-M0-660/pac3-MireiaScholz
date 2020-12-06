package edu.uoc.pac3.twitch.streams

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.uoc.pac3.R
import edu.uoc.pac3.data.streams.Stream

class StreamsActivity : AppCompatActivity() {

    private val TAG = "StreamsActivity"
    private lateinit var adapter: StreamListAdapter
    private var streams: MutableList<Stream> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_streams)
        // Init RecyclerView
        initRecyclerView()

        // TODO: Get Streams
        //getStreams()
    }

    private fun initRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Set Layout Manager
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        // Init Adapter
        streams = mutableListOf(Stream("AuronPlay", "GTA Roleplay"), Stream("Rubius", "Just chatting"))
        adapter = StreamListAdapter(streams)
        recyclerView.adapter = adapter
    }

    private fun getStreams() {
        TODO("Not yet implemented")
    }

}