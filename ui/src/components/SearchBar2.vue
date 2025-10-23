<template>
  <div class="search-bar2">
    <input
      type="text"
      :placeholder="placeholder"
      v-model="query"
      @input="$emit('search', query)"
    />
  </div>
</template>

<script setup>
import { ref } from "vue"

defineProps({
  placeholder: String,
})

const query = ref("")
</script>

<style scoped>
.search-bar2 {
  width: 320px;
  position: relative;
}

.search-bar2 input {
  width: 100%;
  padding: 10px 16px;
  border-radius: 20px;
  border: 1px solid #ddd;
  outline: none;
  font-size: 14px;
}
</style>
