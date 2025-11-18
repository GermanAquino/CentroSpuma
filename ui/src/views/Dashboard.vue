<template>
  <div class="dashboard">
    <!-- Barra lateral fija -->
    <Sidebar class="sidebar" />

    <!-- Contenido principal -->
    <div class="main-content">
      <Navbar userName="Seba Kisser" />

      <section class="content">
        <h2>Resumen de ventas</h2>
        <SearchBar @search="handleSearch" />

        <SalesTable :sales="filteredSales" />

        <Pagination
          :page="page"
          :totalPages="totalPages"
          @prev="changePage(page - 1)"
          @next="changePage(page + 1)"
        />
      </section>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import Sidebar from "../components/Sidebar.vue";
import Navbar from "../components/Navbar.vue";
import SearchBar from "../components/SearchBar.vue";
import SalesTable from "../components/SalesTable.vue";
import Pagination from "../components/Pagination.vue";
import { getVentas } from "../services/VentasService";

const sales = ref([]);
const filters = ref({ client: "", date: "" });
const page = ref(0);
const totalPages = ref(1);

// Cargar ventas desde el backend
const loadSales = async () => {
  try {
    const data = await getVentas(page.value, 10);

    // mapeamos para frontend
    sales.value = data.content.map(s => ({
      id: s.id,
      cliente: s.clienteNombre,
      clienteRuc: s.clienteRuc, // agregamos RUC
      fecha: new Date(s.fecha).toLocaleDateString(), // opcional: formatear fecha
      total: s.total,
      producto: s.detalles
        .map(d => `${d.productoNombre} (x${d.cantidad}, ${d.precio})`) // nombre, cantidad y precio
        .join(", ")
    }));

    totalPages.value = data.totalPages;
  } catch (error) {
    console.error("Error al cargar ventas:", error);
  }
};

onMounted(() => {
  loadSales();
});

// Filtrado por cliente o fecha
const filteredSales = computed(() => {
  return sales.value.filter(s => {
    return (
      (!filters.value.client || s.clienteNombre.toLowerCase().includes(filters.value.client.toLowerCase())) &&
      (!filters.value.date || s.fecha === filters.value.date)
    );
  });
});

const handleSearch = (f) => {
  filters.value = f;
};

const changePage = async (p) => {
  if (p >= 0 && p < totalPages.value) {
    page.value = p;
    await loadSales();
  }
};
</script>

<style scoped>
:global(html, body, #app) {
  margin: 0;
  padding: 0;
  height: 100%;
  width: 100%;
  overflow: hidden;
}

.dashboard {
  display: flex;
  height: 100vh;
  width: 100vw;
  overflow: hidden;
}

.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  background: #f9f9f9;
  overflow-y: auto;
  overflow-x: hidden;
}

.content {
  flex: 1;
  padding: 24px;
  box-sizing: border-box;
}

h2 {
  margin-top: 0;
}
</style>
