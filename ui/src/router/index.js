import { createRouter, createWebHistory } from "vue-router"
import Dashboard from "../views/Dashboard.vue"
import InventoryView from "../views/InventoryView.vue"

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
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router