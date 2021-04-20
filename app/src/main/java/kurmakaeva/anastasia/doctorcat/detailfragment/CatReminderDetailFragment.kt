package kurmakaeva.anastasia.doctorcat.detailfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import kurmakaeva.anastasia.doctorcat.R
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
        binding.lifecycleOwner = this
        binding.viewModel = sharedViewModel

        sharedViewModel.singleCatReminder.observe(viewLifecycleOwner, Observer {
            Glide.with(this)
                .load(it.image)
                .override(150, Target.SIZE_ORIGINAL)
                .circleCrop()
                .placeholder(R.drawable.ic_paw)
                .into(binding.catPicture)
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args by navArgs<CatReminderDetailFragmentArgs>()
        sharedViewModel.getSingleCatReminder(args.reminderId)
    }
}