package com.zurita.ficklewriter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zurita.ficklewriter.ui.main.EditorFragment

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
}