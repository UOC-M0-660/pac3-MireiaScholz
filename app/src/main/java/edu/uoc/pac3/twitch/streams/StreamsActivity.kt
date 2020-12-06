package edu.uoc.pac3.twitch.streams

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.uoc.pac3.R
import edu.uoc.pac3.data.TwitchApiService
import edu.uoc.pac3.data.network.Network
import edu.uoc.pac3.data.streams.StreamsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StreamsActivity : AppCompatActivity() {

    private val TAG = "StreamsActivity"
    private lateinit var adapter: StreamListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_streams)
        // Init RecyclerView
        initRecyclerView()
        getStreams()
    }

    private fun initRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Set Layout Manager
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        // Init Adapter
        adapter = StreamListAdapter(listOf(), this)
        recyclerView.adapter = adapter
    }

    private fun getStreams() {
        val twitch = TwitchApiService(Network.createHttpClient(this))
        GlobalScope.launch(Dispatchers.IO) {
            // Fetch streams from twitch from a IO Coroutine
            val streams: StreamsResponse = twitch.getStreams()

            // Switch to main thread in order to update the list
            withContext(Dispatchers.Main){
                streams.data?.let { adapter.setStreams(it) }
            }
        }
    }

}