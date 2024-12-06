package com.capstone.diabticapp.ui.calculate

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.diabticapp.AuthViewModelFactory
import com.capstone.diabticapp.CalculateViewModelFactory
import com.capstone.diabticapp.MainActivity
import com.capstone.diabticapp.data.pref.UserPreference
import com.capstone.diabticapp.data.remote.request.CalculateRequest
import com.capstone.diabticapp.databinding.ActivityCalculateBinding
import com.capstone.diabticapp.databinding.LayoutDiabetesDetectedBinding
import com.capstone.diabticapp.databinding.LayoutNoDiabetesBinding
import com.capstone.diabticapp.di.Injection
import com.capstone.diabticapp.ui.home.HomeViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.math.pow
import kotlin.math.roundToInt

class CalculateActivity : AppCompatActivity(), FormAdapter.OnFormItemChangedListener{
    private lateinit var binding: ActivityCalculateBinding
    private lateinit var adapter: FormAdapter

    private var name: String = "guest"
    private lateinit var tmpGender: String
    private lateinit var tmpSmoke: String
    private lateinit var tmpFamilyHistoryDiabetes: String
    private lateinit var tmpGestationalDiabetes: String
    private lateinit var tmpPreviousPreDiabetes: String
    private lateinit var tmpHypertension: String
    private lateinit var tmpAntihypertensiveMedications: String
    private lateinit var tmpAntidiabeticMedications: String
    private lateinit var tmpFrequentUrination: String
    private lateinit var tmpExcessiveThirst: String
    private lateinit var tmpUnexplainedWeightLoss: String
    private lateinit var tmpBlurredVision: String
    private lateinit var tmpSlowHealingSores: String
    private lateinit var tmpTinglingHandsFeet: String
    private lateinit var tmpHeavyMetalsExposure: String
    private lateinit var tmpOccupationalExposureChemicals: String
    private lateinit var tmpWaterQuality: String

    private var tmpSleepQuality : Int = 0
    private var tmpHbA1c : Int = 0

    private var gender: Int? = 0
    private var weight: Double = 0.0
    private var height : Int = 0
    private var dietQuality : Int = 0
    private var sleepQuality : Int? = 0
    private var hypertension: Int? = 0
    private var alcohol : Int? = 0
    private var physicalExercise : Int? = 0
    private var fatigueLevels : Int? = 0
    private var qualityOfLifeScore : Int? = 0
    private var medicalCheckupsFrequency : Int? = 0
    private var medicationAdherence : Int? = 0
    private var healthLiteracy : Int? = 0
    private var age: Int? = 0
    private var systolicBP: Int? = 0
    private var diastolicBP: Int? = 0
    private var fastingBloodSugar: Int? = 0
    private var cholesterolTotal: Int? = 0
//    private var age: Int? = null
    private var familyHistoryDiabetes: Int? = null
    private var gestationalDiabetes: Int? = null
    private var previousPreDiabetes: Int? = null
    private var antihypertensiveMedications: Int? = null
    private var antidiabeticMedications: Int? = null
    private var frequentUrination: Int? = null
    private var excessiveThirst: Int? = null
    private var unexplainedWeightLoss: Int? = null
    private var blurredVision: Int? = null
    private var slowHealingSores: Int? = null
    private var tinglingHandsFeet: Int? = null
    private var heavyMetalsExposure: Int? = null
    private var occupationalExposureChemicals: Int? = null
    private var waterQuality: Int? = null
    private var hbA1c: Int? = null
    private var bmi: Double? = null
    private var smoke : Int? = null


    private val _errorMessage = MutableLiveData<String>()
    private val errorMessage: LiveData<String> = _errorMessage

    private val userViewModel: HomeViewModel by viewModels {
        AuthViewModelFactory.getInstance(this)
    }
    private val authRepository by lazy { Injection.provideAuthRepository(applicationContext) } // buat ngebinding username sama status isDiabetes biar tiap account gak conflict status diabetesnya

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculateBinding.inflate(layoutInflater)

        setContentView(binding.root)
        val addViewModel: CalculateViewModel by viewModels {
            CalculateViewModelFactory.getInstance(this)
        }

        addViewModel.isLoading.observe(this){ status ->
            isloading(status)
        }

        observeViewModel()

        binding.recyclerView.isNestedScrollingEnabled = false

