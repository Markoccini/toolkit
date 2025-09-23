import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Polls from '../components/Polls.vue'

const routes: RouteRecordRaw[] = [
    { path: '/', component: Polls },
]

export const router = createRouter({
    history: createWebHistory(),
    routes,
})
