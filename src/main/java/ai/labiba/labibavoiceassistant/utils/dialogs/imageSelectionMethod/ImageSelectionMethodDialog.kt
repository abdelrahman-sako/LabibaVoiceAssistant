package ai.labiba.labibavoiceassistant.utils.dialogs.imageSelectionMethod

import ai.labiba.labibavoiceassistant.databinding.DialogImageSelectionMethodBinding
import ai.labiba.labibavoiceassistant.utils.dialogs.CustomBottomSheetDialogFragment
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream


class ImageSelectionMethodDialog : CustomBottomSheetDialogFragment() {

    //binding
    private var _binding: DialogImageSelectionMethodBinding? = null
    private val binding get() = _binding!!

    //view Model
    private val viewModel: ImageSelectionMethodViewModel by viewModels()

    //camera - gallery ResultLaunchers
    private lateinit var galleryLauncher: ActivityResultLauncher<String>
    private lateinit var cameraLauncher: ActivityResultLauncher<Uri>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    private lateinit var image: Uri

    private lateinit var file: File


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //initialize provider
        ImageSelectionMethodConstants.init(context)


        //initializing activityResultLauncher outside on create causes an exception if the fragment is recreated
        setupActivityResultLauncher()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogImageSelectionMethodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupCameraImageUri()
        setupListeners()
        setupObservers()


    }

    private fun setupUI(){
        //background color
        binding.dialogImageSelectionMainContainer.background.colorFilter = PorterDuffColorFilter(
            ImageSelectionMethodConstants.backGroundColor,
            PorterDuff.Mode.SRC_IN)

//        binding.dialogImageSelectionCameraBtnContentContainer.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.light_grey_zero))

//        binding.dialogImageSelectionGalleryBtnContentContainer.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.light_grey_zero))



        //button text color
//        binding.dialogImageSelectionCameraBtn.setTextColor(ImageSelectionMethodConstants.cameraButtonTextColor)
//        binding.dialogImageSelectionGalleryBtn.setTextColor(ImageSelectionMethodConstants.galleryButtonTextColor)

        //line color
//        binding.dialogImageSelectionSeparatingLine.background.setTint(ImageSelectionMethodConstants.separatingDividerLineColor)

        //button strings
//        binding.dialogImageSelectionCameraBtn.text = ImageSelectionMethodConstants.cameraString
//        binding.dialogImageSelectionGalleryBtn.text = ImageSelectionMethodConstants.galleryString

    }

    private fun setupCameraImageUri() {

        //initialize file
        file = File.createTempFile("Img_", ".jpeg", requireActivity().cacheDir).also {
            it.deleteOnExit()
        }

        //get file uri
        image = FileProvider.getUriForFile(
            requireActivity(),
            ImageSelectionMethodConstants.fileProviderAuthority,
            file
        )

    }

    private fun setupListeners() {

        //camera button listener
        binding.dialogImageSelectionCameraBtn.setOnClickListener {
            launchCamera()
        }

        //gallery button listener
        binding.dialogImageSelectionGalleryBtn.setOnClickListener {
            galleryLauncher.launch("image/*")
        }

    }

    private fun setupObservers() {
        viewModel.imageName.observe(viewLifecycleOwner) {
            if (it != null) {
                showLoadingDialog(false)
                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        val originalSize = file.length()
                        if (originalSize > ImageSelectionMethodConstants.compressUntilBytes || originalSize == 0L) {
                            val compressedFile = getNeededSizeFile(image)
                            val copySize = compressedFile.length()
                            if (copySize > 10)
                                file = compressedFile


                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    listener?.invoke(Pair(it, file))
                    dismissLoadingDialog()
                    dismiss()
                }
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun getNeededSizeFile(uri: Uri): File {
        var size = 10000000L
        var quality = 100
        var imageBitmap: Bitmap? = null
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            imageBitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)

        } else {
            imageBitmap =
                ImageDecoder.createSource(requireContext().contentResolver, uri).let {
                    ImageDecoder.decodeBitmap(it)
                }
        }

        val compressedFile =
            File.createTempFile("Img_", ".jpeg", requireActivity().cacheDir).also {
                it.deleteOnExit()
            }

        size = compressedFile.length()

        do {
            val stream = FileOutputStream(compressedFile)
            imageBitmap?.compress(Bitmap.CompressFormat.JPEG, quality, stream)
            stream.flush()
            stream.close()

            size = compressedFile.length()
            quality -= 5

            if (quality < 0) {
                quality = 0
            }

        } while (size > ImageSelectionMethodConstants.compressUntilBytes && quality != 0)

        return compressedFile
    }

    private fun setupActivityResultLauncher() {

        //on permission request result
        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {

                if (it) {
                    cameraLauncher.launch(image)
                }

            }

        //on gallery image back
        galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->

            if (uri != null) {
                image = uri
                viewModel.setImageName(uri, requireActivity())
            }
        }

        //on camera image back
        cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) {
            if (it)
                viewModel.setImageName(image.lastPathSegment ?: "")
        }

    }


    private fun launchCamera() {

        //check if permission is granted
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            cameraLauncher.launch(image)

        } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.CAMERA
            )
        ) {  // if permission denied show dialog
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
            builder.setMessage(ImageSelectionMethodConstants.permissionDeniedDialogMessage)
                .setTitle(ImageSelectionMethodConstants.permissionDeniedDialogTitle)
                .setPositiveButton(
                    android.R.string.ok
                ) { _, _ ->
                    val intent = Intent()
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    intent.data = Uri.fromParts("package", ImageSelectionMethodConstants.applicationID, null)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
                .setNegativeButton(
                    android.R.string.cancel
                ) { dialog, _ -> dialog?.dismiss() }
                .create()
                .show()
        } else { //request permission
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private var listener: ((Pair<String, File>) -> Unit)? = null

        fun setupListener(listener: (Pair<String, File>) -> Unit) {
            Companion.listener = listener
        }
    }

}