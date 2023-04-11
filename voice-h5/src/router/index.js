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
    path: '/my',
    name: 'my',
    component: () => import('../views/MyView.vue')
  },
  {
    path: '/record/:wordId',
    name: 'record',
    component: () => import('../views/RecordView.vue')
  }
  // {
  //   path: '/report/:examGroupId',
  //   name: 'report',
  //   component: () => import('../views/ReportView.vue')
  // }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
