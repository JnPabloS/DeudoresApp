package com.example.deudoresapp.ui.update

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.deudoresapp.DeudoresApp
import com.example.deudoresapp.R
import com.example.deudoresapp.data.local.dao.DebtorDao
import com.example.deudoresapp.data.local.entities.Debtor
import com.example.deudoresapp.data.server.DebtorServer
import com.example.deudoresapp.databinding.FragmentUpdateBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class UpdateFragment : Fragment() {

    companion object {
        fun newInstance() = UpdateFragment()
    }

    private lateinit var viewModel: UpdateViewModel
    private var _binding : FragmentUpdateBinding? = null

    private val binding get() =_binding!!
    private var isSearching = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var idDebtor : String? = ""

        binding.updateButton.setOnClickListener {

            val debtorDao: DebtorDao = DeudoresApp.database.DebtorDao()
            val name = binding.nameEditText.text.toString()

            if(isSearching){  //Buscando
                //searchLocal(debtorDao, name, idDebtor)

                val db = Firebase.firestore
                db.collection("deudores").get().addOnSuccessListener {result ->
                    for (document in result){
                        val debtor: DebtorServer = document.toObject<DebtorServer>()
                        if(debtor.name == name){
                            idDebtor = debtor.id
                            with(binding){
                                phoneEditText.setText(debtor.phone)
                                amountEditText.setText(debtor.amount.toString())
                                binding.updateButton.text = getString(R.string.title_update)
                                isSearching = false
                            }
                        }
                    }
                }
            }else{      //Actualizando
                //updateLocal(idDebtor, debtorDao)
                val documentUpdate = HashMap<String, Any>()
                documentUpdate["name"] = binding.nameEditText.text.toString()
                documentUpdate["amount"] = binding.amountEditText.text.toString().toLong()
                documentUpdate["phone"] = binding.phoneEditText.text.toString()

                val db = Firebase.firestore
                idDebtor?.let { id -> db.collection("deudores").document(id).update(documentUpdate).addOnSuccessListener {
                    Toast.makeText(requireContext(), "Deudor actualizado", Toast.LENGTH_SHORT).show()
                } }

                binding.updateButton.text = getString(R.string.title_read)
                isSearching = true
                cleanWidgets()
            }
        }

        return root
    }

    private fun updateLocal(
        idDebtor: String,
        debtorDao: DebtorDao
    ) {
        val deudor = Debtor(
            id = idDebtor.toInt(),
            name = binding.nameEditText.text.toString(),
            phone = binding.phoneEditText.text.toString(),
            amount = binding.amountEditText.text.toString().toLong()
        )
        debtorDao.updateDebtor(deudor)
    }

    private fun searchLocal(
        debtorDao: DebtorDao,
        name: String,
        idDebtor: Int
    ) {
        var idDebtor1 = idDebtor
        val debtor: Debtor = debtorDao.readDebtor(name)
        if (debtor != null) {
            idDebtor1 = debtor.id
            binding.amountEditText.setText(debtor.amount.toString())
            binding.phoneEditText.setText(debtor.phone)
            binding.updateButton.text = getString(R.string.title_update)
            isSearching = false
        } else
            Toast.makeText(requireContext(), getString(R.string.dont_exist), Toast.LENGTH_SHORT)
                .show()
    }

    private fun cleanWidgets() {
        with(binding){
            nameEditText.setText("")
            phoneEditText.setText("")
            amountEditText.setText("")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UpdateViewModel::class.java)
        // TODO: Use the ViewModel
    }

}