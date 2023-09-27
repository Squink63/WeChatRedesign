package com.padcmyanmar.abbc.wechatredesign.network

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.android.play.core.integrity.p
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.padcmyanmar.abbc.wechatredesign.data.vos.MomentVO
import com.padcmyanmar.abbc.wechatredesign.data.vos.UserVO
import java.io.ByteArrayOutputStream
import java.util.UUID

object CloudFirestoreFirebaseImpl: CloudFirestoreFirebaseApi {

    @SuppressLint("StaticFieldLeak")
    private var database: FirebaseFirestore = Firebase.firestore
    private var storageReference= FirebaseStorage.getInstance().reference

    private var mMomentImages: String = ""

    override fun addUser(user: UserVO) {
        val userMap = hashMapOf(
            "email" to user.email,
            "password" to user.password,
            "user_name" to user.userName,
            "phone_number" to user.phoneNumber,
            "user_id" to user.userId,
            "profile_picture" to user.profilePicture,
            "birth_date" to user.birthDate,
            "qr_code" to user.qrCode,
            "gender" to user.gender
        )

        database.collection("users")
            .document(user.userId)
            .set(userMap)
            .addOnSuccessListener { Log.d("Success", "Successfully added user") }
            .addOnFailureListener { Log.d("Failure", "Failed to add user") }
    }

    private fun changeBitmapToUrlString(bitmap: Bitmap): Task<Uri>{
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val imageRef = storageReference.child("images/${UUID.randomUUID()}")
        val uploadTask = imageRef.putBytes(data)
        uploadTask.addOnFailureListener {
            Log.i("Upload", "Failed to upload")
        }.addOnSuccessListener {
            Log.i("Upload", "Successfully uploaded")
        }

        val urlTask = uploadTask.continueWithTask {
            return@continueWithTask imageRef.downloadUrl
        }
        return urlTask
    }

    override fun uploadAndUpdateProfilePicture(bitmap: Bitmap, user: UserVO) {

        val urlTask = changeBitmapToUrlString(bitmap)

        urlTask.addOnCompleteListener { task ->
            val imageUrl = task.result?.toString()
            addUser(
                UserVO(
                    email = user.email,
                     password = user.password,
                     userName = user.userName,
                     userId = user.userId,
                     phoneNumber = user.phoneNumber,
                     profilePicture = imageUrl ?: "",
                    birthDate = user.birthDate,
                     qrCode = user.qrCode,
                     gender = user.gender
                )
            )
        }
    }

    override fun getUser(onSuccess: (users: List<UserVO>) -> Unit, onFailure: (String) -> Unit) {

        database.collection("users")
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.localizedMessage ?: "Check Internet Connection")
                } ?: run {
                    val userList: MutableList<UserVO> =  arrayListOf()
                    val result = value?.documents ?: arrayListOf()

                    for (document in result){
                        val data = document.data
                        val userId = data?.get("user_id") as String
                        val username = data["user_name"] as String
                        val password = data["password"] as String
                        val email = data["email"] as String
                        val phoneNumber = data["phone_number"] as String
                        val birthDate = data["birth_date"] as String
                        val qrCode = data["qr_code"] as String
                        val profilePicture = data["profile_picture"] as String
                        val gender = data["gender"] as String
                        val user = UserVO(email, password, username, phoneNumber, userId, profilePicture, qrCode, birthDate, gender)
                        userList.add(user)
                    }
                    onSuccess(userList)
                }
            }
    }

    override fun createMoment(moment: MomentVO) {
        val momentMap = hashMapOf(
            "id" to moment.id,
            "user_id" to moment.userId,
            "user_name" to moment.userName,
            "user_profile_image" to moment.userProfileImage,
            "caption" to moment.caption,
            "image_url" to moment.imageUrl,
            "is_bookmarked" to moment.isBookmarked
        )

        database.collection("moments")
            .document(moment.id)
            .set(momentMap)
            .addOnSuccessListener {
                Log.i("FirebaseCall", "Successfully Created")
            }.addOnFailureListener {
                Log.i("FirebaseCall", "Failed To Created")
            }
    }

    override fun updateAndUploadMomentImage(bitmap: Bitmap) {
        val urlTask = changeBitmapToUrlString(bitmap)

        urlTask.addOnCompleteListener {
            val imageUrl = it.result?.toString()
            mMomentImages += "$imageUrl,"
        }
    }

    override fun getMomentImages(): String {
        return mMomentImages
    }

    override fun getMoments(
        onSuccess: (moments: List<MomentVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.collection("moments")
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.localizedMessage ?: "Check Internet Connection")
                } ?: run {
                    val momentList: MutableList<MomentVO> = arrayListOf()
                    val documentList = value?.documents ?: arrayListOf()
                    for (document in documentList) {
                        val data = document.data
                        val id = data?.get("id") as String
                        val userId = data["user_id"] as? String ?: ""
                        val userName = data["user_name"] as String
                        val userProfileImage = data["user_profile_image"] as String
                        val caption = data["caption"] as String
                        val imageUrl = data["image_url"] as String
                        val isBookmarked = data["is_bookmarked"] as? Boolean ?: false
                        val moment = MomentVO(
                            id,
                            userId,
                            userName,
                            userProfileImage,
                            caption,
                            imageUrl,
                            isBookmarked
                        )
                        momentList.add(moment)
                    }
                    onSuccess(momentList)
                }
            }
    }

    override fun addContact(scannerId: String, exporterId: String, contact: UserVO) {

        val userMap = hashMapOf(
            "email" to contact.email,
            "password" to contact.password,
            "user_name" to contact.userName,
            "phone_number" to contact.phoneNumber,
            "user_id" to contact.userId,
            "profile_picture" to contact.profilePicture,
            "birth_date" to contact.birthDate,
            "qr_code" to contact.qrCode,
            "gender" to contact.gender
        )

        database.collection("users")
            .document(scannerId)
            .collection("contacts")
            .document(exporterId)
            .set(userMap)
            .addOnSuccessListener {
                Log.i("FirebaseCall", "Successfully Added")
            }.addOnFailureListener {
                Log.i("FirebaseCall", "Failed To Add")
            }
    }

    override fun getContacts(
        scannerId: String,
        onSuccess: (users: List<UserVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.collection("users")
            .document(scannerId)
            .collection("contacts")
            .addSnapshotListener { value, error ->
                error?.let {
                    onFailure(it.localizedMessage ?: "Check Internet Connection")
                } ?: run {
                    val userList: MutableList<UserVO> = arrayListOf()
                    val result = value?.documents ?: arrayListOf()
                    for (document in result){
                        val data = document.data
                        val userId = data?.get("user_id") as String
                        val username = data["user_name"] as String
                        val password = data["password"] as String
                        val email = data["email"] as String
                        val phoneNumber = data["phone_number"] as String
                        val birthDate = data["birth_date"] as String
                        val qrCode = data["qr_code"] as String
                        val profilePicture = data["profile_picture"] as String
                        val gender = data["gender"] as String
                        val user = UserVO(email, password, username, phoneNumber, userId, profilePicture, qrCode, birthDate, gender)
                        userList.add(user)
                    }
                    onSuccess(userList)
                }
            }
    }


}