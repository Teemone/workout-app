package com.example.workoutapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp.databinding.ExerciseNumberListItemBinding

class ExerciseItemAdapter(val itemList: ArrayList<Exercises>):
    RecyclerView.Adapter<ExerciseItemAdapter.ExerciseViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        return ExerciseViewHolder(ExerciseNumberListItemBinding.
        inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.tvItemNumber.text = "${itemList[position].id + 1}"

        if (itemList[position].isCompleted){
            holder.flExerciseItem.background =
                ContextCompat.getDrawable(holder.itemView.context, R.drawable.item_bg_completed)
            holder.tvItemNumber.visibility = View.INVISIBLE
//            notifyItemChanged(position)
        }
    }


    class ExerciseViewHolder(binding: ExerciseNumberListItemBinding):
        RecyclerView.ViewHolder(binding.root){
            val flExerciseItem = binding.flExerciseItem
            val tvItemNumber = binding.tvItemNumber

    }
}