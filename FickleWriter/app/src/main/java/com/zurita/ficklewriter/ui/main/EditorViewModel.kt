package com.zurita.ficklewriter.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EditorViewModel : ViewModel() {

    // Backing fields used internally
    private val _editModeEnabled = MutableLiveData(false)

    /**
     *  The view can be either in edit mode or normal mode.
     *  A value of true means edit mode. In edit mode the user
     *  can change the title, description, text, etc.
     */
    val editModeEnabled = _editModeEnabled

    /**
     * Toggle between edit mode and normal mode. This will trigger
     * a live data update.
     */
    fun toggleEditMode() {
        _editModeEnabled.postValue(!_editModeEnabled.value!!)
    }
}