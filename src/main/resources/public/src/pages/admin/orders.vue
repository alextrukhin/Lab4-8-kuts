<template>
  <RouterLink to="/admin">
    <p style="font-weight: 600; color: cornflowerblue">View products</p>
  </RouterLink>
  <table v-for="order in ordersEnhanced" :key="order.id">
    <p>#{{ order.id }}</p>
    <select
      @change="setStatus(order.id, ($event.target as HTMLSelectElement).value as Order['status'])"
    >
      <option value="NEW" :selected="order.status === 'NEW'">NEW</option>
      <option value="DELIVERING" :selected="order.status === 'DELIVERING'">DELIVERING</option>
      <option value="COMPLETED" :selected="order.status === 'COMPLETED'">COMPLETED</option>
      <option value="CANCELED" :selected="order.status === 'CANCELED'">CANCELED</option>
    </select>
    <p>{{ order.email }}</p>
    <tr>
      <th>Product name</th>
      <th>Product color</th>
      <th>Product manufacturer</th>
      <th>Product price</th>
      <th>Product image</th>
      <th>Quantity</th>
    </tr>
    <tr v-for="p in order.products" :key="p.productId">
      <div v-if="p.product">
        <td>{{ p.product.name }}</td>
        <td>{{ p.product.color }}</td>
        <td>{{ p.product.manufacturer }}</td>
        <td>{{ p.product.price }}</td>
        <td>
          <img :src="p.product.image" :alt="p.product.name" style="width: 100px; height: 100px" />
        </td>
        <td>{{ p.quantity }}</td>
      </div>
    </tr>
  </table>
</template>
<script setup lang="ts">
import { computed, ref } from 'vue'
import { Order, Product } from '@/types'

const orders = ref<Order[]>([])
const products = ref<Product[]>([])

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

const refreshProducts = () => {
  fetch('http://localhost:8080/getProducts')
    .then((res) => res.json())
    .then((data) => (products.value = data))
}
refreshProducts()
const refreshOrders = () => {
  fetch('http://localhost:8080/getOrders')
    .then((res) => res.json())
    .then((data) => (orders.value = data))
}
refreshOrders()
const setStatus = async (id: number, status: Order['status']) => {
  await fetch('http://localhost:8080/updateOrder', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ id, status })
  })
  refreshOrders()
}
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
form {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 1000px;
  flex-direction: column;
  margin-top: 50px;
}
input {
  width: 220px;
  height: 30px;
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
textarea {
  width: 220px;
  resize: vertical;
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
select {
  width: 220px;
  height: 30px;
  margin: 5px auto;
  border-radius: 10px;
  padding-left: 15px;
  color: black;
  text-align: left;
  font-family: Montserrat;
  font-size: 15px;
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
table {
  width: 1000px;
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
