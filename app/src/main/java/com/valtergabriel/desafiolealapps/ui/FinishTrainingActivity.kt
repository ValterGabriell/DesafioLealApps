package com.valtergabriel.desafiolealapps.ui

import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.valtergabriel.desafiolealapps.R
import com.valtergabriel.desafiolealapps.databinding.ActivityFinishTrainingBinding
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class FinishTrainingActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private lateinit var binding: ActivityFinishTrainingBinding
    private lateinit var imgBefore: Bitmap
    private lateinit var imgAfter: Bitmap

    /**
     * Contratos para abrir a galeria
     */
    private val getImageBefore =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            val inputStr = contentResolver.openInputStream(uri!!)
            val image = BitmapFactory.decodeStream(inputStr)
            binding.imgBefore.setImageBitmap(image)
            imgBefore = image
        }

    private val getImageAfter =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            val inputStr = contentResolver.openInputStream(uri!!)
            val image = BitmapFactory.decodeStream(inputStr)
            binding.imgAfter.setImageBitmap(image)
            imgAfter = image
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish_training)

        binding = ActivityFinishTrainingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBefore.setOnClickListener {
            val hasPermissions = methodRequireCameraPermission()
            if (hasPermissions) {
                setGalleryLaunchBefore()
            }
        }

        binding.imgAfter.setOnClickListener {
            val hasPermissions = methodRequireCameraPermission()
            if (hasPermissions) {
                setGalleryLaunchAfter()
            }
        }

    }

    private fun methodRequireCameraPermission(): Boolean {
        val perms = Manifest.permission.CAMERA
        return if (EasyPermissions.hasPermissions(this, perms)) {
            true
        } else {
            EasyPermissions.requestPermissions(this, "rationale", 0, perms)
            false
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Toast.makeText(this@FinishTrainingActivity, "Permissoes garantidas", Toast.LENGTH_SHORT)
            .show()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    private fun setGalleryLaunchBefore() {
        getImageBefore.launch("image/*")
    }

    private fun setGalleryLaunchAfter() {
        getImageAfter.launch("image/*")
    }
}