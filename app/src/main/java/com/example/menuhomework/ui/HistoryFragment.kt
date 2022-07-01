package com.example.menuhomework.ui

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.menuhomework.R
import com.example.menuhomework.databinding.FragmentSearchBinding
import com.example.menuhomework.model.database.*
import com.example.menuhomework.viewmodels.HistoryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment :
    BaseFragment<
            List<WeatherEntity>,
            FragmentSearchBinding>(R.layout.fragment_search),
    RequestRecyclerAdapter.OnItemClickListener {

    private lateinit var adapter: RequestRecyclerAdapter
    override val viewModel: HistoryViewModel by viewModel()
    private var sorting = DATEDESC

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerForContextMenu(binding.recyclerList as View)
        setHasOptionsMenu(true)

        initList()

        loadPreferences()
    }

    private fun initList() {
        val recyclerView = binding.recyclerList

        recyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

        adapter = RequestRecyclerAdapter(requireActivity(), this)

        adapter.itemClickListener = this
        recyclerView.adapter = adapter
    }

    private fun showDialog(message: String, function: () -> Unit) {
        AlertDialog.Builder(requireContext())
            .setTitle(message)
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no))
            { _, _ ->
            }
            .setPositiveButton(getString(R.string.yes))
            { _, _ ->
                function()
            }
            .create()
            .show()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().menuInflater.inflate(R.menu.main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        } else if (id == R.id.action_clear) {
            showDialog(getString(R.string.want_delete_all)) {
                viewModel.deleteAll()
            }
            return true
        } else if (id == R.id.sort_by_name) {
            saveAndSort(NAME)
        } else if (id == R.id.sort_by_date) {
            saveAndSort(DATE)
        } else if (id == R.id.sort_by_name_descending) {
            saveAndSort(NAMEDESC)
        } else if (id == R.id.sort_by_date_descending) {
            saveAndSort(DATEDESC)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun saveAndSort(sort: Int) {
        viewModel.sort(sort)
        sorting = sort
        savePreferences()
    }

    private fun savePreferences() {
        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)

        val editor = sharedPref.edit()

        editor.putInt(SORT, sorting)

        editor.apply()
    }

    private fun loadPreferences() {
        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        sorting = sharedPref.getInt(SORT, DATEDESC)
        viewModel.sort(sorting)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        requireActivity().menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.remove_context -> {
                showDialog(getString(R.string.do_you_want_delete)) {
                    viewModel.deleteForId(adapter.weathers[adapter.menuPosition].id)
                }
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onItemClick(view: View, element: WeatherEntity) {
        val fragment = WeatherFragment.newInstance(element, true)

        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun bindView(): FragmentSearchBinding = FragmentSearchBinding.bind(requireView())

    override fun renderSuccess(data: List<WeatherEntity>) {
        adapter.weathers = mutableListOf<WeatherEntity>().apply {
            for (weather in data) {
                this.add(weather)
            }
        }
    }

    companion object {
        private const val SORT = "sort"
    }
}