package com.joxoo.navigationrechner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.joxoo.navigationrechner.databinding.FragmentTaschenrechnerBinding
import com.joxoo.navigationrechner.model.RechnerViewModel


class TaschenrechnerFragment : Fragment() {
    private lateinit var binding: FragmentTaschenrechnerBinding
    private val viewModel: RechnerViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentTaschenrechnerBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaschenrechnerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.textValue.observe(viewLifecycleOwner) {
            binding.eingabeTV.text = it
        }
        listOf(
            binding.nullBTN,
            binding.einsBTN,
            binding.zweiBTN,
            binding.dreiBTN,
            binding.vierBTN,
            binding.fuenfBTN,
            binding.sechsBTN,
            binding.siebenBTN,
            binding.achtBTN,
            binding.neunBTN
        ).forEachIndexed { index, button ->
            button.setOnClickListener {
                viewModel.addNumber(index)
            }
        }

        binding.plusBTN.setOnClickListener {
            viewModel.addOperator("+")
        }
        binding.minusBTN.setOnClickListener {
            viewModel.addOperator("-")
        }
        binding.malBTN.setOnClickListener {
            viewModel.addOperator("*")
        }
        binding.durchBTN.setOnClickListener {
            viewModel.addOperator("/")
        }
        binding.gleichBTN.setOnClickListener {
            val result = viewModel.calculate()
            findNavController().navigate(TaschenrechnerFragmentDirections.actionTaschenrechnerFragmentToErgebnisFragment(result))

        }

        binding.acBTN.setOnClickListener {
            viewModel.reset()
        }

    }
}