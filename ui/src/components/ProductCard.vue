<template>
  <div class="product-card">
    <img v-if="image" :src="image" alt="Producto" />
    <div class="product-info">
      <p>{{ name }}</p>
    </div>
  </div>
</template>

<script setup>
defineProps({
  name: String,
  image: String,
})
</script>

<style scoped>
.product-card {
  background-color: #f8f6fc;
  border-radius: 16px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  padding: 16px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  transition: transform 0.2s ease;
  cursor: pointer;
}

.product-card:hover {
  transform: translateY(-4px);
}

.product-card img {
  width: 80px;
  height: 80px;
  object-fit: contain;
  margin-bottom: 10px;
}

.product-info p {
  font-weight: 500;
  text-align: center;
}
</style>
