import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import PollList from '../components/Polls/PollList.vue'
import PollForm from '../components/Polls/PollForm.vue'

const routes: RouteRecordRaw[] = [
    { path: '/polls', component: PollList },
    { path: '/test', component: PollForm },
]

export const router = createRouter({
    history: createWebHistory(),
    routes,
})
