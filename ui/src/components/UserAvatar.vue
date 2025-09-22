<template>
  <div class="avatar">
    <img :src="src" :alt="alt" class="avatar-img" />
  </div>
</template>

<script setup>
defineProps({
  src: {
    type: String,
    required: true
  },
  alt: {
    type: String,
    default: "User Avatar"
  },
  size: {
    type: Number,
    default: 60   // tamaño en px
  }
})
</script>

<style scoped>
.avatar {
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  overflow: hidden;
  border: 2px solid #fff;
  background: #ddd;
  width: v-bind(size + 'px');
  height: v-bind(size + 'px');
}

.avatar-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
</style>