package com.example.deudoresapp.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.deudoresapp.R
import com.example.deudoresapp.data.entities.Debtor

class DetailFragment : Fragment() {

    private val args : DetailFragmentArgs by navArgs()

    companion object {
        fun newInstance() = DetailFragment()
    }

    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        val debtor : Debtor = args.debtor
        Toast.makeText(requireContext(), debtor.name + " me debe " + debtor.amount.toString() + " \nTeléfono: " + debtor.phone, Toast.LENGTH_SHORT).show()
    }

}