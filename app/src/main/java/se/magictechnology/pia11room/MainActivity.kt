package se.magictechnology.pia11room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var db : AppDatabase

    var peopleadapter = PeopleAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()


        var peoplerv = findViewById<RecyclerView>(R.id.peopleRV)

        peoplerv.adapter = peopleadapter
        peoplerv.layoutManager = LinearLayoutManager(this)


        findViewById<Button>(R.id.addButton).setOnClickListener {
            val firstname = findViewById<EditText>(R.id.firstnameET).text.toString()
            val lastname = findViewById<EditText>(R.id.lastnameET).text.toString()

            val nyperson = User(0, firstname, lastname)

            CoroutineScope(Dispatchers.IO).launch {
                val userDao = db.userDao()
                userDao.insertAll(nyperson)
                loadpeople()
            }

        }

        loadpeople()
    }

    fun clickRow(person : User) {
        CoroutineScope(Dispatchers.IO).launch {
            val userDao = db.userDao()
            userDao.delete(person)
            loadpeople()
        }
    }


    fun loadpeople() {


        CoroutineScope(Dispatchers.IO).launch {
            val userDao = db.userDao()

            val allpeople = userDao.getAll()

            peopleadapter.people = allpeople.toMutableList()

            runOnUiThread {
                peopleadapter.notifyDataSetChanged()
            }
        }
    }


    fun exempelroom() {
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()

        CoroutineScope(Dispatchers.IO).launch {

            val userDao = db.userDao()

            //var nyperson = User(0, "Henning", "")
            //userDao.insertAll(nyperson)

            val users: List<User> = userDao.getNoLastname()

            Log.i("PIA11DEBUG", "ANTAL " + users.size.toString())
            for (currentuser in users) {
                Log.i("PIA11DEBUG", currentuser.firstName!!)
                Log.i("PIA11DEBUG", currentuser.lastName!!)
                Log.i("PIA11DEBUG", currentuser.uid!!.toString())

            }

        }
    }

}