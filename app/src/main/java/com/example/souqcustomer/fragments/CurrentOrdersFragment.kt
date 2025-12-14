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
import com.example.souqcustomer.adapters.CurrentOrdersAdapter
import com.example.souqcustomer.databinding.FragmentCurrentOrdersBinding
import com.example.souqcustomer.interface0.OnClick
import com.example.souqcustomer.viewModel.OrderViewModel

class CurrentOrders : Fragment() {

    private lateinit var binding: FragmentCurrentOrdersBinding
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
        binding = FragmentCurrentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = requireContext()
            .getSharedPreferences("souq_prefs", AppCompatActivity.MODE_PRIVATE)

        userId = prefs.getInt("USER_ID", 0)

        viewModel.getConfirmedOrders(userId)

        viewModel.observeConfirmedOrders().observe(viewLifecycleOwner) { orders ->

            // الطلبات الحالية فقط
            val currentOrders = orders.filter {
                it.status == "CONFIRMED" ||
                        it.status == "PREPARING" ||
                        it.status == "OUT_FOR_DELIVERY"
            }

            if (currentOrders.isEmpty()) {
                // 🔴 ما في طلبات
                binding.tvNoOrders.visibility = View.VISIBLE
                binding.rvCurrentOrders.visibility = View.GONE
            } else {
                // 🟢 في طلبات
                binding.tvNoOrders.visibility = View.GONE
                binding.rvCurrentOrders.visibility = View.VISIBLE

                val adapter = CurrentOrdersAdapter(
                    currentOrders,
                    object : OnClick {
                        override fun OnClick(index: Int) {
                            val orderId = currentOrders[index].id
                            val intent = Intent(
                                requireContext(),
                                OrdersDetalisActivity::class.java
                            )
                            intent.putExtra("orderId", orderId)
                            startActivity(intent)
                        }
                    }
                )

                binding.rvCurrentOrders.layoutManager =
                    LinearLayoutManager(requireContext())
                binding.rvCurrentOrders.adapter = adapter
            }
        }
    }
}
