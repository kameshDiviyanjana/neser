package com.example.cataract_and_conjunctivitis_detection
import com.example.cataract_and_conjunctivitis_detection.Constants.OPEN_GOOGLE
import com.example.cataract_and_conjunctivitis_detection.Constants.OPEN_SEARCH
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
object BotResponse {

    fun basicResponses(_message: String): String {

        val random = (0..2).random()
        val message =_message.toLowerCase()

        return when {
                    message.contains("what is a cataract")  -> {
                        "A cataract is a clouding of the eye's natural lens, which can lead to blurred vision. It often develops with age but can be treated with surgery."
                    }

                    message.contains("cataract symptoms")  -> {
                        "Common symptoms of cataracts include blurry vision, sensitivity to light, difficulty seeing at night, and seeing halos around lights."
                    }

                    message.contains("what causes cataracts")  -> {
                        "Cataracts can be caused by aging, exposure to UV radiation, smoking, diabetes, and certain medications. Genetics can also play a role."
                    }

                    message.contains("cataract treatment")  -> {
                        "The primary treatment for cataracts is surgery, where the clouded lens is replaced with an artificial one. It's a common and safe procedure."
                    }

                    message.contains("cataract surgery")  -> {
                        "Cataract surgery involves removing the cloudy lens and replacing it with an intraocular lens (IOL). It's typically an outpatient procedure."
                    }

                    message.contains("tell me about cataracts") -> {
                        "Cataracts are a common eye condition that can affect vision. If you have specific questions or need more information, feel free to ask."
                    }

                    message.contains("how to prevent cataracts")  -> {
                        "You can reduce the risk of cataracts by protecting your eyes from UV radiation, not smoking, and maintaining a healthy lifestyle with a balanced diet."
                    }

                    message.contains("cataract surgery recovery") -> {
                        "After cataract surgery, it's important to follow your doctor's instructions for recovery. Most people experience improved vision within a few days."
                    }

                    message.contains("can cataracts be cured") -> {
                        "While cataracts can't be cured with medication, they can be effectively treated with surgery to restore clear vision."
                    }
            //Flips a coin
            message.contains("flip") && message.contains("coin") -> {
                val r = (0..1).random()
                val result = if (r == 0) "heads" else "tails"

                "I flipped a coin and it landed on $result"
            }

            //Math calculations
            message.contains("solve") -> {
                val equation: String? = message.substringAfterLast("solve")
                return try {
                    val answer = SolveMath.solveMath(equation ?: "0")
                    "$answer"

                } catch (e: Exception) {
                    "Sorry, I can't solve that."
                }
            }

            //Hello
            message.contains("hello") -> {
                when (random) {
                    0 -> "Hello there!"
                    1 -> "Sup"
                    2 -> "Buongiorno!"
                    else -> "error" }
            }

            //How are you?
            message.contains("how are you") -> {
                when (random) {
                    0 -> "I'm doing fine, thanks!"
                    1 -> "I'm hungry..."
                    2 -> "Pretty good! How about you?"
                    else -> "error"
                }
            }

            //What time is it?
            message.contains("time") && message.contains("?")-> {
                val timeStamp = Timestamp(System.currentTimeMillis())
                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
                val date = sdf.format(Date(timeStamp.time))

                date.toString()
            }

            //Open Google
            message.contains("open") && message.contains("google")-> {
                OPEN_GOOGLE
            }

            //Search on the internet
            message.contains("search")-> {
                OPEN_SEARCH
            }

            //When the program doesn't understand...
            else -> {
                when (random) {
                    0 -> "I'm not sure about that. Please ask another question related to cataracts."
                    1 -> "I may not have the answer to that. Try asking something else about cataracts."
                    2 -> "Sorry, I couldn't understand your question. Feel free to ask about cataracts."
                    else -> "I don't have the information you're looking for at the moment."
                }
            }
        }
    }
}
