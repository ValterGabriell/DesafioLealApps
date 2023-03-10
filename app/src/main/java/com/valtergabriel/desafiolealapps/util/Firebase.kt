package com.valtergabriel.desafiolealapps.util

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

object Firebase {

    fun getAuth() = FirebaseAuth.getInstance()
    fun getFirestore() = FirebaseFirestore.getInstance()
    fun getStorage() = FirebaseStorage.getInstance()


}