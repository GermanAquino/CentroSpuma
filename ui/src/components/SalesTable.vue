<template>
  <table class="sales-table">
    <thead>
      <tr>
        <th>N°</th>
        <th>Cliente</th>
        <th>Fecha</th>
        <th>Vendedor</th>
        <th>Método Pago</th>
        <th>Documento</th>
        <th>Producto</th>
        <th>Total</th>
        <th>Acciones</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="(sale, index) in sales" :key="index">
        <td>{{ index + 1 }}</td>
        <td>{{ sale.cliente }}</td>
        <td>{{ sale.fecha }}</td>
        <td>{{ sale.vendedor }}</td>
        <td>{{ sale.metodoPago }}</td>
        <td>{{ sale.documento }}</td>
        <td>{{ sale.producto }}</td>
        <td>{{ sale.total }}</td>
        <td>
          <button @click="$emit('view', sale)">👁</button>
          <button @click="$emit('edit', sale)">✏️</button>
          <button @click="$emit('delete', sale)">🗑</button>
        </td>
      </tr>
    </tbody>
  </table>
</template>

<script setup>
defineProps({
  sales: Array
})
</script>

<style scoped>
.sales-table {
  width: 100%;
  border-collapse: collapse;
}
.sales-table th, .sales-table td {
  border: 1px solid #ccc;
  padding: 0.5rem;
}
</style>