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
          @prev="page--"
          @next="page++"
        />
      </section>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from "vue"
import Sidebar from "../components/Sidebar.vue"
import Navbar from "../components/Navbar.vue"
import SearchBar from "../components/SearchBar.vue"
import SalesTable from "../components/SalesTable.vue"
import Pagination from "../components/Pagination.vue"

const sales = ref([
  {
    cliente: "Juan Pérez",
    fecha: "20-02-2025",
    vendedor: "Seba Kisser",
    metodoPago: "Efectivo",
    documento: "Ticket",
    producto: "Poliuretano 500ml",
    total: "30.000",
  },
  {
    cliente: "Maria Gómez",
    fecha: "20-02-2025",
    vendedor: "Daniel Ferreira",
    metodoPago: "Crédito",
    documento: "Ticket",
    producto: "Poliuretano 250ml",
    total: "18.000",
  },
])

const filters = ref({ client: "", date: "" })
const page = ref(1)
const totalPages = ref(1)

const filteredSales = computed(() => {
  return sales.value.filter((s) => {
    return (
      (!filters.value.client ||
        s.cliente.toLowerCase().includes(filters.value.client.toLowerCase())) &&
      (!filters.value.date || s.fecha === filters.value.date)
    )
  })
})

const handleSearch = (f) => {
  filters.value = f
}
</script>

<style scoped>
/* 🔹 Eliminar cualquier margen del body y asegurar altura completa */
:global(html, body, #app) {
  margin: 0;
  padding: 0;
  height: 100%;
  width: 100%;
  overflow: hidden; /* evita scroll horizontal */
}

/* 🔹 Layout principal */
.dashboard {
  display: flex;
  height: 100vh;
  width: 100vw;
  overflow: hidden;
}

/* 🔹 Contenedor principal */
.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  background: #f9f9f9;
  overflow-y: auto;
  overflow-x: hidden;
}

/* 🔹 Contenido del dashboard */
.content {
  flex: 1;
  padding: 24px;
  box-sizing: border-box;
}

h2 {
  margin-top: 0;
}
</style>
