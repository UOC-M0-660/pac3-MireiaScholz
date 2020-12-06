package edu.uoc.pac3.twitch.profile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import edu.uoc.pac3.R
import edu.uoc.pac3.data.SessionManager
import edu.uoc.pac3.data.TwitchApiService
import edu.uoc.pac3.data.network.Network
import edu.uoc.pac3.data.user.User
import edu.uoc.pac3.oauth.LoginActivity
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileActivity : AppCompatActivity() {

    private val TAG = "ProfileActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        GlobalScope.launch(Dispatchers.IO) {
            val user: User? = getProfile()
            withContext(Dispatchers.Main) {
                userNameTextView.text = user?.userName
                viewsText.text = user?.viewCount.toString()
                userDescriptionEditText.setText(user?.description)
                Glide.with(this@ProfileActivity)
                    .load(user?.profileImageUrl)
                    .into(imageView)
            }
        }

        updateDescriptionButton.setOnClickListener { updateUserDescription(userDescriptionEditText.text.toString()) }
        logoutButton.setOnClickListener { logOut() }
    }

    private fun updateUserDescription(description: String) {
        val twitch = TwitchApiService(Network.createHttpClient(this))
        GlobalScope.launch(Dispatchers.IO) {
            val user: User? = twitch.updateUserDescription(description)
            withContext(Dispatchers.Main) {
                userDescriptionEditText.setText(user?.description)
            }
        }
    }

    private suspend fun getProfile(): User? {
        val twitch = TwitchApiService(Network.createHttpClient(this))
        return twitch.getUser()
    }

    private fun logOut() {
        val session = SessionManager(this)
        session.clearAccessToken()
        session.clearRefreshToken()
        startActivity(Intent(this, LoginActivity::class.java))
    }
}