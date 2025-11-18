<template>
  <button class="floating-btn" @click="$emit('click')">+</button>
</template>

<style scoped>
.floating-btn {
  position: fixed;
  bottom: 32px;
  right: 32px;
  width: 48px;
  height: 48px;
  border-radius: 50%;
  border: none;
  background-color: #f5f3ff;
  color: #333;
  font-size: 24px;
  cursor: pointer;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
  transition: background 0.2s ease;
}

.floating-btn:hover {
  background-color: #ece8ff;
}
</style>
