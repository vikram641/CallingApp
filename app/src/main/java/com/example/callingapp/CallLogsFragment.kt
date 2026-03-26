package com.example.callingapp.VIew

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.CallLog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.callingapp.R
import com.example.callingapp.databinding.FragmentCallLogsBinding
import com.example.callingapp.model.CallLogItem
import java.text.SimpleDateFormat
import java.util.*

class CallLogsFragment : Fragment() {

    private lateinit var binding: FragmentCallLogsBinding
    private val list = mutableListOf<CallLogItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCallLogsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.rvCallLogs.layoutManager = LinearLayoutManager(requireContext())

        // Navigation
        binding.btnDial.setOnClickListener {
            findNavController().navigate(R.id.action_callLogsFragment_to_dialPadFragment)
        }

        binding.btnContacts.setOnClickListener {
            findNavController().navigate(R.id.action_callLogsFragment_to_contactFragment)
        }

        checkPermissionAndLoad()
    }


    private fun checkPermissionAndLoad() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_CALL_LOG
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            loadCallLogs()
        } else {
            requestPermissions(arrayOf(Manifest.permission.READ_CALL_LOG), 200)
        }
    }

    // ✅ Handle permission result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 200) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadCallLogs()
            } else {
                Toast.makeText(requireContext(), "Permission required", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun loadCallLogs() {

        list.clear() // 🔥 VERY IMPORTANT

        val cursor = requireContext().contentResolver.query(
            CallLog.Calls.CONTENT_URI,
            null,
            null,
            null,
            "${CallLog.Calls.DATE} DESC"
        )

        if (cursor == null) {
            Toast.makeText(requireContext(), "No call logs found", Toast.LENGTH_SHORT).show()
            return
        }

        cursor.use {
            while (it.moveToNext()) {

                val name = it.getString(
                    it.getColumnIndexOrThrow(CallLog.Calls.CACHED_NAME)
                )

                val number = it.getString(
                    it.getColumnIndexOrThrow(CallLog.Calls.NUMBER)
                )

                val typeCode = it.getInt(
                    it.getColumnIndexOrThrow(CallLog.Calls.TYPE)
                )

                val dateMillis = it.getLong(
                    it.getColumnIndexOrThrow(CallLog.Calls.DATE)
                )

                val durationSec = it.getString(
                    it.getColumnIndexOrThrow(CallLog.Calls.DURATION)
                )

                val type = when (typeCode) {
                    CallLog.Calls.INCOMING_TYPE -> "Incoming"
                    CallLog.Calls.OUTGOING_TYPE -> "Outgoing"
                    CallLog.Calls.MISSED_TYPE -> "Missed"
                    else -> "Other"
                }

                val date = SimpleDateFormat(
                    "dd MMM, hh:mm a",
                    Locale.getDefault()
                ).format(Date(dateMillis))

                val duration = "$durationSec sec"

                list.add(
                    CallLogItem(
                        name,
                        number,
                        type,
                        date,
                        duration
                    )
                )
            }
        }

        binding.rvCallLogs.adapter = CallLogAdapter(list) {
            makeCall(it.number)
        }
    }

    override fun onResume() {
        super.onResume()
        loadCallLogs()

    }

    // ✅ CALL FUNCTION
    private fun makeCall(number: String) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:$number")
            startActivity(intent)
        } else {
            Toast.makeText(requireContext(), "Call permission required", Toast.LENGTH_SHORT).show()
        }
    }
}