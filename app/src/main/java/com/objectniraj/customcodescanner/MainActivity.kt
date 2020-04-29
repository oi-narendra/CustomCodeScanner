package com.objectniraj.customcodescanner

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startScan.setOnClickListener {
            IntentIntegrator(this)
                .setCaptureActivity(ScannerActivity::class.java)
                .setBeepEnabled(true)
                .setOrientationLocked(true)
                .setBarcodeImageEnabled(true)
                .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
                .setCameraId(0)
                .initiateScan()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        //check for null
        if (result != null) {
            if (result.contents != null) {

                //Scanned Result
                scannedCodeResult.text = result.contents

                //Scanned Code Image
                Glide.with(applicationContext)
                    .load(result.barcodeImagePath)
                    .optionalCenterCrop()
                    .into(scannedCodeImage)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
