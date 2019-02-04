package com.example.thefirstnewprojectaddtoday28jan62

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class PageChatFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
//        list_manage_Chat.setOnClickListener {
//            val popupMenu = PopupMenu(context, it)
//            popupMenu.setOnMenuItemClickListener { item ->
//                when (item.itemId) {
//                    R.id.Btn_edit_chat -> {
//                        Toast.makeText(context,"Edit",Toast.LENGTH_SHORT).show()
//                        true
//                    }
//                    R.id.Btn_Delete_chat -> {
//                        Toast.makeText(context,"Delete",Toast.LENGTH_SHORT).show()
//                        true
//                    }
//                }
//            }
//            popupMenu.inflate(R.menu.menu_page_chat)
//            popupMenu.show()
//        }

    }

    companion object {
        fun newInstance() = PageChatFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}
