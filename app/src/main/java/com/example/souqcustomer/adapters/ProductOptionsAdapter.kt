package com.example.souqcustomer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.souqcustomer.R
import com.example.souqcustomer.databinding.ItemOptionGroupBinding
import com.example.souqcustomer.pojo.ProductOptionsItem
import com.google.android.material.chip.Chip

class ProductOptionsAdapter(
    private val onSelectionChanged: (optionId: Int, valueId: Int) -> Unit
) : RecyclerView.Adapter<ProductOptionsAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemOptionGroupBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val items = mutableListOf<ProductOptionsItem>()
    private val selectedMap = mutableMapOf<Int, Int>() // optionId -> valueId

    fun submitList(newItems: List<ProductOptionsItem>) {
        items.clear()
        items.addAll(newItems.sortedBy { it.sort_order })
        notifyDataSetChanged()
    }

    fun currentSelection(): Map<Int, Int> = selectedMap.toMap()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOptionGroupBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val option = items[position]
        val ctx = holder.itemView.context
        val chipGroup = holder.binding.chipGroup

        holder.binding.tvGroupTitle.text = option.label
        chipGroup.isSingleSelection = option.selection.equals("single", ignoreCase = true)
        chipGroup.isSelectionRequired = option.required == 1
        chipGroup.removeAllViews()

        val selectedValueId = selectedMap[option.id]

        option.values.sortedBy { it.sort_order }.forEach { value ->
            // انفخ Chip بمظهر الماتيريال الجاهز
            val chip = Chip(ctx).apply {
                // ضيف ستايل الـChoice Chip
                setTextAppearance(com.google.android.material.R.style.TextAppearance_MaterialComponents_Body2)
                isCheckable = true
                isClickable = true
                isCheckedIconVisible = false
                text = value.label
            }

            // ألوانك (selector) عشان لما يتحدد يصير mauve والنص أبيض
            chip.chipBackgroundColor = ContextCompat.getColorStateList(ctx, R.color.chip_bg_color)
            chip.setTextColor(ContextCompat.getColorStateList(ctx, R.color.chip_text_color))
            chip.isCheckedIconVisible = false

            // فعّل التحديد الحالي (لو موجود)
            chip.isChecked = (selectedValueId != null && selectedValueId == value.id)

            chip.setOnClickListener {
                // single: آخر اختيار يغطي القديم
                selectedMap[option.id] = value.id
                onSelectionChanged(option.id, value.id)

                val pos = holder.bindingAdapterPosition
                if (pos != RecyclerView.NO_POSITION) notifyItemChanged(pos)
            }

            chipGroup.addView(chip)
        }
    }

    override fun getItemCount(): Int = items.size
}
