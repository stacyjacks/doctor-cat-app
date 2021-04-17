package kurmakaeva.anastasia.doctorcat.detailfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import kurmakaeva.anastasia.doctorcat.databinding.FragmentCatReminderDetailBinding
import kurmakaeva.anastasia.doctorcat.listfragment.CatRemindersViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CatReminderDetailFragment: Fragment() {

    private lateinit var binding: FragmentCatReminderDetailBinding
    private val sharedViewModel by sharedViewModel<CatRemindersViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        binding = FragmentCatReminderDetailBinding.inflate(inflater, container, false)

        val args by navArgs<CatReminderDetailFragmentArgs>()

        viewModelSetup(args.reminderId)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun viewModelSetup(arguments: String) {
        sharedViewModel.getSingleCatReminder(arguments)
        sharedViewModel.singleCatReminder.observe(viewLifecycleOwner, Observer {
            binding.viewModel = sharedViewModel
        })
    }
}