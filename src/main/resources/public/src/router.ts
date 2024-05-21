import { createWebHistory, createRouter } from 'vue-router'

import IndexPage from './pages/index.vue'
import AdminPage from './pages/admin/index.vue'
import AdminProductsPage from './pages/admin/products.vue'
import AdminOrdersPage from './pages/admin/orders.vue'
import StudentPage from './pages/student.vue'
import TeacherPage from './pages/teacher.vue'

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
      path: '/admin/products',
      name: 'admin-products',
      component: AdminProductsPage
    },
    {
      path: '/admin/orders',
      name: 'admin-orders',
      component: AdminOrdersPage
    },
    {
      path: '/student',
      name: 'student',
      component: StudentPage
    },
    {
      path: '/teacher',
      name: 'teacher',
      component: TeacherPage
    }
  ]
})

export default router
