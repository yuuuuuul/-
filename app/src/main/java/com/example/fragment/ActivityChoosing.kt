package com.example.fragment

import CustomAdapter
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fragment.databinding.ActivityChoosingBinding
import com.example.fragment.fragments.FragmentViewModel
import com.example.fragment.fragments.PhoneFragment
import java.util.Arrays
import java.util.EnumSet.range

class ActivityChoosing : AppCompatActivity(), CustomAdapter.OnItemClickListener {
    lateinit var button_on_main: Button
    lateinit var selected: String
    lateinit var button_show_choice: Button
    lateinit var recyclerView: RecyclerView
    lateinit var binding: ActivityChoosingBinding
    private val user = arrayListOf<UserDataModel>()
    private var last_position = -1
    private val fragmentViewModel: FragmentViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChoosingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        button_on_main = binding.buttonToMain
        button_show_choice = binding.buttonShowChoice
        recyclerView = binding.recyclerView
        recyclerView.visibility = RecyclerView.VISIBLE
        recyclerView.layoutManager = LinearLayoutManager(this)
        for(i in 1..15) {
            user.add(UserDataModel("${i}", ""))
        }
        var adapter = CustomAdapter(this, user, this)
        recyclerView.adapter = adapter

    }

    override fun onStart() {
        super.onStart()
        button_on_main.setOnClickListener {
            fragmentViewModel.setData("1")
            //supportFragmentManager.beginTransaction().replace(R.id.choosing, PhoneFragment()).commit()
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("point", selected)
            startActivity(intent)
        }
    }

    override fun onItemClick(position: Int, adapter: CustomAdapter, v: View) {
        var clicked_item:UserDataModel = user[position]
        clicked_item.isSelected = !clicked_item.isSelected
        if (last_position != -1){
            user[last_position].isSelected = false
            recyclerView.getChildAt(last_position).setBackgroundColor(Color.WHITE)
        }
        if(clicked_item.isSelected){
            recyclerView.getChildAt(recyclerView.indexOfChild(v)).setBackgroundColor(Color.YELLOW)
            selected = clicked_item.title
            Log.d("Mylog", selected)
            recyclerView.getChildAt(recyclerView.indexOfChild(v)).findViewById<LinearLayout>(R.id.linear_content).setBackgroundColor(Color.YELLOW)
        }else{
            recyclerView.getChildAt(recyclerView.indexOfChild(v)).setBackgroundColor(Color.WHITE)
            recyclerView.getChildAt(recyclerView.indexOfChild(v)).findViewById<LinearLayout>(R.id.linear_content).setBackgroundColor(Color.WHITE)
        }
        last_position = position
    }
}

