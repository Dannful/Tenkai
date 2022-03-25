package me.dannly.profile_domain.model.activity

data class MessageActivity(
    val id: Int,
    val message: String,
    val senderImage: String?,
    val senderName: String,
    val senderId: Int
)
