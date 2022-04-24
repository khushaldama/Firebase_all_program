package com.khushal.firebasedemo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_item.view.*

class UserAdapter (val context: Context, var arr:ArrayList<User>)
    : RecyclerView.Adapter<UserAdapter.PersonViewHolde>()
{


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolde {
        var inflater= LayoutInflater.from(parent.context)
        var view= inflater.inflate(R.layout.card_item,parent,false)
        return PersonViewHolde(view)
    }

    override fun onBindViewHolder(holder: PersonViewHolde, position: Int) {
        holder.bind(arr[position])
        holder.itemView.imgDelete.setOnClickListener {
            if(context is ViewAll)
            {
                context.delete(position)
            }
        }
        holder.itemView.imgUpdate.setOnClickListener {
            if(context is ViewAll)
            {
                context.update(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return  arr.size
    }

    class PersonViewHolde(var view: View):RecyclerView.ViewHolder(view)
    {
        fun bind(p:User)
        {
            view.tvUname.setText(p.uname)
            view.tvpass.setText(p.pass)

        }
    }
}