        // Setup RecyclerView
        adapter = FormAdapter(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        errorMessage.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        // Setup data
        val formItems = listOf(
            // 1
            FormItem.TextInput("age", "Usia (Tahun)", "Contoh: 20"),
            // 2
            FormItem.RadioButtonInput("gender", "Jenis Kelamin", listOf("Pria", "Wanita")),
            // 3
            FormItem.TextInput("weight", "Berat Badan (Kg)", "Contoh: 68"),
            FormItem.TextInput("height", "Tinggi Badan (Cm)", "Contoh: 170"),
            // 4
            FormItem.RadioButtonInput("smoke", "Merokok", listOf("Ya", "Tidak")),
            // 5
            FormItem.SliderInput("alcohol", "Alcohol (Selama 1 Minggu)", 0f, 20f, 1f),
            // 6
            FormItem.SliderInput("exercise", "Olahraga (Jam)", 0f, 10f, 1f),
            // 7
            FormItem.SliderInput("dietQuality", "Seberapa Baik Polamakan anda", 0f, 10f, 1f),
            // 8
            FormItem.SliderInput("sleepQuality", "Lama Waktu Tidur (Jam / Hari)", 0f, 10f, 1f),
            // 9
            FormItem.RadioButtonInput("familyHistoryDiabetes", "Apakah keluarga anda memilikir riwayat diabets?", listOf("Ya", "Tidak")),
            // 10
            FormItem.RadioButtonInput("gestationalDiabetes", "Apakah terjadi diabetes selama kehamilan?", listOf("Ya", "Tidak")),
            // 11
            FormItem.RadioButtonInput("previousPreDiabetes", "apakah kadar guladarah sempat tinggi atau prediabets?", listOf("Ya", "Tidak")),
            // 12
            FormItem.RadioButtonInput("hypertension", "Hipertensi atau tekanan darah tinggi?", listOf("Ya", "Tidak")),
            // 13
            FormItem.ImageItem("tensiMeter", "https://www.hipwee.com/wp-content/uploads/2019/07/hipwee-collage-16.png", "tensi meter"),
            FormItem.TextInput("systolicBP", "Tekanan darah sistolik(mmHg)", "Contoh: 107"),
            // 14
            FormItem.TextInput("diastolicBP", "Tekanan darah diastolik(mmHg)", "Contoh: 69"),
            // 15
            FormItem.TextInput("fastingBloodSugar", "Kadar gula darah puasa (mg/dL)", "Contoh: 105"),
            // 16
            FormItem.SliderInput("hbA1c", "Tingkat Hemoglobin A1c(%)", 0f, 10f, 1f),
            // 17
            FormItem.TextInput("cholesterolTotal", "Kadar kolesterol total (mg/dL)", "Contoh: 150"),
            // 18
            FormItem.RadioButtonInput("antihypertensiveMedications", "Penggunaan obat antihipertensi?", listOf("Ya", "Tidak")),
            // 19
            FormItem.RadioButtonInput("antidiabeticMedications", "Penggunaan obat antidiabetes?", listOf("Ya", "Tidak")),
            // 20
            FormItem.RadioButtonInput("frequentUrination", "Keberadaan gejala sering buang air kecil?", listOf("Ya", "Tidak")),
            // 21
            FormItem.RadioButtonInput("excessiveThirst", "Keberadaan gejala haus berlebihan?", listOf("Ya", "Tidak")),
            // 22
            FormItem.RadioButtonInput("unexplainedWeightLoss", "Penurunan Berat Badan yang Tidak Jelas?", listOf("Ya", "Tidak")),
            // 23
            FormItem.SliderInput("fatigueLevels", "Tingkat kelelahan", 0f, 10f, 1f),
            // 24
            FormItem.RadioButtonInput("blurredVision", "penglihatan kabur?", listOf("Ya", "Tidak")),
            // 25
            FormItem.RadioButtonInput("slowHealingSores", "Luka yang Lambat Sembuh", listOf("Ya", "Tidak")),
            // 26
            FormItem.RadioButtonInput("tinglingHandsFeet", "Kesemutan pada Tangan atau Kaki", listOf("Ya", "Tidak")),
            // 27
            FormItem.SliderInput("qualityOfLifeScore", "Skor Hidup Sehat", 0f, 100f, 10f),
            // 28
            FormItem.RadioButtonInput("heavyMetalsExposure", "Paparan logam berat", listOf("Ya", "Tidak")),
            // 29
            FormItem.RadioButtonInput("occupationalExposureChemicals", "Paparan bahan kimia berbahaya", listOf("Ya", "Tidak")),
            // 30
            FormItem.RadioButtonInput("waterQuality", "Kualitas air", listOf("Baik", "Buruk")),
            // 31
            FormItem.SliderInput("medicalCheckupsFrequency", "Chek Kesehatan Pertahun", 0f, 4f, 1f),
            // 32
            FormItem.SliderInput("medicationAdherence", "Kepatuhan terhadap Pengobatan", 0f, 10f, 1f),
            // 33
            FormItem.SliderInput("healthLiteracy", "Literasi Kesehatan", 0f, 10f, 1f),
        )

        adapter.submitList(formItems)

        addViewModel.uploadResponse.observe(this) { response ->
            response?.let {
                Log.d("uploadRedponse", "response")
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                isloading(false)
                if (response.data?.prediction?.classification == "Risky"){
                    showDiabetesDetectedLayout()
                }else if (response.data?.prediction?.classification == "No Risky"){
                    showNoDiabetesLayout()
                }else{
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)

                    // Menutup Calculate Activity
                    finish()
                }
            }
        }

