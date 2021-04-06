package kurmakaeva.anastasia.doctorcat.listfragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import kurmakaeva.anastasia.doctorcat.R
import kurmakaeva.anastasia.doctorcat.authentication.AuthenticationActivity
import kurmakaeva.anastasia.doctorcat.databinding.FragmentCatRemindersListBinding

class CatRemindersListFragment: Fragment() {

    private lateinit var binding: FragmentCatRemindersListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_cat_reminders_list, container,false)

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addFAB.setOnClickListener {
            
        }
    }

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
}