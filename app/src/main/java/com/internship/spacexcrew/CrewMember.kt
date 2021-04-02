package com.internship.spacexcrew

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CrewMember(
        @PrimaryKey
        val id: String,
        val name: String,
        val agency: String,
        val image: String,
        val wikipedia: String,
        val status: String
        )