        binding.submitLayout.setOnClickListener{
            isloading(true)

            Log.d("UploadData", "uploading")

            lifecycleScope.launch {
                try {
                    Log.d("UploadData", "filetring")
                    // Validasi input
                    // 1 AGE
                    if (age == 0) throw IllegalArgumentException("Usia tidak boleh kosong atau 0")
//                    batas usia 20 tahun
//                    if (age != 0 && age!! < 20) age = 20
                    // 2 GENDER
                    gender = if (tmpGender == "Pria") 0 else 1
                    // 3 BMI
                    if (weight == 0.0) throw IllegalArgumentException("Berat Badan tidak boleh kosong atau 0")
                    if (height == 0) throw IllegalArgumentException("Tinggi Badan tidak boleh kosong atau 0")
                    bmi = weight / ((height / 100.0).pow(2))
                    bmi = (bmi!! * 10).roundToInt() / 10.0

                    // 4 SMOKE
                    smoke = if (tmpSmoke == "Ya") 1 else 0
                    // 8 sleepQuality
                    sleepQuality = if (tmpSleepQuality < 4) 4 else tmpSleepQuality
                    // 9 familyHistoryDiabetes
                    familyHistoryDiabetes = if(tmpFamilyHistoryDiabetes == "Ya") 1 else 0
                    // 10 gestationalDiabetes
                    gestationalDiabetes = if (tmpGestationalDiabetes == "Ya") 1 else 0
                    // 11 previousPreDiabetes
                    previousPreDiabetes = if (tmpPreviousPreDiabetes == "Ya") 1 else 0
                    // 12 hypertension
                    hypertension = if (tmpHypertension == "Ya") 1 else 0
                    // 13 systolicBP
                    if (systolicBP == 0) throw IllegalArgumentException("Tekanan darah sistolik tidak boleh kosong atau 0")
                    if (systolicBP!! < 90) throw IllegalArgumentException("Tekanan darah sistolik minimal 90 mmHg")
                    if (systolicBP!! > 180) throw IllegalArgumentException("Tekanan darah sistolik maximal 180 mmHg")
                    // 14 diastolicBP
                    if (diastolicBP == 0) throw IllegalArgumentException("Tekanan darah sistolik tidak boleh kosong atau 0")
                    if (diastolicBP!! < 60) throw IllegalArgumentException("Tekanan darah sistolik minimal 60 mmHg")
                    if (diastolicBP!! > 120) throw IllegalArgumentException("Tekanan darah sistolik maximal 120 mmHg")
                    // 15 fastingBloodSugar
                    Log.d("guladarahPuasa", fastingBloodSugar.toString())
                    if (fastingBloodSugar == 0) throw IllegalArgumentException("Kadar gula darah puasa tidak boleh kosong atau 0")
                    if (fastingBloodSugar!! < 70) throw IllegalArgumentException("Kadar gula darah puasa minimal 70 mg/dL")
                    if (fastingBloodSugar!! > 200) throw IllegalArgumentException("Kadar gula darah puasa maximal 200 mg/dL")
                    // 16 hbA1c
                    hbA1c = if (tmpHbA1c < 4) 4 else tmpHbA1c
                    // 17 cholesterolTotal
                    if (cholesterolTotal == 0) throw IllegalArgumentException("Kadar kolestrol tidak boleh kosong atau 0")
                    if (cholesterolTotal!! < 150) throw IllegalArgumentException("Kadar kolestrol minimal 150 mg/dL")
                    if (cholesterolTotal!! > 300) throw IllegalArgumentException("Kadar kolestrol maximal 300 mg/dL")
                    // 18 antihypertensiveMedications
                    antihypertensiveMedications = if (tmpAntihypertensiveMedications == "Ya") 1 else 0
                    // 19 antidiabeticMedications
                    antidiabeticMedications = if (tmpAntidiabeticMedications == "Ya") 1 else 0
                    // 20 frequentUrination
                    frequentUrination = if (tmpFrequentUrination == "Ya") 1 else 0
                    // 21 excessiveThirst
                    excessiveThirst = if (tmpExcessiveThirst == "Ya") 1 else 0
                    // 22 unexplainedWeightLoss
                    unexplainedWeightLoss = if (tmpUnexplainedWeightLoss == "Ya") 1 else 0
                    // 24 blurredVision
                    blurredVision = if (tmpBlurredVision == "Ya") 1 else 0
                    // 25 slowHealingSores
                    slowHealingSores = if (tmpSlowHealingSores == "Ya") 1 else 0
                    // 26tinglingHandsFeet
                    tinglingHandsFeet = if (tmpTinglingHandsFeet == "Ya") 1 else 0
                    // 28 heavyMetalsExposure
                    heavyMetalsExposure = if (tmpHeavyMetalsExposure == "Ya") 1 else 0
                    // 29 occupationalExposureChemicals
                    occupationalExposureChemicals = if (tmpOccupationalExposureChemicals == "Ya") 1 else 0
                    // 30 waterQuality
                    waterQuality = if (tmpWaterQuality == "Baik") 0 else 1

                    Log.d("UploadData", "initialised")

                    val userRequest = CalculateRequest(
                        age = age,
                        gender = gender,
                        bmi = bmi,
                        smoking = smoke,
                        alcoholConsumption = alcohol,
                        physicalActivity = physicalExercise,
                        dietQuality = dietQuality,
                        sleepQuality = sleepQuality,
                        familyHistoryDiabetes = familyHistoryDiabetes,
                        gestationalDiabetes = gestationalDiabetes,
                        previousPreDiabetes = previousPreDiabetes,
                        hypertension = hypertension,
                        systolicBP = systolicBP,
                        diastolicBP = diastolicBP,
                        fastingBloodSugar = fastingBloodSugar,
                        hbA1c = hbA1c,
                        cholesterolTotal = cholesterolTotal,
                        antihypertensiveMedications = antihypertensiveMedications,
                        antidiabeticMedications = antidiabeticMedications,
                        frequentUrination = frequentUrination,
                        excessiveThirst = excessiveThirst,
                        unexplainedWeightLoss = unexplainedWeightLoss,
                        fatigueLevels = fatigueLevels,
                        blurredVision = blurredVision,
                        slowHealingSores = slowHealingSores,
                        tinglingHandsFeet = tinglingHandsFeet,
                        qualityOfLifeScore = qualityOfLifeScore,
                        heavyMetalsExposure = heavyMetalsExposure,
                        occupationalExposureChemicals = occupationalExposureChemicals,
                        waterQuality = waterQuality,
                        medicalCheckupsFrequency = medicalCheckupsFrequency,
                        medicationAdherence = medicationAdherence,
                        healthLiteracy = healthLiteracy
                    )

                    Log.d("UploadData", "upload proccess")

                    addViewModel.uploadPrediction(userRequest)

                    Log.d("UserRequest", userRequest.toString())

                    Log.d("UploadData", "upload success")


                } catch (e: IllegalArgumentException) {
                    // Tangani error input
                    Log.e("InputValidationError", e.message.toString())
                    isloading(false)
                    Toast.makeText(this@CalculateActivity, "${e.message}", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Log.e("ExceptionMessage", e.message.toString())
                    if (e.message.toString() == "lateinit property tmpGender has not been initialized")
                        Toast.makeText(this@CalculateActivity, "Jenis kelamin diperlukan, harap pilih salah satu opsi", Toast.LENGTH_SHORT).show()
                    if (e.message.toString() == "lateinit property tmpSmoke has not been initialized")
                        Toast.makeText(this@CalculateActivity, "Apakah Anda Merokok?, harap pilih salah satu opsi", Toast.LENGTH_SHORT).show()
                    if (e.message.toString() == "lateinit property tmpFamilyHistoryDiabetes has not been initialized")
                        Toast.makeText(this@CalculateActivity, "Apakah keluarga anda memiliki sejarah diabetes?", Toast.LENGTH_SHORT).show()
                    if (e.message.toString() == "lateinit property tmpGestationalDiabetes has not been initialized")
                        Toast.makeText(this@CalculateActivity, "Apakah terjadi diabetes selama kehamilan?", Toast.LENGTH_SHORT).show()
                    if (e.message.toString() == "lateinit property tmpPreviousPreDiabetes has not been initialized")
                        Toast.makeText(this@CalculateActivity, "apakah kadar guladarah sempat tinggi atau prediabets?", Toast.LENGTH_SHORT).show()
                    if (e.message.toString() == "lateinit property tmpHypertension has not been initialized")
                        Toast.makeText(this@CalculateActivity, "Status hypertensi belum di isi, harap pilih salah satu opsi", Toast.LENGTH_SHORT).show()
                    if (e.message.toString() == "lateinit property tmpAntihypertensiveMedications has not been initialized")
                        Toast.makeText(this@CalculateActivity, "Harap pilih penggunaan obat antihipertensi", Toast.LENGTH_SHORT).show()
                    if (e.message.toString() == "lateinit property tmpAntidiabeticMedications has not been initialized")
                        Toast.makeText(this@CalculateActivity, "Harap pilih penggunaan obat antidiabetes", Toast.LENGTH_SHORT).show()
                    if (e.message.toString() == "lateinit property tmpFrequentUrination has not been initialized")
                        Toast.makeText(this@CalculateActivity, "Harap pilih status sering buang air kecil", Toast.LENGTH_SHORT).show()
                    if (e.message.toString() == "lateinit property tmpExcessiveThirst has not been initialized")
                        Toast.makeText(this@CalculateActivity, "Harap pilih status sering merasa haus", Toast.LENGTH_SHORT).show()
                    if (e.message.toString() == "lateinit property tmpUnexplainedWeightLoss has not been initialized")
                        Toast.makeText(this@CalculateActivity, "Harap pilih status penurunan berat badan", Toast.LENGTH_SHORT).show()
                    if (e.message.toString() == "lateinit property tmpBlurredVision has not been initialized")
                        Toast.makeText(this@CalculateActivity, "Harap pilih status penglihatan kabur", Toast.LENGTH_SHORT).show()
                    if (e.message.toString() == "lateinit property tmpSlowHealingSores has not been initialized")
                        Toast.makeText(this@CalculateActivity, "Harap pilih status luka sulit sembuh", Toast.LENGTH_SHORT).show()
                    if (e.message.toString() == "lateinit property tmpTinglingHandsFeet has not been initialized")
                        Toast.makeText(this@CalculateActivity, "Harap pilih status kesemutan", Toast.LENGTH_SHORT).show()
                    if (e.message.toString() == "lateinit property tmpHeavyMetalsExposure has not been initialized")
                        Toast.makeText(this@CalculateActivity, "Harap pilih status paparan logam berat", Toast.LENGTH_SHORT).show()
                    if (e.message.toString() == "lateinit property tmpOccupationalExposureChemicals has not been initialized")
                        Toast.makeText(this@CalculateActivity, "Harap pilih status paparan bahan kimia", Toast.LENGTH_SHORT).show()
                    if (e.message.toString() == "lateinit property tmpWaterQuality has not been initialized")
                        Toast.makeText(this@CalculateActivity, "Harap pilih status kualitas air", Toast.LENGTH_SHORT).show()

                    isloading(false)

                }
            }
        }
    }


