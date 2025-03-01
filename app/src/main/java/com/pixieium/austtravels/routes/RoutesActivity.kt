package com.pixieium.austtravels.routes

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pixieium.austtravels.R
import com.pixieium.austtravels.auth.SignInActivity
import com.pixieium.austtravels.databinding.ActivityRoutesBinding
import com.pixieium.austtravels.models.BusInfo
import com.pixieium.austtravels.models.BusTiming
import com.pixieium.austtravels.models.Route
import kotlinx.coroutines.launch

class RoutesActivity : AppCompatActivity() {
    private lateinit var mAdapter: RoutesAdapter
    private lateinit var mLayoutManager
            : RecyclerView.LayoutManager
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mBinding: ActivityRoutesBinding
    private val mDatabase: RoutesRepository = RoutesRepository()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRoutesBinding.inflate(layoutInflater)
        val view: View = mBinding.root
        setContentView(view)
        setSupportActionBar(mBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        mBinding.name.isEnabled = false
        mBinding.time.isEnabled = false
        mBinding.select.isEnabled = false

        lifecycleScope.launch {
            try {
                val list: ArrayList<BusInfo> = mDatabase.fetchAllBusInfo()
                if (list.size != 0) {
                    initSpinnerName(list)
                } else {
                    Toast.makeText(
                        baseContext,
                        "Couldn't fetch data from database. Please check your connection",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

            } catch (e: Exception) {
                //e.printStackTrace()
                Toast.makeText(
                    baseContext,
                    "Couldn't fetch data from database. Please check your connection",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_app_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout) {
            Firebase.auth.signOut()
            Toast.makeText(this, "Signing out!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, SignInActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)

            return true
        } else if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return false
    }

    private fun initSpinnerName(list: ArrayList<BusInfo>) {
        val items: ArrayList<String> = ArrayList()
        for (busInfo: BusInfo in list) {
            items.add(busInfo.name)
        }
        mBinding.name.isEnabled = true

        val adapter = ArrayAdapter(baseContext, R.layout.item_spinner, items)
        (mBinding.name.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        mBinding.name.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //Toast.makeText(this@RoutesActivity, s.toString(), Toast.LENGTH_SHORT).show()
                initSpinnerTime(s.toString(), list)
                mBinding.select.isEnabled = true
            }
        })

    }

    private fun initSpinnerTime(selectedName: String, list: ArrayList<BusInfo>) {
        val timingList: ArrayList<String> = ArrayList()
        mBinding.time.isEnabled = true
        for (busInfo: BusInfo in list) {
            if (busInfo.name == selectedName) {
                for (timing: BusTiming in busInfo.timing) {
                    timingList.add(timing.startTime)
                }
                break
            }
        }
        val adapter = ArrayAdapter(baseContext, R.layout.item_spinner, timingList)
        (mBinding.time.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun initRecyclerView(routeList: ArrayList<Route>) {
        mRecyclerView = findViewById(R.id.recyclerView)
        mLayoutManager = LinearLayoutManager(this)
        mAdapter = RoutesAdapter(routeList)
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = mAdapter
    }

    fun onBusSelectClick(view: View) {
        if (!mBinding.time.editText?.text.isNullOrEmpty() && !mBinding.name.editText?.text.isNullOrEmpty()) {
            lifecycleScope.launch {
                val list: ArrayList<Route> = mDatabase.fetchRouteList(
                    mBinding.name.editText?.text.toString(),
                    mBinding.time.editText?.text.toString()
                )
                initRecyclerView(list)
            }

        } else {
            Toast.makeText(
                this,
                "You must select a bus timing first",
                Toast.LENGTH_SHORT
            ).show()
        }

    }
}