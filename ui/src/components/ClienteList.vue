<template>
  <div>
    <h2>Clientes</h2>
    <ul>
      <li v-for="cliente in clientes" :key="cliente.id">
        {{ cliente.nombre }} - {{ cliente.telefono }}
      </li>
    </ul>
    <button @click="cargarPagina(prevPage)" :disabled="pagina === 0">Anterior</button>
    <button @click="cargarPagina(nextPage)" :disabled="pagina >= totalPaginas - 1">Siguiente</button>
  </div>
</template>

<script>
import { ref, onMounted, computed } from 'vue';
import { getClientes } from '../services/clienteService.js';


export default {
  setup() {
    const clientes = ref([]);
    const pagina = ref(0);
    const totalPaginas = ref(0);

    const cargarClientes = async (page = 0) => {
      const data = await getClientes(0, 10);
      clientes.value = data.content;
      pagina.value = data.number;
      totalPaginas.value = data.totalPages;
    };

    const prevPage = computed(() => pagina.value - 1);
    const nextPage = computed(() => pagina.value + 1);

    const cargarPagina = (p) => cargarClientes(p);

    onMounted(() => cargarClientes());

    return { clientes, pagina, totalPaginas, cargarPagina, prevPage, nextPage };
  },
};
</script>
