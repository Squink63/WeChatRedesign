package com.padcmyanmar.abbc.wechatredesign.network.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.padcmyanmar.abbc.wechatredesign.data.vos.UserVO

object FirebaseAuthManager : AuthManager {

    private val mFirebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun login(
        email: String,
        password: String,
        phoneNumber: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful && it.isComplete) {
                onSuccess()
            } else {
                onFailure(it.exception?.message ?: "Please Check Internet Connection" )
            }
        }
    }

    override fun register(
        email: String,
        phoneNumber: String,
        password: String,
        userName: String,
        profilePicture: String,
        birthDate: String,
        gender: String,
        onSuccess: (user: UserVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful && it.isComplete) {
                mFirebaseAuth.currentUser?.updateProfile(
                    UserProfileChangeRequest.Builder().setDisplayName(userName).build()
                )
                onSuccess(UserVO(email, password, userName, phoneNumber, getUserId(), profilePicture, getUserId(), birthDate, gender))
            } else {
                onFailure(it.exception?.message ?: "Please Check Internet Connection")
            }
        }
    }

    override fun getUserId(): String {
       return mFirebaseAuth.currentUser?.uid.toString()
    }
}