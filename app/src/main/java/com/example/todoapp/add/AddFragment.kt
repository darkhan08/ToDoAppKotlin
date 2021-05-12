package com.example.todoapp.add

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.database.Note
import com.example.todoapp.databinding.FragmentAddBinding


class AddFragment : Fragment() {
    private lateinit var binding:FragmentAddBinding
    private lateinit var viewModel: AddViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentAddBinding.inflate(inflater)
        binding.setLifecycleOwner(this)
        viewModel = ViewModelProvider(this).get(AddViewModel::class.java)
        binding.viewModel = viewModel
        findNavController()
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun insertDataToDatabase(){
        val title = binding.titleText.text.toString()
        val text = binding.noteText.text.toString()
        if(inputCheck(title,text)){
            val note = Note(0,title,text)
            viewModel.addNote(note)
        }
            findNavController().navigate(R.id.action_addFragment_to_homeFragment)

        val inputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken,0)
    }
    private fun inputCheck(title:String,text:String): Boolean {
       return (text.filter { !it.isWhitespace() }.length>0 ||title.filter { !it.isWhitespace() }.length>0)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.my_menu,menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.add->insertDataToDatabase()
        }
        return super.onOptionsItemSelected(item)
    }
}