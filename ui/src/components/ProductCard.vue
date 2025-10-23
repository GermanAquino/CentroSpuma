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
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.15);
  padding: 20px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  transition: transform 0.2s ease;
  cursor: pointer;
  width: 220px;  /* ancho fijo opcional */
}

.product-card:hover {
  transform: translateY(-6px);
}

.product-card img {
  width: 120px;          /* más grande */
  height: 120px;         /* más grande */
  object-fit: cover;     /* mantiene proporción y ocupa más área */
  border-radius: 12px;   /* bordes redondeados opcionales */
  margin-bottom: 12px;
}

.product-info p {
  font-weight: 600;
  text-align: center;
  font-size: 1.1rem;     /* un poco más grande */
}
</style>
