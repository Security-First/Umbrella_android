package org.secfirst.umbrella.whitelabel.feature.reader.view.feed

import android.content.Context
import android.location.Geocoder
import android.support.v7.widget.AppCompatAutoCompleteTextView
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.feed_location_dialog.view.*
import org.secfirst.umbrella.whitelabel.feature.reader.interactor.ReaderBaseInteractor
import org.secfirst.umbrella.whitelabel.feature.reader.presenter.ReaderBasePresenter
import org.secfirst.umbrella.whitelabel.feature.reader.view.ReaderView
import java.util.*

class FeedLocationAutoText(private val autocompleteLocation: AppCompatAutoCompleteTextView,
                           private val context: Context,
                           private val presenter: ReaderBasePresenter<ReaderView, ReaderBaseInteractor>) : ReaderView {

    private var geocoder: Geocoder

    init {
        startAutocompleteLocation()
        geocoder = Geocoder(context, Locale.getDefault())
    }

    private fun startAutocompleteLocation() {
        val test = listOf("Apple", "Banana", "Pear", "Mango", "Lemon")
        val adapter = ArrayAdapter<String>(context, android.R.layout.select_dialog_item, test)
        autocompleteLocation.autocompleteLocation.threshold = 3
        autocompleteLocation.autocompleteLocation.setAdapter(adapter)
        autocompleteLocation.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (count == 3)
                    presenter.submitAutocompleteAddress(s.toString())
            }
        })
    }

    override fun newAddressAvailable(address: List<String>) {
        val adapter = ArrayAdapter<String>(context, android.R.layout.select_dialog_item, address)
        autocompleteLocation.setAdapter(adapter)
    }
}