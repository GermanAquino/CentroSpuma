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
            :key="product.id || index"
            :name="product.nombre"
            :image="product.image"
            />
      </div>

      <FloatingButton @click="addProduct" />

    </div>
  </div>
  <AddProductForm :visible="showForm" @close="showForm = false" @saved="loadProducts" />
</template>

<script setup>
import { ref, onMounted } from "vue";
import Sidebar from "../components/Sidebar.vue";
import SearchBar from "../components/SearchBar.vue";
import ProductCard from "../components/ProductCard.vue";
import FloatingButton from "../components/FloatingButton.vue";
import AddProductForm from "../components/AddProductForm.vue";
import { addProducto, getProductos } from "../services/productosService";

const placeholderImage = "https://i1.wp.com/gelatologia.com/wp-content/uploads/2020/07/placeholder.png?ssl=1";
const products = ref([]);
const page = ref(0);
const totalPages = ref(1);
const showForm = ref(false);

const loadProducts = async () => {
  try {
    const data = await getProductos(page.value, 20);
    products.value = data.content.map((p) => ({
      ...p,
      image: placeholderImage,
    }));
    totalPages.value = data.totalPages;
  } catch (error) {
    console.error("Error al cargar productos:", error);
  }
};

const changePage = async (p) => {
  if (p >= 0 && p < totalPages.value) {
    page.value = p;
    await loadProducts();
  }
};

const addProduct = () => {
  showForm.value = true;
};

const handleSaveProduct = async (newProduct) => {
  console.log("📦 Enviando producto:", newProduct);
  try {
    
    const saved = await addProducto(newProduct);
    products.value.unshift({
      ...saved,
      image: placeholderImage,
    });
    alert("Producto agregado correctamente");
  } catch (error) {
    console.error("Error al agregar producto:", error);
    alert("No se pudo agregar el producto");
  }
};

onMounted(() => {
  loadProducts();
});
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
