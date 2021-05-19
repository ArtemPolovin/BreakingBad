package com.example.breakingbad.ui.characterdetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.breakingbad.R
import com.example.breakingbad.common.getKSerializable
import com.example.breakingbad.common.loadImage
import com.example.domain.models.BreakingBadCharacterModel
import kotlinx.android.synthetic.main.fragment_character_details.*
import java.lang.StringBuilder

class CharacterDetailsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_character_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val model =
            arguments?.getKSerializable<BreakingBadCharacterModel>("characterObject")

        model?.let { setupProfile(view.context,it) }



    }

    private fun setupProfile(context: Context,model: BreakingBadCharacterModel) {
        val strBuilder = StringBuilder()

        model.apply {
            loadImage(image_profile,this.imageUrl,context)
            text_name.text = name
            text_nickname.text = "@$nickName"
            text_status_value.text = status

            occupation.forEach {
                strBuilder.append("$it\n")
            }
            text_occupation_value.text = strBuilder.toString().dropLast(1)
            strBuilder.clear()

            seasonAppearance.forEach { strBuilder.append("$it, ") }
            text_season_value.text = strBuilder.toString().dropLast(2)
        }

    }



}