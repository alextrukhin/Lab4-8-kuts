<template>
  <RouterLink to="/">
    <p>To catalog</p>
  </RouterLink>
  <form @submit.prevent="getOrders">
    <input v-model="email" type="email" placeholder="Order email" />
    <button type="submit">Get orders</button>
  </form>
  <p v-if="orders === null">Enter email first</p>
  <p v-else-if="!orders?.length">No orders</p>
  <table v-for="order in ordersEnhanced" :key="order.id">
    <p>#{{ order.id }}</p>
    <p>{{ order.status }}</p>
    <p>{{ order.trackingNumber }}</p>
    <tr>
      <th>Product name</th>
      <th>Product color</th>
      <th>Product manufacturer</th>
      <th>Product price</th>
      <th>Product image</th>
      <th>Quantity</th>
    </tr>
    <tr v-for="p in order.products" :key="p.productId">
      <td>{{ p.product.name }}</td>
      <td>{{ p.product.color }}</td>
      <td>{{ p.product.manufacturer }}</td>
      <td>{{ p.product.price }}</td>
      <td><img :src="p.product.image" :alt="p.product.name" /></td>
      <td>{{ p.quantity }}</td>
    </tr>
  </table>
</template>
<script setup lang="ts">
import { computed, ref } from 'vue'
import { Order, Product } from '@/types'

const email = ref('')
const products = ref<Product[]>([])
const orders = ref<Order[] | null>(null)

const ordersEnhanced = computed(
  () =>
    orders.value?.map((order) => ({
      ...order,
      products: order.products.map((orderProduct) => ({
        ...orderProduct,
        product: products.value.find((p) => p.id === orderProduct.productId)!
      }))
    })) ?? []
)

const getOrders = () => {
  fetch(`http://localhost:8080/getMyOrders`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ email: email.value })
  })
    .then((res) => res.json())
    .then((data) => (orders.value = data))
}

const refreshProducts = () => {
  fetch('http://localhost:8080/getProducts')
    .then((res) => res.json())
    .then((data) => (products.value = data))
}
refreshProducts()
</script>
