package com.example.surimusakotlin.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.surimusakotlin.databinding.RegistrationFragmentBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegistrationFragment : Fragment() {

    private lateinit var binding: RegistrationFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RegistrationFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backToAuth.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.buttonRegistration.setOnClickListener {
            if (binding.emailEdittext.text.toString().isEmpty() || binding.passwordEdittext.text.toString().isEmpty() || binding.usernameEdittext.text.toString().isEmpty()){
                Toast.makeText(context, "Поля должны быть заполнены", Toast.LENGTH_SHORT).show()
            }else{
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(binding.emailEdittext.text.toString(),binding.passwordEdittext.text.toString())
                    .addOnCompleteListener(object: OnCompleteListener<AuthResult> {
                        override fun onComplete(task: Task<AuthResult>) {
                            if (task.isSuccessful()){
                                val userInfo: HashMap<String, String> = HashMap()
                                userInfo.put("email", binding.emailEdittext.text.toString())
                                userInfo.put("username", binding.usernameEdittext.text.toString())
                                userInfo.put("password", binding.passwordEdittext.text.toString())
                                FirebaseDatabase.getInstance().getReference().child("Users").child(
                                    FirebaseAuth.getInstance()
                                    .currentUser!!.uid).setValue(userInfo)
                                Toast.makeText(
                                    context,
                                    "Регистрация завершена успешно",
                                    Toast.LENGTH_SHORT
                                ).show()
                                findNavController().popBackStack()
                            }

                        }
                    })
            }
        }
    }
}