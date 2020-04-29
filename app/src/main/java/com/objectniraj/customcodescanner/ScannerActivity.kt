package com.objectniraj.customcodescanner

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import kotlinx.android.synthetic.main.content_scanner.*

class ScannerActivity : AppCompatActivity(), DecoratedBarcodeView.TorchListener {


    private var isFlashOn = false
    private var capture: CaptureManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Hiding the notch display
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_scanner)

        //For smooth enter/exit animation
        this.overridePendingTransition(
            android.R.anim.fade_in,
            android.R.anim.fade_out
        )

        //Setting torch listener
        barcodeScanner.setTorchListener(this)

        flash.setOnClickListener {
            isFlashOn = if (isFlashOn) {
                barcodeScanner.setTorchOff()
                false
            } else {
                barcodeScanner.setTorchOn()
                true
            }
        }
        //initiate scan
        capture = CaptureManager(this, barcodeScanner)
        capture!!.initializeFromIntent(intent, savedInstanceState)
        capture!!.decode()
    }

    override fun onPause() {
        super.onPause()
        capture!!.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        capture!!.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        capture!!.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        capture!!.onSaveInstanceState(outState)
    }

    override fun onTorchOn() {
        flash.setImageResource(R.drawable.ic_flash_on)
    }

    override fun onTorchOff() {
        flash.setImageResource(R.drawable.ic_flash_off)
    }

}
