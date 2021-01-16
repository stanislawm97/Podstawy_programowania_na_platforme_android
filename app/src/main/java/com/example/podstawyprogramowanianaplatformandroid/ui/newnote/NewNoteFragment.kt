package com.example.podstawyprogramowanianaplatformandroid.ui.newnote

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.podstawyprogramowanianaplatformandroid.R
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_new_note.*
import java.io.File
import java.io.FileOutputStream

class NewNoteFragment : Fragment(R.layout.fragment_new_note) {

    private lateinit var viewModel: NewNoteViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewNoteViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (til_title as TextInputLayout).editText?.apply {
            setText(" ")
            addTextChangedListener {
                if(it.isNullOrEmpty())
                    setText(" ")
            }
        }

        (til_description as TextInputLayout).editText?.apply {
            setText(" ")
            addTextChangedListener {
                if(it.isNullOrEmpty())
                    setText(" ")
            }
        }


        bt_save.setOnClickListener {
            val title = (til_title as TextInputLayout).editText?.text.toString().trim()
            var description = (til_description as TextInputLayout).editText?.text.toString()

            if (title.isEmpty() || title.isBlank() || title.contains('.')) {
                Toast.makeText(
                    requireActivity(),
                    getString(R.string.wrong_note_title),
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                if (description.isEmpty()) description = " "

                val path: File = requireContext().filesDir

                if (path.list()?.contains("$title.txt") == false) {
                    val file = File(path, "$title.txt")
                    FileOutputStream(file).use { stream ->
                        stream.write(description.toByteArray())
                    }
                    Toast.makeText(
                        requireActivity(),
                        getString(R.string.save_note) + " \"$title\"",
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().navigate(R.id.action_nav_new_note_to_nav_note)
                } else {
                    Toast.makeText(
                        requireActivity(),
                        getString(R.string.note_exist),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}