package com.example.team_16.Repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore



@RequiresApi(Build.VERSION_CODES.O)
class StopwatchRepository {
    private val email = FirebaseAuth.getInstance().currentUser?.email ?: "None"
    private val db = FirebaseFirestore.getInstance()
    val userRef = db.collection("Users").document("$email")
    val timeRef = userRef.collection("studyTime")

    fun makeHash(
        Hours: Long?, Minutes: Long?, Seconds: Long?, MilliSeconds: Long?,
        TimeBuff: Long?, Major: String?): HashMap<String, *> {
        val data = hashMapOf(
            "hour" to Hours.toString(),
            "minute" to Minutes.toString(),
            "second" to Seconds.toString(),
            "millisecond" to MilliSeconds.toString(),
            "timeBuff" to TimeBuff.toString(),
            "major" to Major
        )
        return data
    }

    fun setData(date: String, data: HashMap<String, *>){
        timeRef.document(date)
            .set(data).addOnSuccessListener {
                Log.v("알림", "데이터가 들어갔습니다")
            }
            .addOnFailureListener { exception ->
                Log.w("MainActivity", "Error getting documents: $exception")
            }
    }
}