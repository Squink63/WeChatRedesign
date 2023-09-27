package com.padcmyanmar.abbc.wechatredesign.network

import android.graphics.Bitmap
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.padcmyanmar.abbc.wechatredesign.data.vos.GroupVO
import com.padcmyanmar.abbc.wechatredesign.data.vos.MessageVO
import java.io.ByteArrayOutputStream
import java.util.*

object RealtimeDatabaseFirebaseImpl: RealtimeDatabaseFirebaseApi {

    private val database: DatabaseReference= Firebase.database.reference
    private val storage= FirebaseStorage.getInstance()
    private val storageReference = storage.reference

    override fun getOtp(onSuccess: (code: String) -> Unit, onFailure: (String) -> Unit) {

        database.child("otp_code").addValueEventListener(object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                onSuccess(snapshot.value.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                onFailure(error.message)
            }

        })
    }

    override fun sendMessage(
        senderId: String,
        receiverId: String,
        timeStamp: Long,
        message: MessageVO
    ) {
        database.child("contactsAndMessages").child(senderId).child(receiverId)
            .child(timeStamp.toString()).setValue(message)
    }

    override fun getMessages(
        senderId: String,
        receiverId: String,
        onSuccess: (messageList: List<MessageVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.child("contactsAndMessages")
            .child(senderId)
            .child(receiverId)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    onFailure(error.message)
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val messageList = arrayListOf<MessageVO>()
                    snapshot.children.forEach { dataSnapShot ->
                        dataSnapShot.getValue(MessageVO::class.java)?.let {
                            messageList.add(it)
                        }
                    }
                    onSuccess(messageList)
                }
            })
    }

    override fun uploadAndSendImage(
        bitmap: Bitmap,
        onSuccess: (file: String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val imageRef = storageReference.child("images/${UUID.randomUUID()}")
        val uploadTask = imageRef.putBytes(data)
        uploadTask.addOnFailureListener {
            Log.i("FileUpload", "File uploaded failed")
        }.addOnSuccessListener {
            Log.i("FileUpload", "File uploaded successful")
        }

        val urlTask = uploadTask.continueWithTask {
            return@continueWithTask imageRef.downloadUrl
        }.addOnCompleteListener {
            val imageUrl = it.result?.toString()
            if (imageUrl != null) {
                onSuccess(imageUrl)
            }
        }
    }
    override fun getChatHistoryUserId(
        senderId: String,
        onSuccess: (messageList: List<String>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.child("contactsAndMessages")
            .child(senderId)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    onFailure(error.message)
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val messageList = arrayListOf<String>()
                    snapshot.children.forEach { dataSnapShot ->
                        dataSnapShot.key?.let {
                            messageList.add(it)
                        }
                    }
                    onSuccess(messageList)
                }
            })
    }

    override fun addGroup(
        timeStamp: Long,
        groupName: String,
        userList: List<String>,
        imageUrl: String
    ) {
        database.child("groups").child(timeStamp.toString()).child("name").setValue(groupName)
        database.child("groups").child(timeStamp.toString()).child("userIdList").setValue(userList)
        database.child("groups").child(timeStamp.toString()).child("id").setValue(timeStamp)
        database.child("groups").child(timeStamp.toString()).child("imageUrl").setValue(imageUrl)
    }

    override fun getGroups(
        onSuccess: (groupIdList: List<GroupVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.child("groups")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    onFailure(error.message)
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val groupList = arrayListOf<GroupVO>()
                    snapshot.children.forEach { dataSnapShot ->
                        dataSnapShot.getValue(GroupVO::class.java)?.let {
                            groupList.add(it)
                        }
                    }
                    onSuccess(groupList)
                }
            })
    }

    override fun sendGroupMessage(groupId: Long,timeStamp:Long, message: MessageVO) {
        database.child("groups").child(groupId.toString()).child("messages")
            .child(timeStamp.toString()).setValue(message)
    }

    override fun getGroupMessages(
        groupId: Long,
        onSuccess: (messageList: List<MessageVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.child("groups")
            .child(groupId.toString())
            .child("messages")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    onFailure(error.message)
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val messageList = arrayListOf<MessageVO>()
                    snapshot.children.forEach { dataSnapShot ->
                        dataSnapShot.getValue(MessageVO::class.java)?.let {
                            messageList.add(it)
                        }
                    }
                    onSuccess(messageList)
                }
            })
    }

    override fun uploadGroupCoverPhoto(
        timeStamp: Long,
        bitmap: Bitmap,
        onSuccess: (imageUrl: String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val imageRef = storageReference.child("images/${UUID.randomUUID()}")
        val uploadTask = imageRef.putBytes(data)
        uploadTask.addOnFailureListener {
            Log.i("FileUpload", "Failed to upload file")
        }.addOnSuccessListener {
            Log.i("FileUpload", "File uploaded successful")
        }

        val urlTask = uploadTask.continueWithTask {
            return@continueWithTask imageRef.downloadUrl
        }.addOnCompleteListener {
            val imageUrl = it.result?.toString()
            if (imageUrl != null) {
                onSuccess(imageUrl)
            }
        }.addOnFailureListener { error ->
            onFailure(error.localizedMessage ?: "")
        }
    }
}