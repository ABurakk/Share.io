package com.solutionchallenge.sharecourseandbook.View.recyclerViewAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.solutionchallenge.sharecourseandbook.Model.FirebaseModels.Book
import com.solutionchallenge.sharecourseandbook.R
import kotlinx.android.synthetic.main.book_request_view.view.*

class bookRequestAdapter(var bookList:List<Book>,var view:View) : RecyclerView.Adapter<bookRequestAdapter.bookRequestViewHolder>() {



    class bookRequestViewHolder(item:View): RecyclerView.ViewHolder(item){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bookRequestViewHolder {

        var view=LayoutInflater.from(parent.context).inflate(R.layout.book_request_view,parent,false)

        return bookRequestViewHolder(view)

    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: bookRequestViewHolder, position: Int) {
        var book=bookList.get(position)

        holder.itemView.apply {
                Glide.with(view).load(book.image_url).into(imageViewBook)
                tvBookName.text=book.name
                tvBookAuthor.text=book.author_name
                tvBookPrice.text=book.price.toString()+book.currency

                btnBookDonate.setOnClickListener {
                    Toast.makeText(context,"${tvBookName},${tvBookAuthor}",Toast.LENGTH_LONG).show()
                }

        }


    }


}