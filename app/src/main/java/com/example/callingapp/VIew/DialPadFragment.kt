package com.example.callingapp.VIew

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.callingapp.R
import com.example.callingapp.databinding.FragmentDialPadBinding
import com.example.callingapp.viewModel.CallViewModel


class DialPadFragment : Fragment() {

    private lateinit var binding: FragmentDialPadBinding
    private val viewModel: CallViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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
            viewModel.startCall(binding.tvNumber.text.toString())
        }
    }



}