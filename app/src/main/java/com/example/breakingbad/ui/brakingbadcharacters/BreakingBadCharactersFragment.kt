package com.example.breakingbad.ui.brakingbadcharacters

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.breakingbad.R
import com.example.breakingbad.common.MINIMUM_SYMBOLS
import com.example.breakingbad.common.putKSerializable
import com.example.domain.common.Resource
import com.example.domain.models.BreakingBadCharacterModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.filter_characters_dialog.view.*
import kotlinx.android.synthetic.main.fragment_breaking_bad_characters.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@FlowPreview
@AndroidEntryPoint
class BreakingBadCharactersFragment : Fragment() {

    lateinit var characterAdapter: BreakingBadCharactersAdapter

    private val viewModel: BreakingBadCharactersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_breaking_bad_characters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        characterAdapter = BreakingBadCharactersAdapter()
        rv_characters.run {
            adapter = characterAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }


        refreshList()
        openProfile()
        setupBreakingBadCharactersList()
        inputResultOfBreakingBadCharacterSearch()
        sendBreakingBadCharactersListToAdapter()
    }

    private fun setupBreakingBadCharactersList() {

        viewModel.breakingBadCharacterList.observe(viewLifecycleOwner, {
            text_error.visibility = View.GONE
            refresh_layout.isRefreshing = false

            when (it) {
                Resource.Loading -> {
                    refresh_layout.isRefreshing = true
                }
                is Resource.Failure -> {
                    text_error.visibility = View.VISIBLE
                    text_error.text = it.message
                }
                is Resource.Success -> {
                    characterAdapter.setData(it.data)
                }
            }
        })
    }

    private fun resultFromSearchView(): StateFlow<String> {
        val query = MutableStateFlow("")

        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { query.value = it }
                return true
            }

        })
        return query
    }

    private fun inputResultOfBreakingBadCharacterSearch() {
        lifecycleScope.launch {
            resultFromSearchView()
                .filter { it.length >= MINIMUM_SYMBOLS }
                .debounce(500)
                .distinctUntilChanged()
                .flowOn(Dispatchers.Default)
                .collect { result ->
                    viewModel.filterByName(result)
                }
        }

    }

    private fun sendBreakingBadCharactersListToAdapter() {
        viewModel.filteredCharactersList.observe(viewLifecycleOwner, {
            characterAdapter.setData(it)
        })
    }

    private fun showFilterDialog() {
        val filterDialog =
            LayoutInflater.from(requireContext()).inflate(R.layout.filter_characters_dialog, null)

        val builder = AlertDialog.Builder(requireContext())
            .setView(filterDialog)

        val alertDialog = builder.show()

        filterDialog.text_apply.setOnClickListener {
            alertDialog.dismiss()

            val seasonNumber: Int = filterDialog.edit_number_of_season.text.toString().toInt()
            viewModel.filterBySeason(seasonNumber)
        }

        filterDialog.text_cancel.setOnClickListener { alertDialog.dismiss() }
    }

    private fun openProfile() {
        characterAdapter.onClickItem(object : BreakingBadCharactersAdapter.OnClickItemListener {
            override fun getBreakingBadCharacter(breakingBadCharacter: BreakingBadCharacterModel) {
                val bundle = Bundle()
                bundle.putKSerializable("characterObject", breakingBadCharacter)
                findNavController().navigate(
                    R.id.action_brakingBadCharactersFragment_to_characterDetailsFragment,
                    bundle
                )
            }

        })
    }

    private fun refreshList() {
        refresh_layout.setOnRefreshListener {
            viewModel.refreshBreakingBadCharactersList()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        showFilterDialog()
        return true
    }


}