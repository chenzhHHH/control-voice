import { createRouter, createWebHashHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const routes = [
  {
    path: '/',
    name: 'index',
    component: () => import('../views/IndexView.vue'),
    redirect: '/home',
    children: [
      {
        path: '/home',
        name: 'home',
        component: HomeView
      }
    ]
  },
  {
    path: '/course',
    name: 'course',
    component: () => import('../views/CourseView.vue')
  },
  {
    path: '/learn/:category',
    name: 'learn',
    component: () => import('../views/LearnView.vue')
  },
  {
    path: '/report/:examGroupId',
    name: 'report',
    component: () => import('../views/ReportView.vue')
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
