package com.valtergabriel.desafiolealapps.util

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object PasswordEcrypt {
    private const val HASH_ALGORITHM = "SHA-256"

    fun encrypt(password: String): String {
        return try {
            val messageDigest = MessageDigest.getInstance(HASH_ALGORITHM)
            val hash = messageDigest.digest(password.toByteArray())
            val stringBuilder = StringBuilder()
            for (b in hash) {
                stringBuilder.append(String.format("%02x", b))
            }
            stringBuilder.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            ""
        }
    }
}