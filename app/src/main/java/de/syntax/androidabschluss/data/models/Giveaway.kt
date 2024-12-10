package de.syntax.androidabschluss.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity class representing a giveaway item.
 * @property id The unique identifier of the giveaway.
 * @property title The title of the giveaway.
 * @property worth The estimated worth of the giveaway.
 * @property thumbnail The URL of the thumbnail image.
 * @property image The URL of the full image.
 * @property description The description of the giveaway.
 * @property instructions The instructions to participate in the giveaway.
 * @property openGiveawayUrl The URL to open the giveaway.
 * @property publishedDate The published date of the giveaway.
 * @property type The type of the giveaway.
 * @property platforms The platforms supported by the giveaway.
 * @property endDate The end date of the giveaway.
 * @property users The number of users participating in the giveaway.
 * @property status The status of the giveaway.
 * @property gamerPowerUrl The URL to GamerPower website.
 * @property openGiveaway The open status of the giveaway.
 */
@Entity
data class Giveaway(
    @PrimaryKey(autoGenerate = false)
    val id: Int?,
    val title: String?,
    val worth: String?,
    val thumbnail: String?,
    val image: String?,
    val description: String?,
    val instructions: String?,
    val openGiveawayUrl: String?,
    val publishedDate: String?,
    val type: String?,
    val platforms: String?,
    val endDate: String?,
    val users: Int?,
    val status: String?,
    val gamerPowerUrl: String?,
    val openGiveaway: String?
)