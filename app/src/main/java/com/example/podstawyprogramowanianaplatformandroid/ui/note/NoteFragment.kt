package com.example.podstawyprogramowanianaplatformandroid.ui.note

import android.app.SearchManager
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.podstawyprogramowanianaplatformandroid.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_note.*
import java.io.File
import java.io.FileInputStream


class NoteFragment : Fragment(R.layout.fragment_note), SearchView.OnQueryTextListener {

    private lateinit var noteViewModel: NoteViewModel

    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchView = searchItem.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.setOnQueryTextListener(this)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onQueryTextChange(newText: String): Boolean {
        if(newText.isEmpty() || newText.isBlank()) {
            (rv_notes.adapter as NotesAdapter).clearSearch()
        } else {
            (rv_notes.adapter as NotesAdapter).search(newText)
        }
        return true
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val notes = ArrayList<NoteItem>()
        val path: File = requireContext().filesDir

        path.list()?.forEach {
            val file = File(path, it)
            val description = String(FileInputStream(file).readBytes())
            notes.add(NoteItem(it.split(".")[0], description))
        }

        val fab = view.findViewById<FloatingActionButton>(R.id.fab_add_note)
        fab.setOnClickListener {
            findNavController().navigate(R.id.action_nav_note_to_nav_new_note)
        }

        val notesAdapter = NotesAdapter()

        rv_notes.adapter = notesAdapter

        notesAdapter.apply {
            setOnListChangedCallback {
                if (it.isEmpty()) {
                    lav_note.animate().alpha(1.0f).setDuration(250L).withStartAction {
                        lav_note.visibility = View.VISIBLE
                    }.start()
                } else {
                    lav_note.animate().alpha(0.0f).setDuration(450L).withEndAction {
                        lav_note.visibility = View.GONE
                    }.start()
                }
            }

            setOnDeleteCallbackAction {
                if (File(requireContext().filesDir, "$it.txt").delete()) {
                    Toast.makeText(
                        requireActivity(),
                        getString(R.string.note) + "\"$it\"" + getString(R.string.was_deleted),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        requireActivity(),
                        getString(R.string.desc_error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            submitList(notes)
        }

        ItemTouchHelper(
            NoteAdapterSwipeToDeleteCallback(
                requireNotNull(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_baseline_delete_forever_24,
                        null
                    )
                ), ColorDrawable(Color.RED), notesAdapter
            )
        ).apply { attachToRecyclerView(rv_notes) }
    }
}