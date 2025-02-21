import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newproject.R
import com.example.newproject.model.ReviewModel
class ReviewAdapter(
    var reviews: List<ReviewModel>,
    val onDeleteClick: (String) -> Unit,
    val onEditClick: (ReviewModel) -> Unit
) : RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val emailTextView: TextView = view.findViewById(R.id.emailTextView) // Correct ID
        val ratingBar: RatingBar = view.findViewById(R.id.ratingBar)         // Correct ID
        val messageTextView: TextView = view.findViewById(R.id.messageTextView) // Correct ID
        val deleteButton: Button = view.findViewById(R.id.btnDeleteReview)   // Correct ID
        val editButton: Button = view.findViewById(R.id.btnEditReview)       // Correct ID
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
        return reviews.size
    }

    fun updateData(newReviews: List<ReviewModel>) {
        reviews = newReviews
        notifyDataSetChanged()
    }
}

