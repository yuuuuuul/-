package com.example.fragment.fragments

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels


class PhoneFragment : Fragment() {

    var tabTitle = arrayOf("Phone", "Map")
    lateinit var missphone: Button
    lateinit var call_client: Button
    lateinit var text_info: TextView
    var count_clients: Int = 0
    private val viewModel: FragmentViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view: View? = null
        view = inflater.inflate(com.example.fragment.R.layout.fragment_phone, container, false)
        text_info = view.findViewById<TextView>(com.example.fragment.R.id.text_inf)
        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val TextView = requireActivity().findViewById<View>(com.example.fragment.R.id.text_view) as TextView
        var s = TextView.text.toString()
        text_info.text = s
    }

    override fun onStart() {
        super.onStart()
        missphone = view?.findViewById(com.example.fragment.R.id.missphone)!!
        call_client = view?.findViewById(com.example.fragment.R.id.call_client)!!
        missphone.setOnClickListener {
            count_clients += 1
            if(count_clients > 5){
                count_clients = 0
            }
            call_client.text = "Button ${count_clients}"
        }
    }

}