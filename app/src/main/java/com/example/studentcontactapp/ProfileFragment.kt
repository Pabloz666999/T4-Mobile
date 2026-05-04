package com.example.studentcontactapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.studentcontactapp.utils.PrefManager
import com.example.studentcontactapp.utils.SettingsManager
import com.google.android.material.switchmaterial.SwitchMaterial

class ProfileFragment : Fragment() {

    private lateinit var prefManager: PrefManager
    private lateinit var settingsManager: SettingsManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        prefManager = PrefManager(requireContext())
        settingsManager = SettingsManager(requireContext())

        val tvProfileName = view.findViewById<TextView>(R.id.tvProfileName)
        val switchDarkMode = view.findViewById<SwitchMaterial>(R.id.switchDarkMode)
        val switchFontSize = view.findViewById<SwitchMaterial>(R.id.switchFontSize)
        val switchNotification = view.findViewById<SwitchMaterial>(R.id.switchNotification)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)

        // 1. Tampilkan Nama User (supaya muncul "Welcome, admin")
        val username = prefManager.getUsername()
        tvProfileName.text = "Welcome, $username!"

        // 2. Set Status Awal Switch
        switchDarkMode.isChecked = settingsManager.isDarkMode()
        switchNotification.isChecked = settingsManager.isNotificationEnabled()
        // Cek apakah font size yang tersimpan adalah 18 (Besar)
        switchFontSize.isChecked = settingsManager.getFontSize() == 18

        // 3. Listener Dark Mode
        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            settingsManager.setDarkMode(isChecked)
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        // 4. Listener Font Size
        switchFontSize.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                settingsManager.setFontSize(18)
            } else {
                settingsManager.setFontSize(14)
            }
            // Memberikan toast informasi bahwa perubahan font memerlukan restart
            Toast.makeText(requireContext(), "Font size diubah! Perubahan akan terlihat setelah restart.", Toast.LENGTH_SHORT).show()
            
            // Opsional: Restart activity untuk menerapkan secara langsung (agak berat)
            // activity?.recreate()
        }

        // 5. Listener Notifikasi
        switchNotification.setOnCheckedChangeListener { _, isChecked ->
            settingsManager.setNotificationEnabled(isChecked)
        }

        // 6. Listener Logout
        btnLogout.setOnClickListener {
            prefManager.logout()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        return view
    }
}