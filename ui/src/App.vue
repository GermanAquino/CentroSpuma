<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script setup>
// No hace falta importar Dashboard ni InventoryView aquí.
// El router se encargará de renderizar la vista correcta.
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: Arial, Helvetica, sans-serif;
}

#app {
  display: flex;
  min-height: 100vh;
  width: 100vw;
}
</style>