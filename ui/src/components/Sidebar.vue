<template>
  <aside class="sidebar">
    <div class="logo">
      <img src="/logo.png" alt="Centro Spuma" />
      <h2>Centro Spuma</h2>
    </div>

    <div class="user">
      <UserAvatar :src="userImage" />
    </div>

    <nav>
      <ul>
        <li><a href="#">Inicio</a></li>
        <li><a href="#">Inventario</a></li>
        <li><a href="#">Ventas</a></li>
        <li><a href="#">Facturación</a></li>
        <li><a href="#">Reportes</a></li>
        <li><a href="#">Usuarios</a></li>
      </ul>
    </nav>
  </aside>
</template>

<script setup>
import UserAvatar from "./UserAvatar.vue"

const userImage = "/logo.png"
</script>

<style scoped>
.sidebar {
  width: 240px;       /* ancho fijo */
  min-height: 100vh;  /* ocupa toda la altura */
  background: #0c2d63;
  color: white;
  padding: 1rem;
}
.logo {
  text-align: center;
  margin-bottom: 2rem;
}
nav ul {
  list-style: none;
  padding: 0;
}
nav ul li {
  margin: 1rem 0;
}
nav ul li a {
  color: white;
  text-decoration: none;
  font-weight: bold;
}
</style>
