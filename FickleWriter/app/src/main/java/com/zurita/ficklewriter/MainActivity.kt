package com.zurita.ficklewriter

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.zurita.ficklewriter.ui.main.EditorFragment
import com.zurita.ficklewriter.ui.main.EditorViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, EditorFragment.newInstance())
                .commitNow()
        }

        // The first activity to call the view model will
        // create it. All subsequent calls from activities/fragments
        // will use the same model.
    }

    /**
     * Called when a menu item from the toolbar is selected.
     */
    fun onMenuItemSelected(item: MenuItem) {
        val model by viewModels<EditorViewModel>()
        when (item.itemId) {
            R.id.edit_title -> model.toggleEditMode()
        }
    }
}