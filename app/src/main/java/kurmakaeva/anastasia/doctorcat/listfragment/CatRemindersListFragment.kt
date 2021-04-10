package kurmakaeva.anastasia.doctorcat.listfragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import kurmakaeva.anastasia.doctorcat.authentication.AuthenticationActivity
import kurmakaeva.anastasia.doctorcat.databinding.FragmentCatRemindersListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class CatRemindersListFragment: Fragment(), SelectableReminder {

    private lateinit var binding: FragmentCatRemindersListBinding
    private lateinit var adapter: CatRemindersListAdapter
    private val viewModel by viewModel<CatRemindersViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCatRemindersListBinding
            .inflate(inflater, container,false)

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addFAB.setOnClickListener {
            navigateToAddReminder()
        }

        adapter = CatRemindersListAdapter(this)

        binding.catRemindersListRv.adapter = adapter

        viewModel.loadCatReminders()
        viewModel.listOfCatReminders.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter.submitList(it)

            if (viewModel.listOfCatReminders.value == null) { // TODO: esto no va, revisar
                binding.noRemindersTv.visibility = View.VISIBLE
            } else {
                binding.noRemindersTv.visibility = View.GONE
            }
        })
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        inflater.inflate(R.menu.main_menu, menu)
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.logout -> {
//                FirebaseAuth.getInstance().signOut()
//                val intent = Intent(activity, AuthenticationActivity::class.java)
//                startActivity(intent)
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }

    override fun selectedReminder(reminderId: String) {
        val action = CatRemindersListFragmentDirections.actionCatRemindersListFragmentToCatReminderDetailFragment(reminderId)
        this.findNavController().navigate(action)
    }

    private fun navigateToAddReminder() {
        val action = CatRemindersListFragmentDirections.actionCatRemindersListFragmentToAddCatReminderFragment()
        this.findNavController().navigate(action)
    }
}