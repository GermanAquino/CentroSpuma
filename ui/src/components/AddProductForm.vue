<template>
  <div v-if="visible" class="modal-overlay" @click.self="close">
    <div class="modal-content">
      <h3 class="mb-3">Añadir nuevo producto</h3>

      <form @submit.prevent="submitForm">
        <div class="mb-3">
          <label class="form-label">Nombre</label>
          <input v-model="form.nombre" type="text" class="form-control" required />
        </div>

        <div class="mb-3">
          <label class="form-label">Descripción</label>
          <textarea v-model="form.descripcion" class="form-control" rows="2"></textarea>
        </div>

        <div class="mb-3">
          <label class="form-label">Precio</label>
          <input v-model.number="form.precio" type="number" min="0" class="form-control" required />
        </div>

        <div class="mb-3">
          <label class="form-label">Stock</label>
          <input v-model.number="form.stock" type="number" min="0" class="form-control" required />
        </div>

        <!-- SELECT MULTIPLE PARA CATEGORÍAS -->
        <div class="mb-3">
          <label class="form-label">Categorías</label>
          <select v-model="form.categoriaIds" class="form-select" multiple required>
            <option v-for="cat in categorias" :key="cat.id" :value="cat.id">
              {{ cat.nombre }}
            </option>
          </select>
          <small class="text-muted">Mantén Ctrl (Windows) o Cmd (Mac) para seleccionar varias.</small>
        </div>

        <div class="d-flex justify-content-end mt-4">
          <button type="button" class="btn btn-secondary me-2" @click="close">
            Cancelar
          </button>
          <button type="submit" class="btn btn-primary">Guardar</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";

const props = defineProps({
  visible: Boolean
});
const emits = defineEmits(["close", "saved"]);

const form = ref({
  nombre: "",
  descripcion: "",
  precio: null,
  stock: 0,
  categoriaIds: []   
});

const categorias = ref([]);

const loadCategorias = async () => {
  try {
    const response = await axios.get("http://localhost:8080/categorias?page=0&size=50");
    categorias.value = response.data.content || [];
  } catch (error) {
    console.error("Error al cargar categorías:", error);
  }
};

onMounted(() => {
  loadCategorias();
});

const submitForm = async () => {
  try {
    const productData = {
      nombre: form.value.nombre,
      descripcion: form.value.descripcion,
      precio: form.value.precio,
      stock: form.value.stock,
      categoriaIds: [form.value.categoriaId] // 👈 formato correcto
    };

    console.log("📦 Enviando producto:", productData);

    await axios.post("http://localhost:8080/productos", productData);
    alert("Producto agregado correctamente ✅");
    emits("saved");
    close();
  } catch (error) {
    console.error("Error al guardar producto:", error);
    alert("Ocurrió un error al guardar el producto");
  }
};

const close = () => {
  emits("close");
};
</script>

<style scoped>
/* Fondo oscuro del modal */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.4);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 2000;
}

/* Caja blanca del formulario */
.modal-content {
  background-color: white;
  border-radius: 12px;
  padding: 24px;
  width: 400px;
  max-width: 90%;
  box-shadow: 0 5px 25px rgba(0, 0, 0, 0.2);
}
</style>
