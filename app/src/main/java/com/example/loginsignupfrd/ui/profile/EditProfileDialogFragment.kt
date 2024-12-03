package com.example.loginsignupfrd.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.loginsignupfrd.R

class EditProfileDialogFragment : DialogFragment() {

    interface EditProfileListener {
        fun onProfileEdited(
            profileImageUrl: String,
            backgroundImageUrl: String,
            name: String,
            status: String,
            profession: String,
            phoneNumber: String,
            origin: String
        )
    }

    private lateinit var listener: EditProfileListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_edit_profile, container, false)

        val etProfileImageUrl: EditText = view.findViewById(R.id.et_profile_image_url)
        val etBackgroundImageUrl: EditText = view.findViewById(R.id.et_background_image_url)
        val etName: EditText = view.findViewById(R.id.et_name)
        val etStatus: EditText = view.findViewById(R.id.et_status)
        val etProfession: EditText = view.findViewById(R.id.et_profession)
        val etPhoneNumber: EditText = view.findViewById(R.id.et_phone_number)
        val etOrigin: EditText = view.findViewById(R.id.et_origin)
        val btnSave: Button = view.findViewById(R.id.btn_save)

        btnSave.setOnClickListener {
            val profileImageUrl = etProfileImageUrl.text.toString()
            val backgroundImageUrl = etBackgroundImageUrl.text.toString()
            val name = etName.text.toString()
            val status = etStatus.text.toString()
            val profession = etProfession.text.toString()
            val phoneNumber = etPhoneNumber.text.toString()
            val origin = etOrigin.text.toString()

            listener.onProfileEdited(
                profileImageUrl,
                backgroundImageUrl,
                name,
                status,
                profession,
                phoneNumber,
                origin
            )
            dismiss()
        }

        return view
    }

    fun setEditProfileListener(listener: EditProfileListener) {
        this.listener = listener
    }
}