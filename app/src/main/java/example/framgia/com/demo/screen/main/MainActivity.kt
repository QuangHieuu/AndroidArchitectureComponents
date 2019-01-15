package example.framgia.com.demo.screen.main

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import example.framgia.com.demo.MainApplication
import example.framgia.com.demo.R
import example.framgia.com.demo.data.Repository
import example.framgia.com.demo.data.model.User
import example.framgia.com.demo.databinding.ActivityMainBinding
import example.framgia.com.demo.screen.detail.DetailFragment
import javax.inject.Inject


class MainActivity : AppCompatActivity(), LifecycleOwner {

    @Inject
    lateinit var lifecycleRegistry: LifecycleRegistry
    @Inject
    lateinit var repository: Repository
    @Inject
    lateinit var viewModel: MainViewModel
    @Inject
    lateinit var customLifecycle: CustomLifecycle
    @Inject
    lateinit var mainBinding: ActivityMainBinding

    private lateinit var itemAdapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
        itemClick()
    }

    private fun initView() {
        DaggerMainComponent.builder().mainModule(MainModule(this))
            .appComponent((application as MainApplication).getAppComponent()).build()
            .inject(this)
        mainBinding.viewModel = viewModel
        itemAdapter = ItemAdapter()
        lifecycleRegistry.addObserver(customLifecycle)
    }

    private fun initData() {
        viewModel.setRepository(repository)
        viewModel.setAdapter(itemAdapter)
        viewModel.getAllUser()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item!!.itemId) {
            R.id.add_user -> {
                val detailFragment = DetailFragment()
                val transition = this.supportFragmentManager.beginTransaction()
                transition.add(R.id.layout_container, detailFragment)
                transition.addToBackStack(null)
                transition.commit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun itemClick() {
        viewModel.getUserDetail().observe(this,
            Observer<User> { user ->
                val detailFragment = DetailFragment()
                val bundle = Bundle()
                bundle.putParcelable("ARGUMENTS_USER", user)
                detailFragment.arguments = bundle
                val transition = this.supportFragmentManager.beginTransaction()
                transition.add(R.id.layout_container, detailFragment)
                transition.addToBackStack(null)
                transition.commit()
            })
    }

}
