import Vue from 'vue'
import Router from 'vue-router'
import Introduction from '@/components/Introduction'
import Lesson1 from '@/components/Lesson1'
import Lesson2 from '@/components/Lesson2'
import Lesson3 from '@/components/Lesson3'
import Lesson4 from '@/components/Lesson4'
import Lesson5 from '@/components/Lesson5'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'introduction',
      component: Introduction
    },
    {
      path: '/lesson1',
      name: 'Lesson1',
      component: Lesson1
    },
    {
      path: '/lesson2',
      name: 'Lesson2',
      component: Lesson2
    },
    {
      path: '/lesson3',
      name: 'Lesson3',
      component: Lesson3
    },
    {
      path: '/lesson4',
      name: 'Lesson4',
      component: Lesson4
    },
    {
      path: '/lesson5',
      name: 'Lesson5',
      component: Lesson5
    }
  ]
})
