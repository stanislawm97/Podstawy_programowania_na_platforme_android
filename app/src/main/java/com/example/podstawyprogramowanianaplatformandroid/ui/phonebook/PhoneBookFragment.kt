package com.example.podstawyprogramowanianaplatformandroid.ui.phonebook

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.podstawyprogramowanianaplatformandroid.R
import com.example.podstawyprogramowanianaplatformandroid.database.entity.PhoneBookEntity
import kotlinx.android.synthetic.main.fragment_phone_book.*


class PhoneBookFragment : Fragment(R.layout.fragment_phone_book), SearchView.OnQueryTextListener {

    private lateinit var phoneBookViewModel: PhoneBookViewModel
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        phoneBookViewModel = ViewModelProvider(this).get(PhoneBookViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        phoneBookViewModel.contacts.observe(viewLifecycleOwner, Observer {
            handleContacts(it)
        })

        phoneBookViewModel.getAllReminder(requireContext())

        addOnScrollFabHider()
        addOnClickFabAction()
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
        if (newText.isEmpty() || newText.isBlank()) {
            (rv_phone_book.adapter as PhoneBookAdapter).clearSearch()
        } else {
            (rv_phone_book.adapter as PhoneBookAdapter).search(newText)
        }
        return true
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        return true
    }

    private fun handleContacts(contacts: List<PhoneBookEntity>) {
        rv_phone_book.adapter = PhoneBookAdapter().apply {
            setOnListChangedCallback {
                if (it.isEmpty()) {
                    lav_phone_book.animate().alpha(1.0f).setDuration(250L).withStartAction {
                        lav_phone_book.visibility = View.VISIBLE
                    }.start()
                } else {
                    lav_phone_book.animate().alpha(0.0f).setDuration(450L).withEndAction {
                        lav_phone_book.visibility = View.GONE
                    }.start()
                }
                fab_add_note.show()
            }

            setOnItemLongClickListener {
                findNavController().navigate(
                    R.id.action_nav_phone_book_to_nav_phone_book_details,
                    bundleOf("PhoneBook" to it.id)
                )
            }

            submitItems(contacts)
        }

    }

    private fun addOnClickFabAction() {
        fab_add_note.setOnClickListener {
            findNavController().navigate(R.id.action_nav_phone_book_to_nav_new_phone_book)
        }
    }

    private fun addOnScrollFabHider() {
        rv_phone_book.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) fab_add_note.hide() else if (dy < 0) fab_add_note.show()
            }
        })
    }
}