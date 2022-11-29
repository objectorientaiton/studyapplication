package com.example.team_16

import android.os.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.team_16.Repository.StopwatchRepository
import com.example.team_16.databinding.FragmentStopwatchBinding
import com.example.team_16.ViewModel.StopwatchViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


@RequiresApi(Build.VERSION_CODES.O)
@Suppress("DEPRECATION")
class StopwatchFragment : Fragment() {

    private val stopwatch = StopwatchRepository()
    private var binding: FragmentStopwatchBinding? = null
    private lateinit var data: HashMap<String, *>
    var major: String? = null
    val viewModel: StopwatchViewModel by activityViewModels()
    //var flag = midnight()


    private val yesterday = SimpleDateFormat("yyyy-MM-dd")
        .format(Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24))
    private val today = SimpleDateFormat("yyyy-MM-dd")
        .format(Date(System.currentTimeMillis())) //오늘 날짜
    val tomorrow = SimpleDateFormat("yyyy-MM-dd")
        .format(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStopwatchBinding.inflate(inflater)

        stopwatch.userRef.get().addOnSuccessListener { document ->
            major = document["department"] as String?
        }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.hour.observe(viewLifecycleOwner){
            binding?.txtHour?.setText(String.format("%02d", it))
        }
        viewModel.min.observe(viewLifecycleOwner){
            binding?.txtMin?.setText(String.format("%02d", it))
        }
        viewModel.sec.observe(viewLifecycleOwner){
            binding?.txtSec?.setText(String.format("%02d", it))
        }
        viewModel.mil.observe(viewLifecycleOwner){
            binding?.txtMil?.setText(String.format("%02d", it))
        }

        binding?.btnStart?.setOnClickListener {
            viewModel.timerStart()
        }
        binding?.btnStop?.setOnClickListener {
            viewModel.timerStop()
            data = stopwatch.makeHash(viewModel.hour.value?.toLong(), viewModel.min.value?.toLong(),
                viewModel.sec.value?.toLong(), viewModel.mil.value?.toLong(), viewModel.timebuff.value?.toLong(), major)
            //if(flag == 1) stopwatch.setData(tomorrow, data)
            stopwatch.setData(today, data)
        }
    }

    override fun onStop() {
        super.onStop()
        data = stopwatch.makeHash(viewModel.hour.value?.toLong(), viewModel.min.value?.toLong(),
            viewModel.sec.value?.toLong(), viewModel.mil.value?.toLong(), viewModel.timebuff.value?.toLong(), major)
        //if(flag == 1) stopwatch.setData(tomorrow, data)
        stopwatch.setData(today, data)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}


