<template>
  <div class="dashboard">
    <Sidebar />
    <main>
      <Navbar userName="Seba Kisser" />
      <h2>Resumen de ventas</h2>
      <SearchBar @search="handleSearch" />
      <SalesTable :sales="filteredSales" />
      <Pagination :page="page" :totalPages="totalPages" 
                  @prev="page--" @next="page++"/>
    </main>
  </div>
</template>

<style scoped>
.dashboard {
  display: flex;             /* 🔹 Sidebar + main lado a lado */
  min-height: 100vh;
}

/* El contenido principal */
.dashboard-main {
  flex: 1;                   /* 🔹 Ocupa el resto del ancho */
  padding: 1rem;
  background: #f9f9f9;
}
</style>

<script setup>
import { ref, computed } from "vue"
import Sidebar from "../components/Sidebar.vue"
import Navbar from "../components/Navbar.vue"
import SearchBar from "../components/SearchBar.vue"
import SalesTable from "../components/SalesTable.vue"
import Pagination from "../components/Pagination.vue"

const sales = ref([
  { cliente: "Juan Pérez", fecha: "20-02-2025", vendedor: "Seba Kisser", metodoPago: "Efectivo", documento: "Ticket", producto: "Poliuretano 500ml", total: "30.000" },
  { cliente: "Maria Gómez", fecha: "20-02-2025", vendedor: "Daniel Ferreira", metodoPago: "Crédito", documento: "Ticket", producto: "Poliuretano 250ml", total: "18.000" }
  // ... más registros
])

const filters = ref({ client: "", date: "" })
const page = ref(1)
const totalPages = ref(1)

const filteredSales = computed(() => {
  return sales.value.filter(s => {
    return (!filters.value.client || s.cliente.toLowerCase().includes(filters.value.client.toLowerCase())) &&
           (!filters.value.date || s.fecha === filters.value.date)
  })
})

const handleSearch = (f) => {
  filters.value = f
}
</script>