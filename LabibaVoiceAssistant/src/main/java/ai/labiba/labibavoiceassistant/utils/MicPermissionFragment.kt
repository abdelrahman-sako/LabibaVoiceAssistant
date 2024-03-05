package ai.labiba.labibavoiceassistant.utils

import ai.labiba.labibavoiceassistant.R
import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class MicPermissionFragment : Fragment() {


    // Register the permissions callback, which handles the user's response to the
    // system permissions dialog. Save the return value, an instance of
    // ActivityResultLauncher. You can use either a val, as shown in this snippet,
    // or a lateinit var in your onAttach() or onCreate() method.
    val requestPermissionLauncher =
        this.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted. Continue the action or workflow in your
                // app.
                microphonePermissionCallback?.invoke(true)
                removeFragment()
            } else {
                // Explain to the user that the feature is unavailable because the
                // feature requires a permission that the user has denied. At the
                // same time, respect the user's decision. Don't link to system
                // settings in an effort to convince the user to change their
                // decision.

                Toast.makeText(
                    this.requireContext(),
                    R.string.microphone_permission_warning,
                    Toast.LENGTH_SHORT
                ).show()
                microphonePermissionCallback?.invoke(false)
                removeFragment()
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        checkMicrophonePermission()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ProgressBar(inflater.context)

    }

    private fun checkMicrophonePermission() {
        if (ContextCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (this.shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO)) {
                showPopUp(requireActivity())
            } else {
                requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
            }
        } else {
            microphonePermissionCallback?.invoke(true)
            removeFragment()
        }
    }

    private fun showPopUp(activity: Activity) {
        AlertDialog.Builder(this.requireContext())
            .setTitle(getString(R.string.microphone_permission))
            .setMessage(getString(R.string.microphone_permission_rationale))
            .setPositiveButton(android.R.string.ok,
                DialogInterface.OnClickListener { dialog, which ->
                    requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                })
            .setNegativeButton(android.R.string.cancel,
                DialogInterface.OnClickListener { dialog, which ->
                    Toast.makeText(
                        activity,
                        getString(R.string.microphone_permission_warning),
                        Toast.LENGTH_SHORT
                    ).show()
                    microphonePermissionCallback?.invoke(false)
                    removeFragment()
                })
            .show()
    }

    private fun removeFragment(){
        parentFragmentManager.beginTransaction().remove(this).commit()
    }



    companion object{
        private val FRAGMENT_TAG="microphone_request_fragment"
        private var microphonePermissionCallback :((Boolean) -> Unit)? = null


        fun setupMicrophonePermissionCallback(callback:(Boolean) -> Unit){
            microphonePermissionCallback = callback
        }

        /**
         *Call this to start mic permission request
         */
        fun requestMicrophonePermission(supportFragmentManager: FragmentManager){

            supportFragmentManager.apply {
                beginTransaction().add(
                    MicPermissionFragment(),
                    FRAGMENT_TAG
                ).commit()
            }
        }



    }
}