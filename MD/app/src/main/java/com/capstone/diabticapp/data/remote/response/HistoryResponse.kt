package com.capstone.diabticapp.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class HistoryResponse(

	@field:SerializedName("data")
	val data: List<HistoryDataItem>,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("message")
	val message: String
)

@Parcelize
data class HistoryPredictionDetails(

	@field:SerializedName("blurredVision")
	val blurredVision: Int,

	@field:SerializedName("occupationalExposureChemicals")
	val occupationalExposureChemicals: Int,

	@field:SerializedName("medicalCheckupsFrequency")
	val medicalCheckupsFrequency: Int,

	@field:SerializedName("medicationAdherence")
	val medicationAdherence: Int,

	@field:SerializedName("gender")
	val gender: Int,

	@field:SerializedName("diastolicBP")
	val diastolicBP: Int,

	@field:SerializedName("frequentUrination")
	val frequentUrination: Int,

	@field:SerializedName("fastingBloodSugar")
	val fastingBloodSugar: Int,

	@field:SerializedName("physicalActivity")
	val physicalActivity: Int,

	@field:SerializedName("previousPreDiabetes")
	val previousPreDiabetes: Int,

	@field:SerializedName("systolicBP")
	val systolicBP: Int,

	@field:SerializedName("unexplainedWeightLoss")
	val unexplainedWeightLoss: Int,

	@field:SerializedName("fatigueLevels")
	val fatigueLevels: Int,

	@field:SerializedName("slowHealingSores")
	val slowHealingSores: Int,

	@field:SerializedName("smoking")
	val smoking: Int,

	@field:SerializedName("excessiveThirst")
	val excessiveThirst: Int,

	@field:SerializedName("cholesterolTotal")
	val cholesterolTotal: Int,

	@field:SerializedName("alcoholConsumption")
	val alcoholConsumption: Int,

	@field:SerializedName("gestationalDiabetes")
	val gestationalDiabetes: Int,

	@field:SerializedName("antihypertensiveMedications")
	val antihypertensiveMedications: Int,

	@field:SerializedName("qualityOfLifeScore")
	val qualityOfLifeScore: Int,

	@field:SerializedName("healthLiteracy")
	val healthLiteracy: Int,

	@field:SerializedName("sleepQuality")
	val sleepQuality: Int,

	@field:SerializedName("familyHistoryDiabetes")
	val familyHistoryDiabetes: Int,

	@field:SerializedName("dietQuality")
	val dietQuality: Int,

	@field:SerializedName("tinglingHandsFeet")
	val tinglingHandsFeet: Int,

	@field:SerializedName("heavyMetalsExposure")
	val heavyMetalsExposure: Int,

	@field:SerializedName("antidiabeticMedications")
	val antidiabeticMedications: Int,

	@field:SerializedName("waterQuality")
	val waterQuality: Int,

	@field:SerializedName("hypertension")
	val hypertension: Int,

	@field:SerializedName("age")
	val age: Int,

	@field:SerializedName("hbA1c")
	val hbA1c: Int,

	@field:SerializedName("bmi")
	val bmi: Double
) : Parcelable

data class HistoryDataItem(

	@field:SerializedName("predictionSuggestion")
	val predictionSuggestion: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("predictionResult")
	val predictionResult: String,

	@field:SerializedName("predictionDetails")
	val predictionDetails: HistoryPredictionDetails,

	@field:SerializedName("id")
	val id: String
)
