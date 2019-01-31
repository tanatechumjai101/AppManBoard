package com.example.thefirstnewprojectaddtoday28jan62

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_one.*
import org.w3c.dom.Text

class OneFragment : Fragment() {
    var adapter: MainApadter? = null
    val listdata = ArrayList<Data>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_one, container, false)
        adapter!!.notifyDataSetChanged()
    }

    companion object {
        fun newInstance() = OneFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val index = listdata.size
        submit.setOnClickListener {
            val textdata = TextInput.text.trim()
            if(textdata.isNotEmpty()){
                AlertDialog.Builder(context)
                    .setTitle("data input")
                    .show()
                val inn = Intent()
                inn.putExtra("DATA",textdata.toString())

            }
            else {
                AlertDialog.Builder(context)
                    .setTitle("No data")
                    .show()
            }

//            listdata.add(Data("","${TextInput.text}"))
//            adapter!!.notifyDataSetChanged()

        }

    }

}
