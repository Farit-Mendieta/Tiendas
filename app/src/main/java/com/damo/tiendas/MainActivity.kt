package com.damo.tiendas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.damo.tiendas.databinding.ActivityMainBinding
import java.util.concurrent.LinkedBlockingQueue

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var nAdapter: StoreAdapter
    private lateinit var nGridLayout: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            val store = StoreEntity(name = binding.etName.text.toString().trim())
            Thread{
                StoreApplication.database.storeDao().addStore(store)
            }.start()

            nAdapter.add(store)
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        nAdapter = StoreAdapter(mutableListOf(), this)
        nGridLayout = GridLayoutManager(this, 2)
        getStores()

        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = nGridLayout
            adapter = nAdapter
        }
    }

    private fun getStores(){
        val queue = LinkedBlockingQueue<MutableList<StoreEntity>>()
        Thread{
            var stores = StoreApplication.database.storeDao().getAllStores()
            queue.add(stores)
        }.start()

        nAdapter.setStores(queue.take())
    }

    override fun onFavoriteStore(storeEntity: StoreEntity) {
        storeEntity.isFavorite = !storeEntity.isFavorite
        val queue = LinkedBlockingQueue<StoreEntity>()
        Thread{
            StoreApplication.database.storeDao().updateStore(storeEntity)
            queue.add(storeEntity)
        }.start()
        nAdapter.update(queue.take())
    }

    override fun onDeleteStore(storeEntity: StoreEntity) {
        val queue = LinkedBlockingQueue<StoreEntity>()
        Thread{
            StoreApplication.database.storeDao().deleteStore(storeEntity)
            queue.add(storeEntity)
        }.start()
        nAdapter.delete(queue.take())
    }

    override fun onClick(storeEntity: StoreEntity) {
        TODO("Not yet implemented")
    }
}