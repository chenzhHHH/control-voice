import { createRouter, createWebHashHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const routes = [
  {
    path: '/',
    name: 'index',
    component: () => import('../views/IndexView.vue'),
    meta: {
      requireAuth: true,
      showTab: true
    },
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
    component: () => import('../views/CourseView.vue'),
    meta: {
      requireAuth: true,
      showTab: true
    }
  },
  {
    path: '/my',
    name: 'my',
    component: () => import('../views/MyView.vue'),
    meta: {
      requireAuth: true,
      showTab: true
    }
  },
  {
    path: '/record/:wordId',
    name: 'record',
    component: () => import('../views/RecordView.vue'),
    meta: {
      requireAuth: true,
      showTab: false
    }
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/LoginView.vue')
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  if (to.meta.requireAuth) {
    if (localStorage.getItem('token')) {
      next();
    } else {
      next({
        path: '/login',
      })
    }
  } else {
    next();
  }
})

export default router
