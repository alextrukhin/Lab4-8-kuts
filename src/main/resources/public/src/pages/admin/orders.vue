<template>
  <div>{{ orders }}</div>
</template>
<script setup lang="ts">
import { ref } from 'vue'
import { Order } from '@/types'

const orders = ref<Order[]>([])

const refreshOrders = () => {
  fetch('http://localhost:8080/getOrders')
    .then((res) => res.json())
    .then((data) => (orders.value = data))
}
refreshOrders()
</script>
