package com.example.callingapp.VIew

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.callingapp.R
import com.example.callingapp.Utlity.CallTimer
import com.example.callingapp.databinding.FragmentActiveCallBinding
import com.example.callingapp.viewModel.CallViewModel
import kotlin.concurrent.timer


class activeCallFragment : Fragment() {
    private var _binding : FragmentActiveCallBinding? = null
    val binding get() = _binding!!
    private val timer = CallTimer()

    private val callViewModel: CallViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentActiveCallBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        callViewModel.phoneNumber.observe(viewLifecycleOwner) {
            binding.tvNumber.text = it
        }

        timer.start {
            binding.tvTimer.text = it
        }

        binding.btnEnd.setOnClickListener {
            callViewModel.endCall()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer.stop()
    }


}