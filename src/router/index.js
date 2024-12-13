import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import Employees from '../views/EmployeeView.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/greeting',
      name: 'greeting',
      component: () => import('../views/GreetingView.vue'),
    },
    {
      path: '/search',
      name: 'search',
      component: () => import('../views/SearchDictionary.vue'),
    },
    {
      path: '/calculator',
      name: 'calculator',
      component: () => import('../views/CalculatorView.vue'),
    },
    {
      path: '/employees',
      name: 'employees',
      component: Employees,
    },
    {
      path: '/about',
      name: 'about',
      component: () => import('../views/AboutView.vue'),
    },
  ],
})

export default router
