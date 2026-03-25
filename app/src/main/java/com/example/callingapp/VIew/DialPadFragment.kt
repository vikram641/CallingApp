package com.example.callingapp.VIew

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.example.callingapp.databinding.FragmentDialPadBinding
import com.example.callingapp.viewModel.CallViewModel

class DialPadFragment : Fragment() {

    private lateinit var binding: FragmentDialPadBinding
    private val viewModel: CallViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialPadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val buttons = listOf(
            binding.btn1, binding.btn2, binding.btn3,
            binding.btn4, binding.btn5, binding.btn6,
            binding.btn7, binding.btn8, binding.btn9,
            binding.btnStar, binding.btn0, binding.btnHash
        )

        buttons.forEach { btn ->
            btn.setOnClickListener {
                binding.tvNumber.append(btn.text)
            }
        }

        binding.btnDelete.setOnClickListener {
            val text = binding.tvNumber.text.toString()
            if (text.isNotEmpty()) {
                binding.tvNumber.text = text.dropLast(1)
            }
        }

        binding.btnCall.setOnClickListener {
            val number = binding.tvNumber.text.toString()

            if (number.isNotEmpty()) {
                viewModel.setPhoneNumber(number)
                makeCall(number)
            }
        }
    }

    private val REQUEST_CALL = 101

    private fun makeCall(number: String) {
        val context = requireContext()

        if (number.isBlank()) {
            Toast.makeText(context, "Enter a valid number", Toast.LENGTH_SHORT).show()
            return
        }

        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CALL_PHONE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            try {
                val intent = Intent(Intent.ACTION_CALL).apply {
                    data = Uri.parse("tel:$number")
                }
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(context, "Call failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }

        } else {
            // Ask permission
            requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), REQUEST_CALL)
        }
    }
}