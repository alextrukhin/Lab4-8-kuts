import { createWebHistory, createRouter } from 'vue-router'

import IndexPage from './pages/index.vue'
import AdminPage from './pages/admin/index.vue'
import AdminOrdersPage from './pages/admin/orders.vue'
import OrdersPage from './pages/orders.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: IndexPage
    },
    {
      path: '/admin',
      name: 'admin',
      component: AdminPage
    },
    {
      path: '/admin/orders',
      name: 'admin-orders',
      component: AdminOrdersPage
    },
    {
      path: '/orders',
      name: 'orders',
      component: OrdersPage
    }
  ]
})

export default router
