package kurmakaeva.anastasia.doctorcat.listfragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kurmakaeva.anastasia.doctorcat.R
import kurmakaeva.anastasia.doctorcat.authentication.AuthenticationActivity
import kurmakaeva.anastasia.doctorcat.databinding.FragmentCatRemindersListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CatRemindersListFragment: Fragment(), SelectableReminder {

    private lateinit var binding: FragmentCatRemindersListBinding
    private lateinit var adapter: CatRemindersListAdapter
    private val viewModel by viewModel<CatRemindersViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCatRemindersListBinding
            .inflate(inflater, container,false)

        binding.lifecycleOwner = this

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addFAB.setOnClickListener {
            navigateToAddReminder()
        }

        binding.catFacts.isSelected = true
        displayCatFacts()

        setupAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logoutButton -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(activity, AuthenticationActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
            R.id.aboutButton -> {
                showAboutInfo()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun selectedReminder(reminderId: String) {
        val action = CatRemindersListFragmentDirections.actionCatRemindersListFragmentToCatReminderDetailFragment(reminderId)
        this.findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        binding.catFacts.isSelected = true
    }

    override fun onPause() {
        super.onPause()
        binding.catFacts.isSelected = false
    }

    private fun setupAdapter() {
        adapter = CatRemindersListAdapter(requireContext(), this)
        binding.apply {
            catRemindersListRv.adapter = adapter
        }

        val itemTouchHelper = ItemTouchHelper(SwipeToDelete(adapter, viewModel))
        itemTouchHelper.attachToRecyclerView(binding.catRemindersListRv)

        viewModel.loadCatReminders()
        viewModel.listOfCatReminders.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter.submitList(it)
            if (it.isNullOrEmpty()) {
                binding.noRemindersTv.visibility = View.VISIBLE
            }
        })
    }

    private fun navigateToAddReminder() {
        val action = CatRemindersListFragmentDirections.actionCatRemindersListFragmentToAddCatReminderFragment()
        this.findNavController().navigate(action)
    }

    private fun displayCatFacts() {
        viewModel.getCatFact()
        viewModel.catFact.observe(viewLifecycleOwner, Observer {
            binding.catFacts.text = StringBuilder(getString(R.string.cat_fact_label) + it)
        })

        viewModel.missingNetworkEvent.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                binding.catFacts.text = getString(R.string.something_went_wrong)
            }
        })
    }

    private fun showAboutInfo() {
        val dialogTitle = getString(R.string.about_menu_title)
        val dialogMessage = getString(R.string.about_text)
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(dialogTitle)
        builder.setMessage(dialogMessage)
        builder.create().show()
    }
}