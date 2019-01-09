package example.framgia.com.demo

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import example.framgia.com.demo.data.Repository
import example.framgia.com.demo.data.local.AppDatabase
import example.framgia.com.demo.data.local.LocalDataSource
import example.framgia.com.demo.data.model.User
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var itemAdapter: ItemAdapter
    private lateinit var repository: Repository
    private var user: User? = null
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initData()
        itemClick()
    }

    override fun onStop() {
        super.onStop()
        viewModel.onStop()
    }

    private fun initView() {
        itemAdapter = ItemAdapter()
        recycler_view.adapter = itemAdapter
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    private fun initData() {
        val appDatabase = AppDatabase.getInstance(applicationContext)
        val localDataSource = LocalDataSource.getInstance(appDatabase.userDao())
        repository = Repository.getInstance(localDataSource)
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
