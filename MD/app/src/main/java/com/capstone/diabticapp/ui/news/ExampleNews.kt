package com.capstone.diabticapp.ui.news

data class ExampleNews(val title: String, val image: String, val description: String){
    companion object{
        fun getDefaultInstance(): List<ExampleNews>{
            val exampleNews = listOf(
                ExampleNews("Tips Sederhana Darius Sinathrya Donna Agnesia Lindungi Keluarga dari Diabetes",
                    "https://akcdn.detik.net.id/community/media/visual/2024/11/18/darius-sinathrya-dan-donna-agnesia_169.jpeg?w=700&q=90",
                    "Jakarta - Pasangan artis Darius Sinathrya dan Donna Agnesia membeberkan rahasia mencegah penyakit diabetes di keluarganya. Hal tersebut diungkapkan saat sesi talkshow di acara #Hands4Diabetes yang diselenggarakan oleh Tropicana Slim pada Minggu (17/11/2024) di Lapangan Banteng, Jakarta Pusat.\n" +
                            "Sebagai seorang kepala keluarga, Darius selalu menekankan pentingnya menjaga pola hidup sehat di dalam lingkungan keluarganya. Itu menurunya menjadi bentuk kasih sayang untuk dirinya sendiri dan juga untuk seluruh anggota keluarganya.\n" +
                            "\n" +
                            "Ya, pasti ya (menjaga pola hidup sehat), pasti kita semua melakukan sesuatu hal yang positif, yang baik, termasuk juga menjaga gaya hidup yang sehat, menjaga kesehatan kita. Kita lakukan itu dengan kesadaran penuh bahwa kita harus ada selama mungkin dan hidup bersama selama mungkin (dengan) orang-orang yang kita sayang, yaitu adalah keluarga kita, katanya.\n"
                ),

                ExampleNews("Cara Mencegah Diabetes ala Orang Jepang, Ternyata Sesimpel Ini",
                    "https://akcdn.detik.net.id/community/media/visual/2024/03/19/ilustrasi-orang-jepang-makan_169.jpeg?w=700&q=90",
                    "Jakarta - Diabetes menjadi salah satu penyakit kronis yang menjadi perhatian dunia, termasuk di Indonesia. Namun, prevalensi penyakit diabetes di Jepang diketahui relatif rendah dibandingkan negara-negara lain.\n" +
                            "Hal ini dipengaruhi gaya hidup sehat yang dijalani masyarakatnya. Kemungkinan ini dipengaruhi kombinasi berbagai faktor, mulai dari aktivitas fisik hingga pola makan yang sehat.\n" +
                            "\n" +
                            "Apa saja kebiasaan orang Jepang yang dapat mencegah penyakit diabetes?\n"
                ),
                ExampleNews("Tips Sederhana Darius Sinathrya Donna Agnesia Lindungi Keluarga dari Diabetes",
                    "https://akcdn.detik.net.id/community/media/visual/2024/11/18/darius-sinathrya-dan-donna-agnesia_169.jpeg?w=700&q=90",
                    "Jakarta - Pasangan artis Darius Sinathrya dan Donna Agnesia membeberkan rahasia mencegah penyakit diabetes di keluarganya. Hal tersebut diungkapkan saat sesi talkshow di acara #Hands4Diabetes yang diselenggarakan oleh Tropicana Slim pada Minggu (17/11/2024) di Lapangan Banteng, Jakarta Pusat.\n" +
                            "Sebagai seorang kepala keluarga, Darius selalu menekankan pentingnya menjaga pola hidup sehat di dalam lingkungan keluarganya. Itu menurunya menjadi bentuk kasih sayang untuk dirinya sendiri dan juga untuk seluruh anggota keluarganya.\n" +
                            "\n" +
                            "Ya, pasti ya (menjaga pola hidup sehat), pasti kita semua melakukan sesuatu hal yang positif, yang baik, termasuk juga menjaga gaya hidup yang sehat, menjaga kesehatan kita. Kita lakukan itu dengan kesadaran penuh bahwa kita harus ada selama mungkin dan hidup bersama selama mungkin (dengan) orang-orang yang kita sayang, yaitu adalah keluarga kita, katanya.\n"
                ),

                ExampleNews("Cara Mencegah Diabetes ala Orang Jepang, Ternyata Sesimpel Ini",
                    "https://akcdn.detik.net.id/community/media/visual/2024/03/19/ilustrasi-orang-jepang-makan_169.jpeg?w=700&q=90",
                    "Jakarta - Diabetes menjadi salah satu penyakit kronis yang menjadi perhatian dunia, termasuk di Indonesia. Namun, prevalensi penyakit diabetes di Jepang diketahui relatif rendah dibandingkan negara-negara lain.\n" +
                            "Hal ini dipengaruhi gaya hidup sehat yang dijalani masyarakatnya. Kemungkinan ini dipengaruhi kombinasi berbagai faktor, mulai dari aktivitas fisik hingga pola makan yang sehat.\n" +
                            "\n" +
                            "Apa saja kebiasaan orang Jepang yang dapat mencegah penyakit diabetes?\n"
                )
                )
            return exampleNews
        }
    }
}