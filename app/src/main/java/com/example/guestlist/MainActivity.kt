package com.example.guestlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

const val LAST_GUEST_NAME_KEY = "last-guest-name-bundle-key"

class MainActivity : AppCompatActivity() {
    // initialize our mutable list to store guest names.
//    var guestNames: MutableList<String> = mutableListOf()
    val guestListViewModel: GuestListViewModel by lazy {
        ViewModelProvider(this).get(GuestListViewModel::class.java)
    }

    lateinit var addGuestButton: Button
    lateinit var clearGuestListButton: Button
    lateinit var newGuestEditText: EditText
    lateinit var guestList: TextView
    lateinit var lastGuestAdded: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addGuestButton = findViewById(R.id.add_guest_button)
        clearGuestListButton = findViewById(R.id.clear_list_button)
        newGuestEditText = findViewById(R.id.new_guest_input)
        guestList = findViewById(R.id.list_of_guests)
        lastGuestAdded = findViewById(R.id.last_guest_added)

        addGuestButton.setOnClickListener {
            addNewGuest()
        }

        clearGuestListButton.setOnClickListener {
            guestListViewModel.clearGuestList()
            updateGuestList()
        }

        val savedLastGuestMessage = savedInstanceState?.getString(LAST_GUEST_NAME_KEY)
        lastGuestAdded.text = savedLastGuestMessage

        updateGuestList()

    }

    override fun onSaveInstanceState(outState: Bundle){
        super.onSaveInstanceState(outState)
        outState.putString(LAST_GUEST_NAME_KEY, lastGuestAdded.text.toString())
    }

    private fun addNewGuest() {
        val guestToAdd = newGuestEditText.text.toString()
        if (guestToAdd.isNotBlank()) {
//            guestNames.add(guestToAdd)
            guestListViewModel.addGuest(guestToAdd)
            updateGuestList()
            newGuestEditText.text.clear()
            lastGuestAdded.text = getString(R.string.last_guest_message, guestToAdd)
        }
    }

    private fun updateGuestList() {
        val guestDisplay = guestListViewModel.GetSortedGuestNames().joinToString(separator = "\n")
        guestList.text = guestDisplay
    }
}