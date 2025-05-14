package ai.labiba.labibavoiceassistant.ui.dialogs.moreDialog

import ai.labiba.labibavoiceassistant.R
import ai.labiba.labibavoiceassistant.adapter.MoreAdapter
import ai.labiba.labibavoiceassistant.databinding.DialogMoreBinding
import ai.labiba.labibavoiceassistant.models.MoreEnum
import ai.labiba.labibavoiceassistant.models.MoreModel
import ai.labiba.labibavoiceassistant.sdkSetupClasses.LabibaVAInternal
import ai.labiba.labibavoiceassistant.ui.dialogs.reportDialog.ReportDialog
import ai.labiba.labibavoiceassistant.utils.dialogs.CustomBottomSheetDialogFragment
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

class MoreDialog : CustomBottomSheetDialogFragment(), MoreAdapter.MoreAdapterCallbackInterface {

    private var _binding: DialogMoreBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy { MoreAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogMoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupRecyclerView()
        setupListeners()

    }

    private fun setupUI() {

        binding.contentContainerConstraintLayout.backgroundTintList = ColorStateList.valueOf(
            Color.parseColor(
                LabibaVAInternal.labibaVaTheme.moreDialog.backgroundColor
            )
        )

    }

    private fun setupRecyclerView() {
        //recyclerView
        binding.moreRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter.setAdapterInterface(this)
        binding.moreRecyclerView.adapter = adapter


        //items
        adapter.submitList(
            listOf(
                MoreModel(MoreEnum.REPORT.id, R.drawable.ic_flag, getString(R.string.report))
            )
        )


    }

    private fun setupListeners() {


    }

    override fun onMoreItemClick(item: MoreModel) {
        when (item.id) {
            MoreEnum.REPORT.id -> {
                dismiss()
                ReportDialog.newInstance(arguments?.getString(BOT_MESSAGE_KEY))
                    .show(requireActivity().supportFragmentManager, "ReportDialog")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        private const val BOT_MESSAGE_KEY = "BOT_MESSAGE_KEY"

        fun newInstance(message: String?): MoreDialog {
            val args = Bundle()
            args.putString(BOT_MESSAGE_KEY, message)
            val fragment = MoreDialog()
            fragment.arguments = args
            return fragment
        }
    }

}