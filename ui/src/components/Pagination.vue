<template>
  <div class="pagination">
    <button @click="$emit('prev')" :disabled="page <= 1">Previous</button>
    <span>Página {{ page }} de {{ totalPages }}</span>
    <button @click="$emit('next')" :disabled="page >= totalPages">Next</button>
  </div>
</template>

<script setup>
defineProps({
  page: Number,
  totalPages: Number
})
</script>