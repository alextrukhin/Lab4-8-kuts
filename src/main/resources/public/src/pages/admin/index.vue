<template>
  <div>{{ products }}</div>
</template>
<script setup lang="ts">
import { ref } from 'vue'
import { Product } from '@/types'

const products = ref<Product[]>([])

const refreshProducts = () => {
  fetch('http://localhost:8080/getProducts')
    .then((res) => res.json())
    .then((data) => (products.value = data))
}
refreshProducts()
</script>
