package com.example.callingapp.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.callingapp.R
import com.example.callingapp.databinding.LayoutIncomingCallBinding
import com.example.callingapp.viewModel.CallViewModel

class IncomingCallDialogFragment : DialogFragment() {

    private var _binding: LayoutIncomingCallBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CallViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setStyle(STYLE_NO_TITLE, R.style.FullScreenDialog)
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LayoutIncomingCallBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.phoneNumber.observe(viewLifecycleOwner) {
            binding.tvNumber.text = it
        }

        binding.btnAccept.setOnClickListener {
            viewModel.acceptCall()
            dismiss()
        }

        binding.btnReject.setOnClickListener {
            viewModel.rejectCall()
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}