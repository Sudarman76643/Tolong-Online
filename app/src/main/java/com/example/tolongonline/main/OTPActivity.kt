package com.example.tolongonline.main

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tolongonline.databinding.ActivityOtpactivityBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class OTPActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpactivityBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    private lateinit var verificationId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        progressDialog.setCancelable(false)

        binding.sendOtpButton.setOnClickListener {
            val phoneNumber = binding.phoneEditText.text.toString().trim()

            if (phoneNumber.isEmpty()) {
                binding.phoneEditText.error = "Nomor HP tidak boleh kosong"
                return@setOnClickListener
            }

            sendOtp(phoneNumber)
        }

        binding.verifyOtpButton.setOnClickListener {
            val otp = binding.otpEditText.text.toString().trim()

            if (otp.isEmpty()) {
                binding.otpEditText.error = "Kode OTP harus diisi"
                return@setOnClickListener
            }

            verifyOtp(otp)
        }
    }

    private fun sendOtp(phoneNumber: String) {
        progressDialog.setMessage("Mengirim OTP...")
        progressDialog.show()

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    signInWithCredential(credential)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    progressDialog.dismiss()
                    Toast.makeText(this@OTPActivity, "Gagal mengirim OTP: ${e.message}", Toast.LENGTH_LONG).show()
                }

                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                    progressDialog.dismiss()
                    Toast.makeText(this@OTPActivity, "OTP berhasil dikirim", Toast.LENGTH_SHORT).show()
                    this@OTPActivity.verificationId = verificationId
                }
            })
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun verifyOtp(otp: String) {
        progressDialog.setMessage("Memverifikasi OTP...")
        progressDialog.show()

        val credential = PhoneAuthProvider.getCredential(verificationId, otp)
        signInWithCredential(credential)
    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                progressDialog.dismiss()
                if (task.isSuccessful) {
                    Toast.makeText(this, "Verifikasi berhasil!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finishAffinity()
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(this, "Kode OTP salah", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Verifikasi gagal: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
    }
}
