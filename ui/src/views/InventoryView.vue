<template>
  <div class="inventory-dashboard">
    <Sidebar />

    <div class="main-content">
      <div class="header">
        <SearchBar placeholder="Buscar producto..." />
      </div>

      <h2 class="title">Filtros</h2>

      <div class="cards-grid">
        <ProductCard
          v-for="(product, index) in products"
          :key="index"
          :name="product.name"
          :image="product.image"
        />
      </div>

      <FloatingButton @click="addProduct" />
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue"
import Sidebar from "../components/Sidebar.vue"
import SearchBar from "../components/SearchBar.vue"
import ProductCard from "../components/ProductCard.vue"
import FloatingButton from "../components/FloatingButton.vue"

const products = ref([
  { name: "Poliuretano 500ml", image: "/images/sample1.png" },
  { name: "Espuma 250ml", image: "/images/sample2.png" },
  { name: "Silicona Blanca", image: "/images/sample3.png" },
  { name: "Sellador Gris", image: "/images/sample4.png" },
  { name: "Pistola aplicadora", image: "/images/sample5.png" },
  { name: "Removedor industrial", image: "/images/sample6.png" },
])

const addProduct = () => {
  console.log("Nuevo producto")
}
</script>

<style scoped>
.inventory-dashboard {
  display: flex;
  height: 100vh;
  width: 100vw;
  overflow: hidden;
}

.main-content {
  flex: 1;
  background-color: #fff;
  padding: 32px;
  overflow-y: auto;
}

.header {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 24px;
}

.title {
  margin: 0 0 16px 0;
  font-size: 1.4rem;
  font-weight: 600;
}

.cards-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
  gap: 24px;
  padding-bottom: 60px; /* espacio para el botón flotante */
}
</style>
