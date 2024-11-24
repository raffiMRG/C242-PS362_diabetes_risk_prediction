package com.capstone.diabticapp.ui.history

data class ExampleHistory(val date: String, val status: String) {
    companion object {
        fun getDefaultInstance(): List<ExampleHistory> {
            val exampleHistory = listOf(
                ExampleHistory("Wed, 7 Nov", "Negative"),
                ExampleHistory("Wed, 8 Nov", "Negative"),
                ExampleHistory("Wed, 9 Nov", "Positive"),
                ExampleHistory("Wed, 10 Nov", "Negative"),
                ExampleHistory("Wed, 11 Nov", "Positive"),
                ExampleHistory("Wed, 7 Nov", "Negative"),
                ExampleHistory("Wed, 8 Nov", "Negative"),
                ExampleHistory("Wed, 9 Nov", "Positive"),
                ExampleHistory("Wed, 10 Nov", "Negative"),
                ExampleHistory("Wed, 11 Nov", "Positive")
            )
            return exampleHistory
        }
    }
}
