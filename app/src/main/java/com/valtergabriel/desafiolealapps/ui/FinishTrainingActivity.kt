package com.valtergabriel.desafiolealapps.ui

import android.Manifest
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.valtergabriel.desafiolealapps.R
import com.valtergabriel.desafiolealapps.databinding.ActivityFinishTrainingBinding
import com.valtergabriel.desafiolealapps.util.Constants
import com.valtergabriel.desafiolealapps.viewmodel.TrainingViewModel
import org.koin.android.ext.android.inject
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class FinishTrainingActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private lateinit var binding: ActivityFinishTrainingBinding
    private val trainingViewModel by inject<TrainingViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish_training)
        binding = ActivityFinishTrainingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val trainingName = intent.extras?.get(Constants.STATIC_TITLE).toString()
        val isJustSee = intent.extras?.get(Constants.JUST_WANNA_SEE) as Boolean

        if (isJustSee) {
            trainingViewModel.retriveImages(trainingName, binding.imgBefore, binding.imgAfter, this)
            binding.txtNameTraining.text = trainingName
            binding.btnSaveImages.visibility = View.GONE
        }


        binding.txtNameTraining.text = trainingName
        saveImages(trainingName)

    }


    private fun saveImages(trainingName: String) {
        var uriBefore: Uri? = null
        var uriAfter: Uri? = null

        /**
         * Contratos para acessar a galeria do celular
         */
        val getImageBefore =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                binding.imgBefore.setImageURI(uri)
                uriBefore = uri
            }

        val getImageAfter =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                binding.imgAfter.setImageURI(uri)
                uriAfter = uri
            }

        /**
         * Setando metodos de clique para as duas imagens e solicitando as permissoes caso ainda nao tenha
         */

        binding.imgBefore.setOnClickListener {
            val hasPermissions = methodRequireCameraPermission()
            if (hasPermissions) {
                getImageBefore.launch("image/*")
            }

        }

        binding.imgAfter.setOnClickListener {
            val hasPermissions = methodRequireCameraPermission()
            if (hasPermissions) {
                getImageAfter.launch("image/*")
            }

        }

        /**
         * Salvando as fotos no storage
         */
        binding.btnSaveImages.setOnClickListener {
            binding.progressBar4.visibility = View.VISIBLE
            it.visibility = View.GONE
            trainingViewModel.finishTraining(trainingName, uriBefore!!, uriAfter!!, this)
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


}