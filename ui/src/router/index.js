import { createRouter, createWebHistory } from "vue-router";
import Dashboard from "../views/Dashboard.vue";
import InventoryView from "../views/InventoryView.vue";
import ClientesView from "../views/ClientesView.vue"; // <-- Importa la vista de clientes

const routes = [
  {
    path: "/",
    name: "dashboard",
    component: Dashboard,
  },
  {
    path: "/inventario",
    name: "inventario",
    component: InventoryView,
  },
  {
    path: "/clientes", // <-- Nueva ruta
    name: "clientes",
    component: ClientesView,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
