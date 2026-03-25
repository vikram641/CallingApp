package com.example.callingapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.callingapp.VIew.DialPadFragment
import com.example.callingapp.viewModel.CallViewModel
import android.Manifest
import android.content.IntentFilter
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager
import android.telephony.TelephonyManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.callingapp.Utlity.CallReceiver
import com.example.callingapp.VIew.ContactsFragment


class MainActivity : AppCompatActivity() {
    private lateinit var callReceiver: CallReceiver

    private lateinit var callViewModel: CallViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        requestPermissions()

//        // Load Dialer as default screen
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.navHostFragment, ContactsFragment())
//            .commit()

        callViewModel = ViewModelProvider(this)[CallViewModel::class.java]

        callReceiver = CallReceiver(callViewModel)

        val filter = IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED)
        registerReceiver(callReceiver, filter)


    }

    private val REQUEST_ALL_PERMISSIONS = 101

    private fun requestPermissions() {
        val permissions = arrayOf(
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.READ_CONTACTS
        )

        val notGranted = permissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        if (notGranted.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                notGranted.toTypedArray(),
                REQUEST_ALL_PERMISSIONS
            )
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_ALL_PERMISSIONS) {

            val denied = grantResults.any { it != PackageManager.PERMISSION_GRANTED }

            if (denied) {
                // Some permission denied
                Toast.makeText(
                    this,
                    "Some permissions are required for full functionality",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                // All granted
                Toast.makeText(
                    this,
                    "Permissions granted",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
