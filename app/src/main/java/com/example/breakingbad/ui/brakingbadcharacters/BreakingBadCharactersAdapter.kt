package com.example.breakingbad.ui.brakingbadcharacters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.breakingbad.R
import com.example.breakingbad.common.loadImage
import com.example.domain.models.BreakingBadCharacterModel


class BreakingBadCharactersAdapter :
    RecyclerView.Adapter<BreakingBadCharactersAdapter.CharactersViewHolder>() {

    private val breakingBadCharactersList = mutableListOf<BreakingBadCharacterModel>()

    private lateinit var onclickItemListener: OnClickItemListener

    fun setData(newCharacterList: List<BreakingBadCharacterModel>) {
        breakingBadCharactersList.clear()
        breakingBadCharactersList.addAll(newCharacterList)
        notifyDataSetChanged()
    }

    fun onClickItem(onClickItemListener: OnClickItemListener) {
        this.onclickItemListener = onClickItemListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cell_breaking_bad_character, parent, false)


        return CharactersViewHolder(view, parent.context, onclickItemListener)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        val breakingBadCharacter = breakingBadCharactersList[position]
        holder.bind(breakingBadCharacter)
        holder.onClick(breakingBadCharacter)
    }

    override fun getItemCount(): Int {
        return breakingBadCharactersList.size
    }

    class CharactersViewHolder(
        itemView: View,
        private val context: Context,
        private val onClickItemListener: OnClickItemListener
    ) : RecyclerView.ViewHolder(itemView) {

        private val image = itemView.findViewById<ImageView>(R.id.image_character)
        private val name = itemView.findViewById<TextView>(R.id.text_character_name)

        fun bind(brakingBadCharacterModel: BreakingBadCharacterModel) {
            loadImage(image, brakingBadCharacterModel.imageUrl, context)
            name.text = brakingBadCharacterModel.name
        }

        fun onClick(brakingBadCharacter: BreakingBadCharacterModel) {
            itemView.setOnClickListener {
                onClickItemListener.getBreakingBadCharacter(brakingBadCharacter)
            }
        }

    }

    interface OnClickItemListener {
        fun getBreakingBadCharacter(breakingBadCharacter: BreakingBadCharacterModel)
    }


}