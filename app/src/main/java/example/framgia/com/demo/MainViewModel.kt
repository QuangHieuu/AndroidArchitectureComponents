package example.framgia.com.demo

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import android.view.View
import android.widget.Toast
import example.framgia.com.demo.data.Repository
import example.framgia.com.demo.data.model.User
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlin.random.Random

class MainViewModel(application: Application) : AndroidViewModel(application),
    ItemAdapter.ItemClick {

    private lateinit var repository: Repository
    private var app: Application = application
    private lateinit var itemAdapter: ItemAdapter
    private lateinit var compositeDisposable: CompositeDisposable
    private var user: MutableLiveData<User> = MutableLiveData()

    fun onCreate() {
        Log.d("ON_CREATE","ON CREATE")
        compositeDisposable = CompositeDisposable()
    }

    fun onResume(){
        Log.d("ON_RESUME","ON RESUME")
    }

    fun onStop() {
        Log.d("ON_STOP","ON STOP")
        compositeDisposable.clear()
    }

    fun setRepository(repository: Repository) {
        this.repository = repository
    }

    fun setAdapter(itemAdapter: ItemAdapter) {
        this.itemAdapter = itemAdapter
        this.itemAdapter.setItemClick(this)
    }

    fun getUserDetail() = user


    fun updateUser(first: String, last: String, id: Int) {
        repository.updataUser(first, last, id)
    }

    fun insertUser(firstName: String, lastName: String) {
        val insertUser = Observable.create(ObservableOnSubscribe<Any> { e ->
            val user = User(Random.nextInt(), firstName, lastName)
            repository.insertUser(user)
            e.onComplete()
        })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({}, { er -> Toast.makeText(app, er.message, Toast.LENGTH_SHORT).show() }, {
                Toast.makeText(app, "VAO", Toast.LENGTH_SHORT).show()
                getAllUser()
            })
        compositeDisposable.add(insertUser)
    }

    fun getAllUser() {
        val getAllUser =
            repository.getAllUser().subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()
            )
                .subscribe({ t -> itemAdapter.updateListUser(t) },
                    { t -> Toast.makeText(app, t.message, Toast.LENGTH_SHORT).show() },
                    {
                        Toast.makeText(app, "GG", Toast.LENGTH_SHORT).show()
                    })
        compositeDisposable.add(getAllUser)
    }


    override fun onItemClick(view: View, user: User, pos: Int) {
        this.user.postValue(user)
    }
}