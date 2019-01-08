package example.framgia.com.demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import example.framgia.com.demo.data.Repository
import example.framgia.com.demo.data.local.AppDatabase
import example.framgia.com.demo.data.local.LocalDataSource
import example.framgia.com.demo.data.model.User
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random


class MainActivity : AppCompatActivity(), View.OnClickListener, ItemAdapter.ItemClick {

    private lateinit var itemAdapter: ItemAdapter
    private lateinit var repository: Repository
    private var user: User? = null
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initData()
        getAllUser()
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
    }

    private fun initView() {
        itemAdapter = ItemAdapter(this)
        recycler_view.adapter = itemAdapter
        button_submit.setOnClickListener(this)
        button_update.setOnClickListener(this)
    }

    private fun initData() {
        val appDatabase = AppDatabase.getInstance(applicationContext)
        val localDataSource = LocalDataSource.getInstance(appDatabase.userDao())
        repository = Repository.getInstance(localDataSource)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.button_submit -> insertUser(
                edit_userFirstName.text.toString(),
                edit_userLastName.text.toString()
            )
            R.id.button_update -> if (user != null) {
                updateUser(edit_userFirstName.text.toString(), edit_userLastName.text.toString(), user!!.uid)
            }
        }
    }

    override fun onItemClick(user: User, pos: Int) {
        edit_userFirstName.setText(user.firstName)
        edit_userLastName.setText(user.lastName)
        this.user = user
    }

    fun updateUser(first: String, last: String, id: Int) {
        repository.updateUser(first, last, id)
    }

    fun insertUser(firstName: String, lastName: String) {
        val insertUser = Observable.create(ObservableOnSubscribe<Any> { e ->
            val user = User(Random.nextInt(), firstName, lastName)
            repository.insertUser(user)
            e.onComplete()
        })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({}, { er -> Toast.makeText(this, er.message, Toast.LENGTH_SHORT).show() }, {
                getAllUser()
            })
        compositeDisposable.add(insertUser)
    }

    fun getAllUser() {
        val getAllUser =
            repository.getAllUser().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t -> itemAdapter.updateListUser(t) },
                    { t -> Toast.makeText(this, t.message, Toast.LENGTH_SHORT).show() },
                    {
                        Toast.makeText(this, "Complete", Toast.LENGTH_SHORT).show()
                    })
        compositeDisposable.add(getAllUser)
    }
}
