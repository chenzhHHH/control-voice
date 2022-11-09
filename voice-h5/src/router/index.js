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
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
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
