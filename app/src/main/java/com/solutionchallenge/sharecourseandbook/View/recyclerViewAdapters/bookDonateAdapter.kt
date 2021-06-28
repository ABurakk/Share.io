package com.solutionchallenge.sharecourseandbook.View.recyclerViewAdapters

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.solutionchallenge.sharecourseandbook.Model.FirebaseModels.BookRequest
import com.solutionchallenge.sharecourseandbook.R
import kotlinx.android.synthetic.main.book_donate_rv_view.view.*
import kotlinx.android.synthetic.main.book_request_view.view.*

class bookDonateAdapter(var list: List<BookRequest>,var view:View) : RecyclerView.Adapter<bookDonateAdapter.bookDonateViewHolder>() {


    class bookDonateViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bookDonateViewHolder {
        var view=LayoutInflater.from(parent.context).inflate(R.layout.book_donate_rv_view,parent,false)
        return bookDonateViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: bookDonateViewHolder, position: Int) {

        var book=list.get(position).book

        holder.itemView.apply {
            Glide.with(view).load(book.image_url).into(imgBook)
            tvBookName2.text=book.name
            tvBookAuthor2.text=book.author_name
            tvBookPrice2.text=book.price.toString()+book.currency
        }

    }

}