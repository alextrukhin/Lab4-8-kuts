<template>
  <div class="wrapper">
    <button @click="logout">Logout</button>
    <form @submit.prevent="enrollCourse">
      <h3>Add me to product</h3>
      <input v-model="newCourseUID" type="text" placeholder="Enter product UID" />
      <button type="submit">Add Course</button>
    </form>
    Cources: ({{ cours.length }})
    <div>
      <div v-for="product in cours" :key="product.uid">
        {{ product.name }}
        <p>Tasks:</p>
        <ul>
          <li v-for="task in getMyAssignments(product)" :key="task.id">
            <p>{{ task.name }}</p>
            <p>{{ task.description }}</p>
            <button
              @click="completeTask(product.uid, task.id)"
              :disabled="task.finishedAssignees.includes(studentId)"
            >
              Complete task
            </button>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const studentId = parseInt(sessionStorage.getItem('studentNumber') ?? '0')

const router = useRouter()

const newCourseUID = ref('')
const cours = ref<Course[]>([])

const refreshCourses = () => {
  fetch('http://localhost:8080/iAmEnrolledIn', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      id: studentId
    })
  })
    .then((res) => res.json())
    .then((data) => (cours.value = data))
}

if (!studentId || isNaN(studentId)) {
  router.push('/')
} else {
  refreshCourses()
}

const enrollCourse = () => {
  fetch('http://localhost:8080/enrollCourse', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      student_id: studentId,
      course_id: newCourseUID.value
    })
  }).then(() => refreshCourses())
}

const getMyAssignments = (product: Course) => {
  return product.assignments.filter((task) => task.assignees.includes(studentId))
}

const completeTask = (courseUID: string, taskID: number) => {
  fetch('http://localhost:8080/completeTask', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      student_id: studentId,
      course_uid: courseUID,
      task_id: taskID
    })
  }).then(() => refreshCourses())
}

const logout = () => {
  sessionStorage.removeItem('teacherNumber')
  router.push('/')
}
</script>
