<template>
  <RouterLink to="/">
    <p style="font-weight: 600; color: cornflowerblue">To catalog</p>
  </RouterLink>
  <form @submit.prevent="getOrders">
    <input v-model="email" type="email" placeholder="Order email" />
    <button type="submit">Get orders</button>
  </form>
  <p v-if="orders === null">Enter email first</p>
  <p v-else-if="!orders?.length">No orders</p>
  <table v-for="order in ordersEnhanced" :key="order.id" style="width: 1000px">
    <p>Order info:</p>
    <p>Id: #{{ order.id }}</p>
    <p>Status: {{ order.status }}</p>
    <tr>
      <th>NAME</th>
      <th>COLOR</th>
      <th>MANUFACTURER</th>
      <th>PRICE</th>
      <th>IMAGE</th>
      <th>QUANTITY</th>
    </tr>
    <tr v-for="p in order.products" :key="p.productId">
      <td>{{ p.product.name }}</td>
      <td>{{ p.product.color }}</td>
      <td>{{ p.product.manufacturer }}</td>
      <td>{{ p.product.price }}</td>
      <td>
        <img :src="p.product.image" :alt="p.product.name" style="height: 100px; width: 100px" />
      </td>
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
<style scoped>
.wrapper {
  display: flex;
  justify-content: center;
  align-items: normal;
  flex-direction: column;
  width: 1000px;
  padding: 0;
  margin: auto;
}
.table-container {
  width: 1000px;
}
.form {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 1000px;
  flex-direction: row;
  margin-top: 50px;
}
input {
  width: 220px;
  height: 50px;
  margin: 5px auto;
  border-radius: 10px;
  padding-left: 15px;
  color: black;
  text-align: left;
  font-family: Montserrat;
  font-size: 20px;
  font-style: normal;
  font-weight: 300;
  line-height: normal;
}
.product {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 1000px;
  flex-direction: row;
  padding-bottom: 20px;
}
p {
  font-family: Montserrat;
  font-size: 20px;
  font-style: normal;
  font-weight: 300;
  line-height: normal;
  color: black;
  text-align: center;
}
th {
  font-family: Montserrat;
  font-size: 20px;
  font-style: normal;
  font-weight: 600;
  line-height: normal;
  color: black;
  text-align: center;
}
td {
  font-family: Montserrat;
  font-size: 20px;
  font-style: normal;
  font-weight: 300;
  line-height: normal;
  color: black;
  text-align: center;
}
button {
  font-family: Montserrat;
  font-size: 20px;
  font-style: normal;
  font-weight: 300;
  line-height: normal;
  color: black;
  outline: none;
  border: none;
  background-color: rgb(171, 200, 254);
}
</style>
