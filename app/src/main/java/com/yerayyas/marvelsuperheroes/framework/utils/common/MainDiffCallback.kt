package com.yerayyas.marvelsuperheroes.framework.utils.common

import androidx.recyclerview.widget.DiffUtil
import com.yerayyas.marvelsuperheroes.domain.model.Super

class MainDiffCallback : DiffUtil.ItemCallback<Super>() {

    override fun areItemsTheSame(oldItem: Super, newItem: Super): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Super, newItem: Super): Boolean {
        return oldItem == newItem
    }
}
