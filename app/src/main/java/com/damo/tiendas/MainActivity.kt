package com.damo.tiendas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.damo.tiendas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var nAdapter: StoreAdapter
    private lateinit var nGridLayout: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            val store = Store(name = binding.etName.text.toString().trim())
            nAdapter.add(store)
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        nAdapter = StoreAdapter(mutableListOf(), this)
        nGridLayout = GridLayoutManager(this, 2)

        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = nGridLayout
            adapter = nAdapter
        }
    }

    override fun onClick(store: Store) {
        TODO("Not yet implemented")
    }
}