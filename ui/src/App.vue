<template>
  <div id="app">
    <Dashboard />
  </div>
</template>

<script setup>
import Dashboard from "./views/Dashboard.vue"
</script>

<style>
/* Reset básico */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: Arial, Helvetica, sans-serif;
}

#app {
  display: flex;
  min-height: 100vh;
}
</style>