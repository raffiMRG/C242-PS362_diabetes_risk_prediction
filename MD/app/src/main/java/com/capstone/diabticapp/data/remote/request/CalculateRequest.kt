package com.capstone.diabticapp.data.remote.request

data class CalculateRequest(
    // 1
    val age: Int? = null,
    // 2
    val gender: Int? = null,
    // 3
    val bmi: Double? = null,
    // 4
    val smoking: Int? = null,
    // 5
    val alcoholConsumption: Int? = null,
    // 6
    val physicalActivity: Int? = null,
    // 7
    val dietQuality: Int? = null,
    // 8
    val sleepQuality: Int? = null,
    // 9
    val familyHistoryDiabetes: Int? = null,
    // 10
    val gestationalDiabetes: Int? = null,
    // 11
    val previousPreDiabetes: Int? = null,
    // 12
    val hypertension: Int? = null,
    // 13
    val systolicBP: Int? = null,
    // 14
    val diastolicBP: Int? = null,
    // 15
    val fastingBloodSugar: Int? = null,
    // 16
    val hbA1c: Int? = null,
    // 17
    val cholesterolTotal: Int? = null,
    // 18
    val antihypertensiveMedications: Int? = null,
    // 19
    val antidiabeticMedications: Int? = null,
    // 20
    val frequentUrination: Int? = null,
    // 21
    val excessiveThirst: Int? = null,
    // 22
    val unexplainedWeightLoss: Int? = null,
    // 23
    val fatigueLevels: Int? = null,
    // 24
    val blurredVision: Int? = null,
    // 25
    val slowHealingSores: Int? = null,
    // 26
    val tinglingHandsFeet: Int? = null,
    // 27
    val qualityOfLifeScore: Int? = null,
    // 28
    val heavyMetalsExposure: Int? = null,
    // 29
    val occupationalExposureChemicals: Int? = null,
    // 30
    val waterQuality: Int? = null,
    // 31
    val medicalCheckupsFrequency: Int? = null,
    // 32
    val medicationAdherence: Int? = null,
    // 33
    val healthLiteracy: Int? = null
)