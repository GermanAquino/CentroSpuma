<template>
  <header class="navbar">
    <h1>BIENVENIDO, {{ userName }}!</h1>
  </header>
</template>

<script setup>
defineProps({
  userName: String
})
</script>

<style scoped>
.navbar {
  padding: 1rem;
  font-size: 1.5rem;
  font-weight: bold;
}
</style>