    // Implementasi listener untuk menangani perubahan input
    override fun onTextChanged(item: FormItem.TextInput) {
        // Handle text changes, misalnya update di ViewModel atau simpan data
        Log.d("FormActivity", "from id ${item.id}, TextInput updated: ${item.text}")

        when(item.id){
            "age" -> age = item.text.toInt()
            "weight" -> weight = item.text.toDouble()
            "height" -> height = item.text.toInt()
            "systolicBP" -> systolicBP = item.text.toInt()
            "diastolicBP" -> diastolicBP = item.text.toInt()
            "fastingBloodSugar" -> fastingBloodSugar = item.text.toInt()
            "cholesterolTotal" -> cholesterolTotal = item.text.toInt()
        }

    }

    override fun onOptionSelected(item: FormItem.RadioButtonInput) {
        Log.d("FormActivity", "${item.id} : ${item.selectedOption}")

        when(item.id){
            "gender" -> tmpGender = item.selectedOption.toString()
            "smoke" -> tmpSmoke = item.selectedOption.toString()
            "familyHistoryDiabetes" -> tmpFamilyHistoryDiabetes = item.selectedOption.toString()
            "gestationalDiabetes" -> tmpGestationalDiabetes = item.selectedOption.toString()
            "previousPreDiabetes" -> tmpPreviousPreDiabetes = item.selectedOption.toString()
            "hypertension" -> tmpHypertension = item.selectedOption.toString()
            "antihypertensiveMedications" -> tmpAntihypertensiveMedications = item.selectedOption.toString()
            "antidiabeticMedications" -> tmpAntidiabeticMedications = item.selectedOption.toString()
            "frequentUrination" -> tmpFrequentUrination = item.selectedOption.toString()
            "excessiveThirst" -> tmpExcessiveThirst = item.selectedOption.toString()
            "unexplainedWeightLoss" -> tmpUnexplainedWeightLoss = item.selectedOption.toString()
            "blurredVision" -> tmpBlurredVision = item.selectedOption.toString()
            "slowHealingSores" -> tmpSlowHealingSores = item.selectedOption.toString()
            "tinglingHandsFeet" -> tmpTinglingHandsFeet = item.selectedOption.toString()
            "heavyMetalsExposure" -> tmpHeavyMetalsExposure = item.selectedOption.toString()
            "occupationalExposureChemicals" -> tmpOccupationalExposureChemicals = item.selectedOption.toString()
            "waterQuality" -> tmpWaterQuality = item.selectedOption.toString()
        }
    }

