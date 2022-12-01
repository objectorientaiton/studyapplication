package com.example.team_16

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.team_16.databinding.FragmentMypageAndLogoutBinding
import com.google.firebase.auth.FirebaseAuth


class MypageAndLogoutFragment : Fragment() {


    var binding : FragmentMypageAndLogoutBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentMypageAndLogoutBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding?.btnLogout?.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(activity, "로그아웃에 성공하였습니다.", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_mypageAndLogoutFragment_to_entryFragment)
        }
    }

}