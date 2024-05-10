package com.example.surimusakotlin.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.surimusakotlin.R
import com.example.surimusakotlin.databinding.FragmentLoginBinding
import com.example.surimusakotlin.presentation.MainActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (FirebaseAuth.getInstance().currentUser != null) {
            findNavController().navigate(R.id.progressFragment)
        }
        (activity as? MainActivity)?.let {
            it.binding.bottomNavigation.visibility = View.GONE
        }

        binding.buttonRegistrationInAuth.setOnClickListener{
            val destination = LoginFragmentDirections.actionLoginFragmentToRegistrationFragment()
            findNavController().navigate(destination)
        }


        binding.buttonAuth.setOnClickListener{
            if (binding.emailEdittext.text.toString().isEmpty() || binding.passwordEdittext.text.toString().isEmpty()){
                Toast.makeText(context, "Поля должны быть заполнены", Toast.LENGTH_SHORT).show()
            }else{
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(binding.emailEdittext.text.toString(), binding.passwordEdittext.text.toString())
                    .addOnCompleteListener(object: OnCompleteListener<AuthResult> {
                        override fun onComplete(task: Task<AuthResult>) {
                            if (task.isSuccessful()){
                                val destination = LoginFragmentDirections.actionLoginFragmentToProgressFragment()
                                findNavController().navigate(destination)
                            }
                            else{
                                Toast.makeText(
                                    context,
                                    "Неверный логин или пароль",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })
            }
        }
    }

}