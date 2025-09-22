<template>
  <div class="search-bar">
    <input 
      v-model="searchClient" 
      type="text" 
      placeholder="Cliente"
    />
    <input 
      v-model="searchDate" 
      type="date" 
    />
    <button @click="emitSearch">🔍</button>
  </div>
</template>

<script setup>
import { ref } from "vue"

const searchClient = ref("")
const searchDate = ref("")

const emit = defineEmits(["search"])
const emitSearch = () => {
  emit("search", { client: searchClient.value, date: searchDate.value })
}
</script>

<style scoped>
.search-bar {
  display: flex;
  gap: 0.5rem;
  margin: 1rem 0;
}
</style>