package ai.labiba.labibavoiceassistant.ui.dialogs.reportDialog

import ai.labiba.labibavoiceassistant.R
import ai.labiba.labibavoiceassistant.databinding.DialogReportBinding
import ai.labiba.labibavoiceassistant.utils.apiHandleResponse.Resource
import ai.labiba.labibavoiceassistant.utils.dialogs.CustomBottomSheetDialogFragment
import ai.labiba.labibavoiceassistant.utils.ext.fadeInToVisible
import ai.labiba.labibavoiceassistant.utils.ext.gone
import ai.labiba.labibavoiceassistant.utils.ext.logd
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.transition.TransitionManager
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class ReportDialog : CustomBottomSheetDialogFragment() {

    private var _binding: DialogReportBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ReportDialogViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setupObservers()

    }

    private fun setupListeners() {
        val botResponse = arguments?.getString(BOT_MESSAGE_KEY)

        if (botResponse != null) {
            binding.botResponseTextView.text = arguments?.getString(BOT_MESSAGE_KEY)
        } else {
            binding.botResponseCardView.gone()
            binding.questionTextView.gone()
        }

        binding.reasonEditText.doAfterTextChanged {
            binding.reasonTextInputLayout.error = null
        }

        binding.submitMtrlButton.setOnClickListener {
            if(binding.reasonEditText.text.isNullOrEmpty()){

                //animate dialog
                val viewG= dialog?.findViewById<ViewGroup>(com.google.android.material.R.id.design_bottom_sheet)
                if (viewG != null) {
                   TransitionManager.beginDelayedTransition(viewG)
                }

                binding.reasonTextInputLayout.error = getString(R.string.required)
            }else{
                viewModel.submitReport(
                    binding.reasonEditText.text.toString(), arguments?.getString(
                        BOT_MESSAGE_KEY
                    )
                )
            }
        }

    }

    private fun setupObservers() {

        viewModel.reportResult.observe(viewLifecycleOwner) {
            if (it != null) {
                when (it) {
                    is Resource.Loading -> {
                        binding.progressBar.fadeInToVisible()
                    }

                    is Resource.Success -> {
                        binding.progressBar.gone()

                        showSnackBar(getString(R.string.report_submitted_successfully)){
                            dismiss()
                        }


                    }

                    is Resource.Error -> {
                        binding.progressBar.gone()
                        showSnackBar(getString(R.string.something_went_wrong_please_try_again_later))
                        it.message?.logd("error while submitting report")


                    }
                }
            }
        }

    }

    private fun showSnackBar(message: String, onDismiss: (() -> Unit)? = null) {

        try {
            val snackBar = Snackbar.make(
                dialog?.window?.decorView ?: binding.root,
                message,
                Snackbar.LENGTH_SHORT
            ).apply {
                setAction(android.R.string.ok){
                    onDismiss?.invoke()
                    this@apply.dismiss()
                }

                addCallback(object : Snackbar.Callback(){
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        super.onDismissed(transientBottomBar, event)
                        onDismiss?.invoke()
                    }
                })

            }
            snackBar.anchorView = binding.snackBarAnchor
            snackBar.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val BOT_MESSAGE_KEY = "BOT_MESSAGE_KEY"
        fun newInstance(botMessage: String?): ReportDialog {
            val args = Bundle()
            args.putString(BOT_MESSAGE_KEY, botMessage)
            val fragment = ReportDialog()
            fragment.arguments = args
            return fragment
        }

    }
}