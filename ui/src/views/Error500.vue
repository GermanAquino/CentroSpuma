<template>
  <div class="error-page">
    <div class="error-content">
      <h1>500</h1>
      <p>Ocurrió un error inesperado en el servidor.</p>
      <p class="sub">Por favor, intentá nuevamente más tarde.</p>

      <router-link to="/" class="btn-primary">Volver al inicio</router-link>
    </div>
  </div>
</template>

<script setup>
</script>

<style scoped>
.error-page {
  height: 100vh;
  width: 100vw;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f4f6fa; /* un gris suave como tu dashboard */
}

.error-content {
  text-align: center;
  padding: 40px;
  background: white;
  border-radius: 14px;
  box-shadow: 0 8px 18px rgba(0, 0, 0, 0.1);
  border: 2px solid #0c2d63;
}

.error-content h1 {
  font-size: 90px;
  margin: 0;
  font-weight: 700;
  color: #0c2d63;
}

.error-content p {
  margin: 12px 0;
  font-size: 18px;
}

.sub {
  font-size: 14px;
  color: #666;
  margin-bottom: 30px;
}

.btn-primary {
  display: inline-block;
  padding: 10px 18px;
  background: #0c2d63;
  color: white;
  border-radius: 6px;
  text-decoration: none;
  font-weight: bold;
}

.btn-primary:hover {
  background: #0a254c;
}
</style>
