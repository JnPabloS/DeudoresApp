package com.example.deudoresapp.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.deudoresapp.R
import com.example.deudoresapp.data.local.entities.Debtor
import com.example.deudoresapp.data.server.DebtorServer
import com.example.deudoresapp.databinding.CardViewDebtorsItemBinding
import com.squareup.picasso.Picasso

class DebtorsAdapter(
    private val onItemClicked: (DebtorServer) -> Unit,
) : RecyclerView.Adapter<DebtorsAdapter.ViewHolder>(){

    private var listDebtor: MutableList<DebtorServer> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_debtors_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listDebtor[position])
        holder.itemView.setOnClickListener{ onItemClicked(listDebtor[position]) }
    }

    override fun getItemCount(): Int = listDebtor.size

    fun appendItems(newItems: MutableList<DebtorServer>){
        listDebtor.clear()
        listDebtor.addAll(newItems)
        notifyDataSetChanged()
    }


    class ViewHolder (view: View) : RecyclerView.ViewHolder(view){
        private val binding = CardViewDebtorsItemBinding.bind(view)
        fun bind(debtor: DebtorServer){
            with(binding){
                nameTextView.text = debtor.name
                amountTextView.text = debtor.amount.toString()
                if(debtor.urlPicture != null){
                    Picasso.get().load(debtor.urlPicture).into(pictureImageView)
                }
            }
        }
    }
}