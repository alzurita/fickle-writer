package com.zurita.ficklewriter.ui.main

import android.os.Bundle
import android.text.method.KeyListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.zurita.ficklewriter.R
import com.zurita.ficklewriter.databinding.EditorFragmentBinding

class EditorFragment : Fragment() {

   companion object {
      fun newInstance() = EditorFragment()
   }

   private var _binding: EditorFragmentBinding? = null

   /**
    * This property is only valid between onCreateView and
    * onDestroyView.
    */
   private val binding get() = _binding!!

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View {
      _binding = EditorFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.toolbar.inflateMenu(R.menu.toolbar)

      val model by activityViewModels<EditorViewModel>()

      // Save the KeyListener by putting it into the tag of its textbox,
      // then continue to set it based on the model. Setting the KeyListener
      // to null will make the textbox not editable
      binding.editor.textbox.tag = binding.editor.textbox.keyListener
      model.editModeEnabled.observe(this, { editModeEnabled ->
         binding.editor.textbox.keyListener =
            if (editModeEnabled) binding.editor.textbox.tag as KeyListener
            else null
      })
   }
}