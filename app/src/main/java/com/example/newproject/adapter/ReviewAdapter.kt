package com.example.newproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newproject.R
import com.example.newproject.model.ReviewModel

class ReviewAdapter(
    var reviews: List<ReviewModel>,
    val onDeleteClick: (String) -> Unit,
    val onEditClick: (ReviewModel) -> Unit
) : RecyclerView.Adapter<ReviewAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val emailTextView: TextView = view.findViewById(R.id.emailEditText)
        val ratingBar: RatingBar = view.findViewById(R.id.ratingBar)
        val messageTextView: TextView = view.findViewById(R.id.contentEditText)
        val deleteButton: TextView = view.findViewById(R.id.reviewDeleteButton)
        val editButton: TextView = view.findViewById(R.id.reviewEditButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewAdapter.ViewHolder, position: Int) {
        val review = reviews[position]
        holder.emailTextView.text = review.email
        holder.ratingBar.rating = review.rating.toFloat()
        holder.messageTextView.text = review.message

        holder.deleteButton.setOnClickListener { onDeleteClick(review.reviewId) }
        holder.editButton.setOnClickListener { onEditClick(review) }
    }

    override fun getItemCount(): Int {
        fun updateData(newReviews: List<ReviewModel>) {
            reviews = newReviews
            notifyDataSetChanged()
        }
    }
}