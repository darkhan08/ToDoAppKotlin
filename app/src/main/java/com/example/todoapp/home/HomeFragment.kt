package com.example.todoapp.home

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentHomeBinding


class HomeFragment : Fragment(), SearchView.OnQueryTextListener {
    private lateinit var viewModel: ViewModel
    private lateinit var adapter: NoteAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
        adapter = NoteAdapter(NoteListner { note ->
            viewModel.onNoteClicked(note)
        })
        binding.recyclerView.adapter = adapter
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel

        viewModel.navigationToUpdate.observe(viewLifecycleOwner, Observer {
            if(it!=null){
                this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToUpdateFragment(it))
                viewModel.onUpdateNavigated()
            }

        })

        viewModel.readAllData.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
        viewModel.navigateToAdd.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAddFragment())
                viewModel.navigatedToAdd()
            }
        })
        val manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerView.layoutManager = manager
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)
        val search = menu?.findItem(R.id.search)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_all->deleteAllNotes()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllNotes() {
        var builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_->
            viewModel.deleteAllNotes()
        }
        builder.setNegativeButton("No"){_,_-> }
        builder.setTitle("Delete all users")
        builder.setMessage("Do you want delete all users?")
        builder.create().show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null)
            searchDatabase(newText)
        return true
    }

    private fun searchDatabase(query: String?) {
        val searchQuery = "%$query%"
        viewModel.searchDatabase(searchQuery).observe(this, { list ->
            list.let {
                adapter.submitList(it)
            }
        })
    }
}