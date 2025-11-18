import { createRouter, createWebHistory } from "vue-router";
import Dashboard from "../views/Dashboard.vue";
import InventoryView from "../views/InventoryView.vue";
import UsuariosView from "../views/UsuariosView.vue";
import VentasView from "../views/VentasView.vue";
import ReportesView from "../views/ReportesView.vue";
import FacturacionView from "../views/FacturacionView.vue";
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
    path: "/ventas",
    name: "ventas",
    component: VentasView,
  },
  /*{
    path: "/facturacion",
    name: "facturacion",
    component: FacturacionView,
  },*/
  {
    path: "/reportes",
    name: "reportes",
    component: ReportesView,
  },
  {
    path: "/usuarios",
    name: "usuarios",
    component: UsuariosView,
  },
  {
    path: "/:pathMatch(.*)*",
    name: "Error404",
    component: () => import("../views/Error404.vue"),
  },
  {
    path: "/500",
    name: "Error500",
    component: () => import("../views/Error500.vue"),
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