    override fun onSliderChanged(item: FormItem.SliderInput) {
        // Handle slider value change
        Log.d("FormActivity", "Slider value: ${item.currentValue}")
        when(item.id){
            "alcohol" -> alcohol = item.currentValue.toInt()
            "exercise" -> physicalExercise = item.currentValue.toInt()
            "dietQuality" -> dietQuality = item.currentValue.toInt()
            "sleepQuality" -> tmpSleepQuality = item.currentValue.toInt()
            "fatigueLevels" -> fatigueLevels = item.currentValue.toInt()
            "qualityOfLifeScore" -> qualityOfLifeScore = item.currentValue.toInt()
            "medicalCheckupsFrequency" -> medicalCheckupsFrequency = item.currentValue.toInt()
            "medicationAdherence" -> medicationAdherence = item.currentValue.toInt()
            "healthLiteracy" -> healthLiteracy = item.currentValue.toInt()
        }
    }

    private fun observeViewModel() {
        userViewModel.username.observe(this) { userName ->
            name = userName
            Log.d("userName", "name : $name")
        }
    }

    private fun isloading(status: Boolean){
        if (status){
            binding.submitLayout.isEnabled = false
            binding.submitLayout.alpha = 0.5f
            binding.loading.visibility = View.VISIBLE
        }else{
            binding.submitLayout.isEnabled = true
            binding.submitLayout.alpha = 1.0f
            binding.loading.visibility = View.GONE
        }
    }


    // TODO: Gunakan fungsi ini untuk menampilkan layout user yang terkena Diabetes
    private fun showDiabetesDetectedLayout() {
        val binding = LayoutDiabetesDetectedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNextDiabetesDetected.setOnClickListener {
            lifecycleScope.launch {
                val userPreference = UserPreference.getInstance(applicationContext)
                val user = authRepository.getUserSession().first()
                userPreference.saveDiabetesStatus(false, user.username) // False = Diabetes Detected
            }

            // TODO: bisa navigasi ke halaman utama atau menyimpan Hasil Prediksi ?
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

            finish()
        }
    }

    // TODO: Gunakan fungsi ini untuk menampilkan layout user yang tidak terkena Diabetes
    private fun showNoDiabetesLayout() {
        val binding = LayoutNoDiabetesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNextNoDiabetes.setOnClickListener {
            lifecycleScope.launch {
                val userPreference = UserPreference.getInstance(applicationContext)
                val user = authRepository.getUserSession().first()
                userPreference.saveDiabetesStatus(true, user.username) // True = No Diabetes Detected
            }
            // TODO: bisa navigasi ke halaman utama atau menyimpan Hasil Prediksi ?
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

            finish()
        }
    }
}