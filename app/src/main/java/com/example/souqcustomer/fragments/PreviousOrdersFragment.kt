package com.example.souqcustomer.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.souqcustomer.activities.OrdersDetalisActivity
import com.example.souqcustomer.adapters.previousOrdersAdapter
import com.example.souqcustomer.databinding.FragmentPreviousOrdersBinding
import com.example.souqcustomer.interface0.OnClick
import com.example.souqcustomer.viewModel.OrderViewModel

class PreviousOrdersFragment : Fragment() {

    private lateinit var binding: FragmentPreviousOrdersBinding
    private lateinit var viewModel: OrderViewModel
    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[OrderViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPreviousOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = requireContext()
            .getSharedPreferences("souq_prefs", AppCompatActivity.MODE_PRIVATE)

        userId = prefs.getInt("USER_ID", 0)

        viewModel.getConfirmedOrders(userId)

        viewModel.observeConfirmedOrders().observe(viewLifecycleOwner) { orders ->

            // الطلبات السابقة فقط
            val previousOrders = orders.filter {
                it.status == "DELIVERED"
            }

            if (previousOrders.isEmpty()) {
                // 🔴 ما في طلبات سابقة
                binding.tvNoPreviousOrders.visibility = View.VISIBLE
                binding.rvPreviousOrders.visibility = View.GONE
            } else {
                // 🟢 في طلبات
                binding.tvNoPreviousOrders.visibility = View.GONE
                binding.rvPreviousOrders.visibility = View.VISIBLE

                val adapter = previousOrdersAdapter(
                    previousOrders,
                    object : OnClick {
                        override fun OnClick(index: Int) {
                            val orderId = previousOrders[index].id
                            val intent = Intent(
                                requireContext(),
                                OrdersDetalisActivity::class.java
                            )
                            intent.putExtra("orderId", orderId)
                            startActivity(intent)
                        }
                    }
                )

                binding.rvPreviousOrders.layoutManager =
                    LinearLayoutManager(requireContext())
                binding.rvPreviousOrders.adapter = adapter
            }
        }
    }
}
