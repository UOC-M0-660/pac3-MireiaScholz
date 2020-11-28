package edu.uoc.pac3.data.oauth

import edu.uoc.pac3.data.network.Endpoints
import java.util.*

/**
 * Created by alex on 07/09/2020.
 */
object OAuthConstants {
    val uniqueState = UUID.randomUUID().toString()
    val clientID = "8gou37grfoher93bz6w3qepl9dwo7s"
    val redirectUri = "http://localhost"
    val scopes: List<String> = listOf("user:read:email","user:edit")
    val clientSecret = "juj8ov9gmvt01q7tusu8th3pawpg7f"
}