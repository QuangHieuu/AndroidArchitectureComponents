package example.framgia.com.demo.screen.detail

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import example.framgia.com.demo.R
import example.framgia.com.demo.data.model.User
import example.framgia.com.demo.screen.main.MainViewModel
import kotlinx.android.synthetic.main.detail_fragment.view.*

class DetailFragment : Fragment(), View.OnClickListener {

    private lateinit var detailFragment: View
    private lateinit var viewModel: MainViewModel
    private var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailFragment = inflater.inflate(R.layout.detail_fragment, container, false)
        viewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        initView()
        initData()
        return detailFragment
    }

    private fun initView() {
        detailFragment.button_Submit.setOnClickListener(this)
    }

    private fun initData() {
        if (arguments != null) {
            user = arguments!!.getParcelable("ARGUMENTS_USER") as User
            if (user != null) {
                detailFragment.edit_userFirstName.setText(user!!.firstName)
                detailFragment.edit_userLastName.setText(user!!.lastName)
            }
        }
    }

    private fun updateUser() {
        viewModel.updateUser(
            detailFragment.edit_userFirstName.text.toString(),
            detailFragment.edit_userLastName.text.toString(),
            user!!.uid
        )
    }

    private fun insertUser() {
        viewModel.insertUser(
            detailFragment.edit_userFirstName.text.toString(),
            detailFragment.edit_userLastName.text.toString()
        )
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.button_Submit -> if (user != null) {
                updateUser()
                fragmentManager!!.popBackStack()
            } else {
                insertUser()
                fragmentManager!!.popBackStack()
            }
            else -> return
        }
    }
}
