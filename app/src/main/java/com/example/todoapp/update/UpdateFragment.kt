package com.example.todoapp.update

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.database.Note
import com.example.todoapp.databinding.FragmentUpdateBinding

class UpdateFragment : Fragment() {
    private lateinit var binding:FragmentUpdateBinding
    private lateinit var viewModel:UpdateViewModel
    private lateinit var args:Note
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentUpdateBinding.inflate(inflater)
        args = UpdateFragmentArgs.fromBundle(requireArguments()!!).noteargs
        val application = requireNotNull(this.activity).application
        val viewModelFactory = ViewModelFactory(application,args)
        viewModel = ViewModelProvider(this,viewModelFactory).get(UpdateViewModel::class.java)
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.delete_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.delete->DeleteNote()
            R.id.safe -> UpdateItem()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun UpdateItem() {
        val title = binding.titleText.text.toString()
        val text = binding.noteText.text.toString()
        if(viewModel.selectedProperty.value?.titleName!=title ||viewModel.selectedProperty.value?.noteText!=text){
            val note = Note(args.noteId,title,text)
            viewModel.update(note)
            view?.hideKeyboard()
            findNavController().navigate(R.id.action_updateFragment_to_homeFragment)
        }

    }

    fun View.hideKeyboard() {
        var inputManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun DeleteNote() {
        viewModel.delete(args)
        findNavController().navigate(R.id.action_updateFragment_to_homeFragment)

    }
}