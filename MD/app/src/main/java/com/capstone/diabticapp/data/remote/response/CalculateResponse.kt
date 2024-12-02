package com.capstone.diabticapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class CalculateResponse(

	@field:SerializedName("data")
	val data: DataCalculate? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataCalculate(

	@field:SerializedName("prediction")
	val prediction: Prediction? = null,

	@field:SerializedName("username")
	val username: String? = null
)

data class Prediction(

	@field:SerializedName("predictionSuggestion")
	val predictionSuggestion: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("predictionResult")
	val predictionResult: String? = null,

	@field:SerializedName("predictionDetails")
	val predictionDetails: PredictionDetails? = null,

	@field:SerializedName("id")
	val id: String? = null
)

data class PredictionDetails(

	@field:SerializedName("blurredVision")
	val blurredVision: Int? = null,

	@field:SerializedName("occupationalExposureChemicals")
	val occupationalExposureChemicals: Int? = null,

	@field:SerializedName("medicalCheckupsFrequency")
	val medicalCheckupsFrequency: Int? = null,

	@field:SerializedName("medicationAdherence")
	val medicationAdherence: Int? = null,

	@field:SerializedName("gender")
	val gender: Int? = null,

	@field:SerializedName("diastolicBP")
	val diastolicBP: Int? = null,

	@field:SerializedName("frequentUrination")
	val frequentUrination: Int? = null,

	@field:SerializedName("fastingBloodSugar")
	val fastingBloodSugar: Int? = null,

	@field:SerializedName("physicalActivity")
	val physicalActivity: Int? = null,

	@field:SerializedName("previousPreDiabetes")
	val previousPreDiabetes: Int? = null,

	@field:SerializedName("systolicBP")
	val systolicBP: Int? = null,

	@field:SerializedName("unexplainedWeightLoss")
	val unexplainedWeightLoss: Int? = null,

	@field:SerializedName("fatigueLevels")
	val fatigueLevels: Int? = null,

	@field:SerializedName("slowHealingSores")
	val slowHealingSores: Int? = null,

	@field:SerializedName("smoking")
	val smoking: Int? = null,

	@field:SerializedName("excessiveThirst")
	val excessiveThirst: Int? = null,

	@field:SerializedName("cholesterolTotal")
	val cholesterolTotal: Int? = null,

	@field:SerializedName("alcoholConsumption")
	val alcoholConsumption: Int? = null,

	@field:SerializedName("gestationalDiabetes")
	val gestationalDiabetes: Int? = null,

	@field:SerializedName("antihypertensiveMedications")
	val antihypertensiveMedications: Int? = null,

	@field:SerializedName("qualityOfLifeScore")
	val qualityOfLifeScore: Int? = null,

	@field:SerializedName("healthLiteracy")
	val healthLiteracy: Int? = null,

	@field:SerializedName("sleepQuality")
	val sleepQuality: Int? = null,

	@field:SerializedName("familyHistoryDiabetes")
	val familyHistoryDiabetes: Int? = null,

	@field:SerializedName("dietQuality")
	val dietQuality: Int? = null,

	@field:SerializedName("tinglingHandsFeet")
	val tinglingHandsFeet: Int? = null,

	@field:SerializedName("heavyMetalsExposure")
	val heavyMetalsExposure: Int? = null,

	@field:SerializedName("antidiabeticMedications")
	val antidiabeticMedications: Int? = null,

	@field:SerializedName("waterQuality")
	val waterQuality: Int? = null,

	@field:SerializedName("hypertension")
	val hypertension: Int? = null,

	@field:SerializedName("age")
	val age: Int? = null,

	@field:SerializedName("hbA1c")
	val hbA1c: Int? = null,

	@field:SerializedName("bmi")
	val bmi: Any? = null
)
